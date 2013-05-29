package battleshipAgents;

import java.util.*;

import ships.*;
import ships.Ship.ShipType;
import battleship.Shot;
import board.*;
import display.Display;
import display.FileDisplay;
import expertAgentUtils.EnemyShips;
import expertAgentUtils.ShipDestroyer;
import expertAgentUtils.ShipMover;
import expertAgentUtils.ShipPredictor;

public class ExpertComputerPlayer extends Player  {
    private Display display;

    private int salvoCount;
    private int shipToFireThisTurn;
    private int shipsFiredThisTurn;

    //private Shot lastShot;
    //private ShipType targetedShip;
    //private List<Point> actualShipLocation;

    private ShipPredictor predictor;
    private EnemyShips enemyShips;
    private ShipDestroyer shipDestroyer;
    private ShipMover shipMover;

    private List<Point> pointsToAttack;
    private List<Point> scanResults;
    private boolean didAScan;

    public ExpertComputerPlayer(Board board, String playerName)
    {
        super(board, playerName);
        display = new FileDisplay(board, playerName, "player-log-" + playerName + ".txt");

        predictor = new ShipPredictor(board);
        enemyShips = new EnemyShips();
        shipDestroyer = new ShipDestroyer(board);
        shipMover = new ShipMover();

        List<Ship> activeShips = board.getActiveShips();
        for(Ship ship : activeShips)
        {
            shipMover.add(new Mover(ship));
        }

        didAScan = false;
        scanResults = new ArrayList<Point>();
        pointsToAttack = new ArrayList<Point>();
    }

    @Override
    public void moveShips()
    {
        display.writeLine("************************* START TURN *************************");
        board.nextTurn();

        pointsToAttack.clear();

        Point target = findNextTarget();
        display.writeLine("Next target is: " + target);

        List<Ship> activeShips = board.getActiveShips();
        for(Ship ship : activeShips)
        {
            shipMover.setTargetDestination(ship.getShipType(), target, board);
        }

        shipMover.moveShips(board);

        salvoCount = activeShips.size();
        shipsFiredThisTurn = 0;
        shipToFireThisTurn = 0;

        doneWithTurn = false;
    }

    private Point getOptimalShot()
    {
        Point optimalShot;
        display.writeLine("finding optimal shot");

        if(enemyShips.isShipStillFloating(ShipType.SUBMARINE))
        {
            optimalShot = predictor.getPrediction(ShipType.SUBMARINE);
            //targetedShip = ShipType.SUBMARINE;
        }
        else if(enemyShips.isShipStillFloating(ShipType.PATROLBOAT))
        {
            optimalShot = predictor.getPrediction(ShipType.PATROLBOAT);
            //targetedShip = ShipType.PATROLBOAT;
        }
        else if(enemyShips.isShipStillFloating(ShipType.DESTROYER))
        {
            optimalShot = predictor.getPrediction(ShipType.DESTROYER);
            //targetedShip = ShipType.DESTROYER;
        }
        else if(enemyShips.isShipStillFloating(ShipType.CARRIER))
        {
            optimalShot = predictor.getPrediction(ShipType.CARRIER);
            //targetedShip = ShipType.CARRIER;
        }
        else //if(enemyShips.isShipStillFloating(ShipType.BATTLESHIP))
        {
            optimalShot = predictor.getPrediction(ShipType.BATTLESHIP);
            //targetedShip = ShipType.BATTLESHIP;
        }

        return optimalShot;
    }

    private List<Point> performCarrierScan()
    {
        didAScan = true;

        Point pointToScan = getOptimalShot();
        boolean needToSearchNextLine = false;

        Point startPoint = new Point(pointToScan.getX()-1, pointToScan.getY()-1);

        if(!board.isValidShot(startPoint))
        {
            startPoint = new Point(pointToScan.getX()-1, pointToScan.getY());

            if(!board.isValidShot(startPoint))
            {
                needToSearchNextLine = true;
                startPoint = new Point(pointToScan.getX(), pointToScan.getY()-1);

                if(!board.isValidShot(startPoint))
                {
                    startPoint = new Point(pointToScan.getX(), pointToScan.getY());
                }
            }
        }

        Point endPoint = null;
        int row;
        if(needToSearchNextLine)
            row = pointToScan.getX()+1;
        else
            row = pointToScan.getX();

        endPoint = new Point(row, pointToScan.getY()+1);

        if(!board.isValidShot(endPoint))
        {
            endPoint = new Point(row, pointToScan.getY());
        }

        display.writeLine("start point: " + startPoint);
        display.writeLine("end point: " + endPoint);

        return (new Scan(startPoint, endPoint, board)).getPointsToScan();
    }

