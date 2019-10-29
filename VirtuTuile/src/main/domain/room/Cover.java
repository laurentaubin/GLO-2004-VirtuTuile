package domain.room;

public class Cover {
    public enum Pattern {
        L,
        ANGLE,
        BRIQUE,
        CHEVRON,
        DROIT,
        PAQUET
    }

    private int shiftX;
    private int shiftY;
    private Pattern pattern;
    private Tile tile;
    private Grout grout;
    private boolean startsWithFullTile;

    public Cover(int shiftX, int shiftY, Pattern pattern, Tile tile, Grout grout, boolean startsWithFullTile)
    {
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        this.pattern = pattern;
        this.grout = grout;
        this.startsWithFullTile = startsWithFullTile;
    }

    static Cover createCoverWithDefaultParameters(Pattern pattern, Tile tile, Grout grout)
    {
        return new Cover(0, 0, pattern, tile, grout, true);
    }

    public int getShiftX() {
        return shiftX;
    }

    public void setShiftX(int shiftX) {
        this.shiftX = shiftX;
    }

    public int getShiftY() {
        return shiftY;
    }

    public void setShiftY(int shiftY) {
        this.shiftY = shiftY;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Grout getGrout() {
        return grout;
    }

    public void setGrout(Grout grout) {
        this.grout = grout;
    }

    public boolean isStartsWithFullTile() {
        return startsWithFullTile;
    }

    public void setStartsWithFullTile(boolean startsWithFullTile) {
        this.startsWithFullTile = startsWithFullTile;
    }
}
