package expertAgentUtils;

import java.util.*;

import ships.Point;
import battleship.Shot;
import board.Board;
import ships.Ship.ShipType;
import ships.*;

public class ShipPredictor {
    private List<Point> carrierShots;
    private List<Point> battleShipShots;
    private List<Point> destroyerShots;
    private List<Point> pbShots;
    private List<Point> subShots;

    private List<Point> carrierGuesses;
    private List<Point> battleshipGuesses;
    private List<Point> destroyerGuesses;
    private List<Point> pbGuesses;
    private List<Point> subGuesses;

    private ParticleFilter carrierParticleFilter;
    private ParticleFilter battleshipParticleFilter;
    private ParticleFilter destroyerParticleFilter;
    private ParticleFilter pbParticleFilter;
    private ParticleFilter subParticleFilter;

    private Board board;

    public ShipPredictor(Board board)
    {
        carrierShots = new ArrayList<Point>();
        battleShipShots = new ArrayList<Point>();
        destroyerShots = new ArrayList<Point>();
        pbShots = new ArrayList<Point>();
        subShots = new ArrayList<Point>();

        carrierGuesses = new ArrayList<Point>();
        battleshipGuesses = new ArrayList<Point>();
        destroyerGuesses = new ArrayList<Point>();
        pbGuesses = new ArrayList<Point>();
        subGuesses = new ArrayList<Point>();

        carrierParticleFilter = new ParticleFilter(board);
        battleshipParticleFilter = new ParticleFilter(board);
        destroyerParticleFilter = new ParticleFilter(board);
        pbParticleFilter = new ParticleFilter(board);
        subParticleFilter = new ParticleFilter(board);

        this.board = board;
    }

    public void addInfo(Shot shot)
    {
        if(shot.getShipType() == ShipType.CARRIER)
        {
            carrierShots.add(shot.getPoint());
            carrierParticleFilter.addShot(shot);
        }
        else if(shot.getShipType() == ShipType.BATTLESHIP)
        {
            battleShipShots.add(shot.getPoint());
            battleshipParticleFilter.addShot(shot);
        }
        else if(shot.getShipType() == ShipType.DESTROYER)
        {
            destroyerShots.add(shot.getPoint());
            destroyerParticleFilter.addShot(shot);
        }
        else if(shot.getShipType() == ShipType.PATROLBOAT)
        {
            pbShots.add(shot.getPoint());
            pbParticleFilter.addShot(shot);
        }
        else //if(s.getShipType() == ShipType.SUBMARINE)
        {
            subShots.add(shot.getPoint());
            subParticleFilter.addShot(shot);
        }
    }

    public Point getPrediction(ShipType type)
    {
        if(type == ShipType.CARRIER)
        {
            Point guess = carrierParticleFilter.getMostLikelyLocation();
            carrierGuesses.add(guess);
            return guess;
        }
        else if(type == ShipType.BATTLESHIP)
        {
            Point guess = battleshipParticleFilter.getMostLikelyLocation();
            battleshipGuesses.add(guess);
            return guess;
        }
        else if(type == ShipType.DESTROYER)
        {
            Point guess = destroyerParticleFilter.getMostLikelyLocation();
            destroyerGuesses.add(guess);
            return guess;
        }
        else if(type == ShipType.PATROLBOAT)
        {
            Point guess = pbParticleFilter.getMostLikelyLocation();
            pbGuesses.add(guess);
            return guess;
        }
        else //(type == ShipType.SUBMARINE)
        {
            Point guess = subParticleFilter.getMostLikelyLocation();
            subGuesses.add(guess);
            return guess;
        }

    }

    public List<Point> getHistoryOfShots(ShipType type)
    {
        if(type == ShipType.CARRIER)
            return carrierShots;
        else if(type == ShipType.BATTLESHIP)
            return battleShipShots;
        else if(type == ShipType.DESTROYER)
            return destroyerShots;
        else if(type == ShipType.PATROLBOAT)
            return pbShots;
        else //if(type == ShipType.SUBMARINE)
            return subShots;
    }

    public List<Point> getHistoryOfGuesses(ShipType type)
    {
        if(type == ShipType.CARRIER)
            return carrierGuesses;
        else if(type == ShipType.BATTLESHIP)
            return battleshipGuesses;
        else if(type == ShipType.DESTROYER)
            return destroyerGuesses;
        else if(type == ShipType.PATROLBOAT)
            return pbGuesses;
        else //if(type == ShipType.SUBMARINE)
            return subGuesses;
    }
}
