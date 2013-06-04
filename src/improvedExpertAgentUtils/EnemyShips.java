package improvedExpertAgentUtils;

import java.util.ArrayList;
import java.util.List;

import ships.Ship;
import ships.Ship.ShipType;
import ships.ShipFactory;

public class EnemyShips
{
    private List<Ship> activeEnemyShips;

    private int carrierHits;
    private int battleshipHits;
    private int destroyerHits;
    private int pbHits;
    private int subHits;

    public EnemyShips() {
        activeEnemyShips = new ArrayList<Ship>();

        activeEnemyShips.add((new ShipFactory()).getShip(ShipType.CARRIER));
        activeEnemyShips.add((new ShipFactory()).getShip(ShipType.BATTLESHIP));
        activeEnemyShips.add((new ShipFactory()).getShip(ShipType.DESTROYER));
        activeEnemyShips.add((new ShipFactory()).getShip(ShipType.PATROLBOAT));
        activeEnemyShips.add((new ShipFactory()).getShip(ShipType.SUBMARINE));

        carrierHits = 0;
        battleshipHits = 0;
        destroyerHits = 0;
        pbHits = 0;
        subHits = 0;
    }

    public void registerHit(ShipType shipTypeAimedFor)
    {
        if (shipTypeAimedFor.equals(ShipType.CARRIER))
        {
            carrierHits++;
        } else if (shipTypeAimedFor.equals(ShipType.BATTLESHIP))
        {
            battleshipHits++;
        } else if (shipTypeAimedFor.equals(ShipType.DESTROYER))
        {
            destroyerHits++;
        } else if (shipTypeAimedFor.equals(ShipType.PATROLBOAT))
        {
            pbHits++;
        } else
        // if(shipType.equals(ShipType.SUBMARINE))
        {
            subHits++;
        }
    }

    public void sunkShip(ShipType shipType)
    {
        int shipIndex = 0;
        for (Ship s : activeEnemyShips)
        {
            if (s.getShipType().equals(shipType))
                break;

            shipIndex++;
        }

        if (shipIndex < activeEnemyShips.size())
            activeEnemyShips.remove(shipIndex);
    }

    public boolean isShipStillFloating(ShipType shipType)
    {
        for (Ship s : activeEnemyShips)
        {
            if (s.getShipType().equals(shipType))
                return true;
        }

        return false;
    }

    public List<Ship> getFloatingShips()
    {
        return activeEnemyShips;
    }
}
