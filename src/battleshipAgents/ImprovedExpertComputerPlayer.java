package battleshipAgents;

import improvedExpertAgentUtils.MetaData;
import improvedExpertAgentUtils.ShipMover;
import improvedExpertAgentUtils.ShipShooter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ships.Mover;
import ships.Ship;
import ships.Ship.ShipType;
import battleship.Shot;
import board.Board;
import board.HitResponse;
import display.Display;
import display.FileDisplay;

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
            shipMover.add(new Mover(ship, display));
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

        shipMover.setAllTargets(shipShooter.getAllTargets(board, display), board);

        Map<ShipType, Map<ShipType, MetaData>> allTargetedShips = shipMover
                .getAllTargetedShips();
        for (Map.Entry<ShipType, Map<ShipType, MetaData>> mapEntry : allTargetedShips
                .entrySet())
        {
            for (Map.Entry<ShipType, MetaData> mapEntry2 : mapEntry.getValue().entrySet())
            {
                shipShooter.setTargetedShipMetaData(mapEntry.getKey(),
                        mapEntry2.getKey(), mapEntry2.getValue());
            }
        }

        // List<Ship> activeShips = getActiveShips();
        // for(Ship ship : activeShips)
        // {
        // shipMover.setTargetDestination(ship.getShipType(),
        // shipShooter.getTarget(ship.getShipType(), board), board);
        // }

        display.writeLine("-------------------- BEFORE MOVING --------------------");
        display.printBoard();
        shipMover.moveShips(board);

        display.writeLine("-------------------- AFTER MOVING --------------------");
        display.printBoard();
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
            hitResponses.add(isHit(shot, dealDamage));
        }

        shipShooter.addInfo(shots);

        return hitResponses;
    }
}
