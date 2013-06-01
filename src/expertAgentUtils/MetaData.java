package expertAgentUtils;

import ships.Point;

public class MetaData
{
    private Point point;
    private boolean isAttacking;
    private boolean isScanResult;
    private boolean isBestGuess;

    public MetaData(Point point, boolean isAttacking, boolean isScanResult,
            boolean isBestGuess) {
        this.point = point;
        this.isAttacking = isAttacking;
        this.isScanResult = isScanResult;
        this.isBestGuess = isBestGuess;
    }

    public Point getPoint()
    {
        return point;
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
}