    @Override
    public List<Shot> takeAShot()
    {
        display.writeLine("------------board when trying to attack------------");
        display.printBoard();

        findPlaceToAttack();

        List<Shot> shots = new ArrayList<Shot>();
        List<Ship> ships = board.getActiveShips();

        Ship ship = ships.get(shipToFireThisTurn);

        display.writeLine("shooting from: " + ship.getShipType());

        for(Point pointToAttack : pointsToAttack)
        {
            if(ship.isValidShot(pointToAttack))
            {
                display.writeLine("attacking/scanning: " + pointToAttack);
                shots.add(new Shot(pointToAttack, ship.getShipType()));
            }
            else
            {
                display.writeLine("I (" + ship.getShipType() + ") want to attack: " + pointToAttack);
                display.writeLine("\nBut I can't!  I'm too far away!");
                scanResults.add(0, pointToAttack);

                //That was in invalid scan you just did
                if(pointsToAttack.size() > 1)
                    scanResults.clear();
            }
        }

        pointsToAttack.clear();

        //regardless if the carrier scanned, no more scans can be done
        if(ship.getShipType() == ShipType.CARRIER)
            didAScan = true;

        shipToFireThisTurn++;
        shipsFiredThisTurn++;

        return shots;
    }

    private void findPlaceToAttack()
    {
        if(scanResults.size() > 0)
        {
            pointsToAttack.add(scanResults.get(0));
            scanResults.remove(0);
        }
        else
        {
            if(shipDestroyer.hotOnTrail())
                pointsToAttack.add(shipDestroyer.getNextShot());
            else
            {
                boolean isThereACarrier = false;
                List<Ship> ships = board.getActiveShips();
                for(Ship s : ships)
                {
                    if(s.getShipType() == ShipType.CARRIER)
                        isThereACarrier = true;
                }

                if(!didAScan && isThereACarrier)
                {
                    pointsToAttack = performCarrierScan();
                }
                else
                {
                    pointsToAttack.add(getOptimalShot());
                }
            }
        }
    }

    private Point findNextTarget()
    {
        if(scanResults.size() > 0)
        {
            return scanResults.remove(0);
        }
        else
        {
            if(shipDestroyer.hotOnTrail())
                return shipDestroyer.getNextShot();
            else
            {
                if(board.getActiveShips().contains((new ShipFactory()).getShip(ShipType.CARRIER)))
                {
                    List<Point> carrierScan = performCarrierScan();
                    didAScan = false; //were just calculating where to move next
                    return carrierScan.get(0);
                }
                else
                {
                    return getOptimalShot();
                }
            }
        }
    }

    /*
    @Override
    public ShipType getTargedShipType()
    {
        return targetedShip;
    }

    @Override
    public void informActualLocation(List<Point> actualShipLocation)
    {
        this.actualShipLocation = actualShipLocation;
    }
    */

    @Override
    public void getResponse(List<HitResponse> hitResponses)
    {
        if(hitResponses.size() == 1)
        {
            HitResponse response = hitResponses.get(0);

            if(response.isAHit())
            {
                display.writeLine("I shot at: " + response.getLocation().toString() + " and I hit something!");
                //enemyShips.registerHit(targetedShip);
                shipDestroyer.hit(response.getLocation());
            }
            else
            {
                display.writeLine("I shot at: " + response.getLocation().toString() + " and I missed!");

                //It doesn't need to know about every miss...
                if(shipDestroyer.hotOnTrail())
                    shipDestroyer.miss(response.getLocation());
            }

            if(response.getSunkShip() != null)
            {
                ShipType sunkShip = response.getSunkShip();
                enemyShips.sunkShip(sunkShip);
                display.writeLine("You sunk their " + sunkShip + "!");
                shipDestroyer.reset();
            }
        }
        else //IT WAS A SCAN!
        {
            for(HitResponse response : hitResponses)
            {
                if(response.isAHit())
                    scanResults.add(response.getLocation());
            }
            display.writeLine("The scan found the following: ");
            for(Point foundLocation : scanResults)
            {
                display.writeLine(foundLocation.toString());
            }
        }

        /*
        display.writeLine("the ship's actual location was: ");
        for(Point p : actualShipLocation)
        {
            display.writeLine(p.toString());
        }

        display.writeLine("history of predictions from that ship: ");
        for(Point p : predictor.getHistoryOfGuesses(lastShot.getShipType()))
        {
            display.writeLine(p.toString());
        }
        */

        if(shipsFiredThisTurn >= salvoCount)
        {
            display.writeLine("************************* TURN OVER *************************");
            didAScan = false;
            doneWithTurn = true;
            scanResults.clear();
        }
    }

    @Override
    public List<HitResponse> isHit(List<Shot> shots)
    {
        List<HitResponse> hitResponses = new ArrayList<HitResponse>();

        //display.writeLine("board before:");
        //display.printBoard();

        boolean dealDamage = true;
        if(shots.size() > 1)
            dealDamage = false; //ITS A SCAN!

        for(Shot shot : shots)
        {
            hitResponses.add(isHit(shot.getPoint(), dealDamage));
            predictor.addInfo(shot);
        }

        //display.writeLine("board after:");
        //display.printBoard();

        return hitResponses;
    }
}
