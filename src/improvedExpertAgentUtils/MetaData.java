package improvedExpertAgentUtils;

import java.util.List;

import ships.Point;

public class MetaData
{
    private List<Point> points;
    private boolean isAttacking;
    private boolean isScanResult;
    private boolean isBestGuess;

    public MetaData(List<Point> points, boolean isAttacking, boolean isScanResult,
            boolean isBestGuess) {
        this.points = points;
        this.isAttacking = isAttacking;
        this.isScanResult = isScanResult;
        this.isBestGuess = isBestGuess;
    }

    public List<Point> getPoints()
    {
        return points;
    }

    public boolean isAttacking()
    {
        return isAttacking;
    }

    public boolean isScanResult()
    {
        return isScanResult;
    }

    public boolean isBestGuess()
    {
        return isBestGuess;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("points: ");
        for (Point point : points)
        {
            sb.append(point + " _ ");
        }

        sb.append(" is attacking: " + isAttacking + " isScanResult: " + isScanResult
                + " isBestGuess: " + isBestGuess);

        return sb.toString();
    }
}
