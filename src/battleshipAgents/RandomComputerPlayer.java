package battleshipAgents;

import ships.*;
import ships.Ship.ShipType;
import ships.Ship.Direction;
import board.HitResponse;
import board.Board;
import display.Display;
import display.FileDisplay;
import battleship.Shot;
import battleshipExceptions.*;

import java.util.*;

public class RandomComputerPlayer extends Player {
    private Display display;

    private int salvoCount;
    private int shipToFireThisTurn;
    private int shipsFiredThisTurn;

    public RandomComputerPlayer(Board board, String playerName)
    {
        super(board, playerName);
        display = new FileDisplay(board, playerName, "player-log-" + playerName + ".txt");
    }

    private void moveShipNoRotate(Ship s)
    {
        Ship.Direction direction = s.getDirection();
        Point startingPoint = s.getStartPoint();
        Point newStartingPoint;

        Random random = new Random();
        int randomNumber = random.nextInt();

        int startX = startingPoint.getX();
        int startY = startingPoint.getY();

        if(direction == Ship.Direction.UP || direction == Ship.Direction.DOWN)
        {
            if(randomNumber % 2 == 0)
                newStartingPoint = new Point(startX+2,startY);
            else
                newStartingPoint = new Point(startX-2,startY);
        }
        else
        {
            if(randomNumber % 2 == 0)
                newStartingPoint = new Point(startX,startY+2);
            else
                newStartingPoint = new Point(startX,startY-2);
        }

        try {
            board.moveShip(s.getShipType(), newStartingPoint, direction);
        } catch (InvalidShipPositionException e) {
            //Throw away, just don't move the ship
        } catch (ShipDamagedException e) {
            //Throw away, just don't move the ship
        }
    }

    private void rotateShip(Ship s)
    {
        Ship.Direction direction = s.getDirection();
        Point startingPoint = s.getStartPoint();

        Random random = new Random();
        int randomNumber = random.nextInt();

        if(direction == Direction.UP || direction == Direction.DOWN)
        {
            if(randomNumber % 2 == 0)
            {
                try {
                    board.moveShip(s.getShipType(), startingPoint, Direction.LEFT);
                } catch (InvalidShipPositionException e) {
                    //Throw away, just don't move the ship
                } catch (ShipDamagedException e) {
                    //Throw away, just don't move the ship
                }
            }
            else
            {
                try {
                    board.moveShip(s.getShipType(), startingPoint, Direction.RIGHT);
                } catch (InvalidShipPositionException e) {
                    //Throw away, just don't move the ship
                } catch (ShipDamagedException e) {
                    //Throw away, just don't move the ship
                }
            }
        }
        else
        {
            if(randomNumber % 2 == 0)
            {
                try {
                    board.moveShip(s.getShipType(), startingPoint, Direction.UP);
                } catch (InvalidShipPositionException e) {
                    //Throw away, just don't move the ship
                } catch (ShipDamagedException e) {
                    //Throw away, just don't move the ship
                }
            }
            else
            {
                try {
                    board.moveShip(s.getShipType(), startingPoint, Direction.DOWN);
                } catch (InvalidShipPositionException e) {
                    //Throw away, just don't move the ship
                } catch (ShipDamagedException e) {
                    //Throw away, just don't move the ship
                }
            }
        }
    }

    @Override
    public void moveShips()
    {
        display.writeLine("************************* START TURN *************************");
        board.nextTurn();

        List<Ship> ships = board.getActiveShips();
        Random random = new Random();
        int randomNumber;

        for (Ship s : ships)
        {
            randomNumber = random.nextInt();
            if(randomNumber % 2 == 0)
                moveShipNoRotate(s);
            else
                rotateShip(s);
        }

        salvoCount = ships.size();
        shipsFiredThisTurn = 0;
        shipToFireThisTurn = 0;

        doneWithTurn = false;
    }

    private Shot getShotFromShip(Ship ship)
    {
        Random random = new Random();

        boolean isValidShot = false;
        Point shotPoint = new Point(0,0);

        while(!isValidShot)
        {
            int randomFrontBack = random.nextInt();
            int randomPlusMinus = random.nextInt();
            int randomShootDistanceX = random.nextInt(ship.getShootDistance());
            int randomShootDistanceY = random.nextInt(ship.getShootDistance());
            Point pointToShootFrom;

            if(randomFrontBack % 2 == 0)
                pointToShootFrom = ship.getStartPoint();
            else
                pointToShootFrom = ship.getEndPoint();

            if(randomPlusMinus % 2 == 0)
                shotPoint = new Point(pointToShootFrom.getX() + randomShootDistanceX, pointToShootFrom.getY() + randomShootDistanceY);
            else
                shotPoint = new Point(pointToShootFrom.getX() - randomShootDistanceX, pointToShootFrom.getY() - randomShootDistanceY);


            if(shotPoint.getX() < 0 || shotPoint.getY() < 0 || shotPoint.getX() > 9 || shotPoint.getY() > 9)
                isValidShot = false;
            else
                isValidShot = true;
        }

        display.writeLine("Shooting at: " + shotPoint.toString() + " from: " + ship.getShipType().toString());

        return new Shot(shotPoint, ship.getShipType());
    }

    @Override
    public List<Shot> takeAShot()
    {
        List<Ship> ships = board.getActiveShips();

        List<Shot> shots = new ArrayList<Shot>();

        shots.add(getShotFromShip(ships.get(shipToFireThisTurn)));

        shipToFireThisTurn++;
        shipsFiredThisTurn++;

        return shots;
    }

    /*
    @Override
    public ShipType getTargedShipType()
    {
        return lastShot.getShipType();
    }

    @Override
    public void informActualLocation(List<Point> actualShipLocation)
    {
        //I'M NOT A CHEATER!
    }
    */

    @Override
    public void getResponse(List<HitResponse> hitResponse)
    {
        //Ignore response, it isn't going to affect anything
        if(shipsFiredThisTurn >= salvoCount)
        {
            display.writeLine("************************* TURN OVER *************************");
            doneWithTurn = true;
        }
    }

    @Override
    public List<HitResponse> isHit(List<Shot> shots)
    {
        List<HitResponse> hitResponses = new ArrayList<HitResponse>();
        //Must respond with result, but ignore where the shot came from
        display.writeLine("board before:");
        display.printBoard();

        boolean dealDamage = true;
        if(shots.size() > 1)
            dealDamage = false; //ITS A SCAN!

        for(Shot shot : shots)
        {
            hitResponses.add(isHit(shot.getPoint(), dealDamage));
        }

        display.writeLine("board after:");
        display.printBoard();

        return hitResponses;
    }
}
