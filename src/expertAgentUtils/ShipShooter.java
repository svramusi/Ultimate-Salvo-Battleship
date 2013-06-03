package expertAgentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ships.Point;
import ships.Ship;
import ships.Ship.ShipType;
import battleship.Shot;
import board.Board;
import board.HitResponse;
import display.Display;

public class ShipShooter
{
    private Display display;
    private List<Shooter> shooters;
    private int currentShooterIndex;
    private boolean turnOver;
    private ScanResult scanResult;
    private EnemyShips enemyShips;

    public ShipShooter(Board board, Display display, List<Ship> ships) {
        this.display = display;
        shooters = new ArrayList<Shooter>();
        enemyShips = new EnemyShips();

        for (Ship ship : ships)
        {
            shooters.add(new Shooter(board, ship, display));
        }

        scanResult = new ScanResult();
        currentShooterIndex = 0;
        turnOver = true;
    }

    public void setActiveShips(List<ShipType> activeShips)
    {
        if (shooters.size() == activeShips.size())
            return;

        List<Shooter> shootersToRemove = new ArrayList<Shooter>();
        for (Shooter shooter : shooters)
        {
            if (!activeShips.contains(shooter.getShipType()))
            {
                shootersToRemove.add(shooter);
            }
        }

        shooters.removeAll(shootersToRemove);
    }

    public Map<ShipType, MetaData> getAllTargets(Board board, Display display)
    {
        Map<ShipType, MetaData> allTargets = new HashMap<ShipType, MetaData>();
        List<ShipType> possibleTargets = new ArrayList<ShipType>();

        for (Ship ship : enemyShips.getFloatingShips())
        {
            possibleTargets.add(ship.getShipType());
        }

        // Here we're only looking for possible targets.
        // As such, give priority to scan results as they are known points of a
        // ship, yet not attacked yet. Target those first so they ships don't
        // get away.

        display.writeLine(Integer.toString(scanResult.size()));

        if (scanResult.size() > 0)
        {
            allTargets.put(scanResult.getShipType(), new MetaData(scanResult.getAll(),
                    false, true, false));
            possibleTargets.remove(scanResult.getShipType());
        }

        for (Shooter shooter : shooters)
        {
            ShipType attackingShip = shooter.attackingShip();
            if (attackingShip != null)
            {
                if (possibleTargets.contains(attackingShip))
                {
                    allTargets.put(attackingShip, new MetaData(shooter.getAllHits(),
                            true, false, false));
                    possibleTargets.remove(attackingShip);
                }
            }
        }

        // Just get the first one because they'll all have the same info
        Shooter shooter = shooters.get(0);
        for (ShipType possibleTarget : possibleTargets)
        {
            List<Point> optimalShot = new ArrayList<Point>();
            optimalShot.add(shooter.getOptimalShot(possibleTarget));

            allTargets.put(possibleTarget, new MetaData(optimalShot, false, false, true));
        }
        // We don't like to remove elements inside of an iterator...
        possibleTargets.clear();

        turnOver = false;
        return allTargets;
    }

    public void setTargetedShipMetaData(ShipType yourShip, ShipType enemyShip,
            MetaData targetMetaData)
    {
        currentShooterIndex = 0;
        for (Shooter shooter : shooters)
        {
            if (shooter.getShipType().equals(yourShip))
            {
                shooter.setTargetedShipMetaData(enemyShip, targetMetaData);
            }
        }
    }

    // public Point getTarget(ShipType shipType, Board board)
    // {
    // for (Shooter shooter : shooters)
    // {
    // if (shooter.getShipType().equals(shipType))
    // {
    // return shooter.findNextTarget(board);
    // }
    // }
    //
    // // Need to return exception
    // return null;
    // }

    public List<Shot> getNextShot(Board board)
    {
        Shooter currentShooter = shooters.get(currentShooterIndex);

        display.writeLine("------------board when trying to attack from "
                + currentShooter.getShipType() + "------------");
        display.printBoard();

        List<Shot> shots = new ArrayList<Shot>();
        Ship ship = currentShooter.getShip();

        display.writeLine("shooting from: " + ship.getShipType());

        List<Point> placesToAttack = currentShooter.findPlacesToAttack(board);
        for (Point placeToAttack : placesToAttack)
        {
            display.writeLine("Place to attack: " + placeToAttack);
            if (ship.isValidShot(placeToAttack))
            {
                display.writeLine("attacking/scanning: " + placeToAttack);
                shots.add(new Shot(placeToAttack, ship.getShipType()));
            } else
            {
                display.writeLine("I (" + ship.getShipType() + ") want to attack: "
                        + placeToAttack);
                display.writeLine("But I can't!  I'm too far away!");
                currentShooter.undoLastAttack(placeToAttack);

                // That was in invalid scan you just did
                if (placesToAttack.size() > 1)
                    currentShooter.undoLastScan();
            }
        }

        currentShooterIndex++;

        return shots;
    }

    public void getResponse(List<HitResponse> hitResponses)
    {
        Shooter lastShooter = shooters.get(currentShooterIndex - 1);

        if (hitResponses.size() == 0)
        {
            // You missed...
        } else if (hitResponses.size() == 1)
        {
            HitResponse response = hitResponses.get(0);

            if (response.isAHit())
            {
                display.writeLine("I shot at: " + response.getLocation().toString()
                        + " and I hit something!");

                for (Shooter shooter : shooters)
                {
                    shooter.registerHit(lastShooter.getTargetedShip(),
                            response.getLocation());
                }
            } else
            {
                display.writeLine("I shot at: " + response.getLocation().toString()
                        + " and I missed!");

                for (Shooter shooter : shooters)
                {
                    shooter.registerMiss(lastShooter.getTargetedShip(),
                            response.getLocation());
                }
            }

            if (response.getSunkShip() != null)
            {
                ShipType sunkShip = response.getSunkShip();
                enemyShips.sunkShip(sunkShip);
                display.writeLine("You sunk their " + sunkShip + "!");

                for (Shooter shooter : shooters)
                {
                    shooter.sunk(response.getLocation());
                }
            }
        } else
        // IT WAS A SCAN!
        {
            scanResult.clear();
            scanResult.setTargetedShip(lastShooter.getTargetedShip());
            for (HitResponse response : hitResponses)
            {
                if (response.isAHit())
                    scanResult.add(response.getLocation());
            }

            for (Shooter shooter : shooters)
                shooter.setScanResult(scanResult);

            display.writeLine("The scan found the following: ");
            for (Point foundLocation : scanResult.getAll())
            {
                display.writeLine(foundLocation.toString());
            }
        }

        if (currentShooterIndex >= shooters.size())
        {
            display.writeLine("************************* TURN OVER *************************");
            turnOver = true;

            for (Shooter shooter : shooters)
                shooter.reset();
        }
    }

    public boolean isTurnOver()
    {
        return turnOver;
    }

    public void addInfo(List<Shot> shots)
    {
        for (Shooter shooter : shooters)
        {
            shooter.addInfo(shots);
        }
    }
}
