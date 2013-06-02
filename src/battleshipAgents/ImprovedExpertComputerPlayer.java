package battleshipAgents;

import java.util.*;

import ships.*;
import ships.Ship.ShipType;
import battleship.Shot;
import board.*;
import display.Display;
import display.FileDisplay;
import expertAgentUtils.ShipMover;
import expertAgentUtils.ShipShooter;

public class ImprovedExpertComputerPlayer extends Player
{
    private Display display;
    private ShipMover shipMover;
    private ShipShooter shipShooter;

    public ImprovedExpertComputerPlayer(Board board, String playerName) {
        super(board, playerName);
        display = new FileDisplay(board, playerName, "player-log-" + playerName + ".txt");

        List<Ship> activeShips = getActiveShips();
        shipMover = new ShipMover();
        shipShooter = new ShipShooter(board, display, activeShips);

        for (Ship ship : activeShips)
        {
            shipMover.add(new Mover(ship));
        }
    }

    private List<Ship> getActiveShips()
    {
        return board.getActiveShips();
    }

    @Override
    public void moveShips()
    {
        display.writeLine("************************* START TURN *************************");
        board.nextTurn();

        List<ShipType> activeShips = board.getActiveShipTypes();
        shipMover.setActiveShips(activeShips);
        shipShooter.setActiveShips(activeShips);

        shipMover.setAllTargets(shipShooter.getAllTargets(board), board);

        Map<ShipType, ShipType> allTargetedShips = shipMover.getAllTargetedShips();
        for (Map.Entry<ShipType, ShipType> mapEntry : allTargetedShips.entrySet())
        {
            shipShooter.setTargetedShip(mapEntry.getKey(), mapEntry.getValue());
        }

        // List<Ship> activeShips = getActiveShips();
        // for(Ship ship : activeShips)
        // {
        // shipMover.setTargetDestination(ship.getShipType(),
        // shipShooter.getTarget(ship.getShipType(), board), board);
        // }

        shipMover.moveShips(board);
        doneWithTurn = false;
    }

    @Override
    public List<Shot> takeAShot()
    {
        return shipShooter.getNextShot(board);
    }

    @Override
    public void getResponse(List<HitResponse> hitResponses)
    {
        shipShooter.getResponse(hitResponses);
        doneWithTurn = shipShooter.isTurnOver();
    }

    @Override
    public List<HitResponse> isHit(List<Shot> shots)
    {
        List<HitResponse> hitResponses = new ArrayList<HitResponse>();

        boolean dealDamage = true;
        if (shots.size() > 1)
            dealDamage = false; // ITS A SCAN!

        for (Shot shot : shots)
        {
            hitResponses.add(isHit(shot.getPoint(), dealDamage));
        }

        shipShooter.addInfo(shots);

        return hitResponses;
    }
}
