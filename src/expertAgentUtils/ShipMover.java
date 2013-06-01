package expertAgentUtils;

import ships.Mover;
import ships.Point;
import ships.Ship.ShipType;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
    }

    public Map<ShipType, ShipType> getAllTargetedShips()
    {
        Map<ShipType, ShipType> allTargetedShips = new HashMap<ShipType, ShipType>();
        
        for (Mover mover : movers)
        {
            allTargetedShips.put(mover.getShipType(), mover.getTargetedShip());
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
}
