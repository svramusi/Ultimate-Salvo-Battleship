package expertAgentUtils;

import improvedExpertAgentUtils.MetaData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ships.Mover;
import ships.Ship.ShipType;
import battleshipExceptions.InvalidShipPositionException;
import battleshipExceptions.ShipDamagedException;
import board.Board;

public class ShipMover
{
    private List<Mover> movers;

    public ShipMover() {
        movers = new ArrayList<Mover>();
    }

    public void add(Mover mover)
    {
        movers.add(mover);
    }

    // public void setTargetDestination(ShipType shipType, Point destination,
    // Board board)
    // {
    // for (Mover mover : movers)
    // {
    // if (mover.getShipType().equals(shipType))
    // {
    // mover.calculateDesiredLocation(destination, board);
    // }
    // }
    // }

    public void setAllTargets(Map<ShipType, MetaData> allTargets, Board board)
    {
        for (Mover mover : movers)
        {
            mover.setAllTargets(allTargets, board);
        }

        for (Mover mover : movers)
        {
            if (mover.didDeferMove())
            {
                mover.setAllTargets(allTargets, board);
            }
        }
    }

    public Map<ShipType, Map<ShipType, MetaData>> getAllTargetedShips()
    {
        Map<ShipType, Map<ShipType, MetaData>> allTargetedShips = new HashMap<ShipType, Map<ShipType, MetaData>>();

        for (Mover mover : movers)
        {
            Map<ShipType, MetaData> shipData = new HashMap<ShipType, MetaData>();
            shipData.put(mover.getTargetedShip(), mover.getMetaData());

            allTargetedShips.put(mover.getShipType(), shipData);
        }

        return allTargetedShips;
    }

    public void moveShips(Board board)
    {
        int movedShips = 0;
        int totalShips = movers.size();

        while (movedShips < totalShips)
        {
            movedShips = 0;
            for (Mover mover : movers)
            {
                if (mover.shouldDelayMove())
                {
                    continue;
                }

                if (mover.shouldCalcNewPosition())
                {
                    mover.recalculateDesiredLocation();
                }

                try
                {
                    mover.move(board);
                } catch (InvalidShipPositionException e)
                {
                    System.out.println("Caught invalid ship position exception.");
                } catch (ShipDamagedException e)
                {
                    System.out.println("Caught invalid ship damaged exception.");
                }

                movedShips++;
            }
        }
    }

    public void setActiveShips(List<ShipType> activeShips)
    {
        if (movers.size() == activeShips.size())
            return;

        List<Mover> shipsToRemove = new ArrayList<Mover>();

        for (Mover mover : movers)
        {
            if (!activeShips.contains(mover.getShipType()))
            {
                mover.sunk();
                shipsToRemove.add(mover);
            }
        }

        movers.removeAll(shipsToRemove);
    }
}
