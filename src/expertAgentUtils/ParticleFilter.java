package expertAgentUtils;

import java.util.*;

import ships.*;
import ships.Ship.Direction;
import ships.Ship.ShipType;
import battleship.Shot;
import board.Board;

public class ParticleFilter {

    private Particle[] particles;

    private Board board;

    private static final int particleCount = 300;
    private static final double noise = 0.2;

    ParticleFilter(Board board)
    {
        this.board = board;

        Random random = new Random();
        particles = new Particle[particleCount];

        for(int i=0; i<particleCount; i++)
        {
            double x = random.nextInt(board.getWidth());
            double y = random.nextInt(board.getHeight());
            double weight = 1.0/particleCount;

            particles[i] = new Particle(x, y, weight);
        }
    }

    public void addShot(Shot shot)
    {
        applyObservation(shot);
        addNoise();
        particles = resample(0.1);
    }

    private int numberOfPossibilities(Point point, ShipType shipType)
    {
        return board.getBoundingBoxSize(point, shipType);
    }

    private double getProbability(Shot shot, Particle particle)
    {
        Ship shipShotFrom = (new ShipFactory()).getShip(shot.getShipType());
        shipShotFrom.setStartPoint(shot.getPoint(), Direction.DOWN);

        if(!shipShotFrom.isValidShot(new Point(particle.getX().intValue(), particle.getY().intValue())))
            //The shot isn't valid from that location
            return 0.0;
        else
        {
            //Everything is equal probability
            //Should probably changes this in the future to take into account previous shots
            return 1.0/numberOfPossibilities(new Point(particle.getX().intValue(), particle.getY().intValue()), shot.getShipType());
        }
    }

    private void applyObservation(Shot shot)
    {
        double[] weight = new double[particleCount];
        double weightSum = 0.0;

        for(int i=0; i<particleCount; i++)
        {
            double probability = getProbability(shot, particles[i]);
            weight[i] = probability;
            weightSum += probability;
        }

        for(int i=0; i<particleCount; i++)
        {
            particles[i].setWeight(weight[i]/weightSum);
        }
    }

    private void addNoise()
    {
        Random random = new Random();

        for(int i=0; i<particleCount; i++)
        {
            double x = particles[i].getX();
            double y = particles[i].getY();

            double amountToMove = noise * random.nextGaussian();

            if(random.nextInt() % 2 == 0)
                x += amountToMove;
            else
                x -= amountToMove;

            if(random.nextInt() % 2 == 0)
                y += amountToMove;
            else
                y -= amountToMove;

            particles[i].setX(x);
            particles[i].setY(y);
        }
    }

    private Particle[] resample(double confusion)
    {
        Random random = new Random();

        Particle[] newParticles = new Particle[particleCount];
        double [] totalWeight = new double[particleCount];
        totalWeight[0] = particles[0].getWeight();

        for(int i=1; i<particleCount; i++)
            totalWeight[i] = totalWeight[i-1] + particles[i].getWeight();

        double[] u = new double[particleCount];
        u[0] = random.nextDouble()/particleCount;

        int i=0;
        int newParticleIndex = 0;

        for(int j=0; j<particleCount; j++)
        {
            while(u[j] > totalWeight[i])
                i += 1;

            double newX;
            double newY;
            if(random.nextDouble() > confusion)
            {
                newX = particles[i].getX() + 0.5*noise*random.nextGaussian();
                newY = particles[i].getY() + 0.5*noise*random.nextGaussian();
            }
            else
            {
                newX = random.nextInt(board.getWidth());
                newY = random.nextInt(board.getHeight());
            }

            double newWeight = 1.0/particleCount;
            newParticles[newParticleIndex++] = new Particle(newX, newY, newWeight);

            if(j+1 < particleCount)
                u[j+1] = u[j] + (1.0/particleCount);
        }

        return newParticles;
    }

    private Double calculateZScore(double value, double mean, double stdDev)
    {
        return Math.abs((value - mean)/stdDev);
    }

    public Point getMostLikelyLocation()
    {
        double xSum = 0;
        double x2Sum = 0;
        double ySum = 0;
        double y2Sum = 0;

        for(int i=0; i<particleCount; i++)
        {
            double x = particles[i].getX();
            double y = particles[i].getY();

            xSum += x;
            ySum += y;

            x2Sum += x * x;
            y2Sum += y * y;
        }

        double xMean = xSum/particleCount;
        double yMean = ySum/particleCount;

        double xStdDev = Math.sqrt((x2Sum - xSum*xMean)/(particleCount - 1));
        double yStdDev = Math.sqrt((y2Sum - ySum*yMean)/(particleCount - 1));

        Double lowestXzscore = Double.POSITIVE_INFINITY;
        Double lowestYzscore = Double.POSITIVE_INFINITY;
        Double lowestzscore = Double.POSITIVE_INFINITY;

        for(int i=0; i<particleCount; i++)
        {
            double x = particles[i].getX();
            double y = particles[i].getY();

            Double xzscore = calculateZScore(x, xMean, xStdDev);
            Double yzscore = calculateZScore(y, yMean, yStdDev);

            Double zscore = xzscore + yzscore;

            if(zscore < lowestzscore)
            {
                lowestXzscore = x;
                lowestYzscore = y;
            }
        }

        return new Point(lowestXzscore.intValue(), lowestYzscore.intValue());
    }
}
