package domain.room;

import domain.room.pattern.Pattern;

public class Cover {

    private int shiftX;
    private int shiftY;
    private Pattern pattern;
    private TileType tileType;
    private Grout grout;
    private boolean startsWithFullTile;

    public Cover(int shiftX, int shiftY, Pattern pattern, TileType tileType, Grout grout, boolean startsWithFullTile)
    {
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        this.pattern = pattern;
        this.tileType = tileType;
        this.grout = grout;
        this.startsWithFullTile = startsWithFullTile;
    }

    public Cover(Cover coverToCopy) {
        this.shiftX = new Integer(coverToCopy.shiftX);
        this.shiftY = new Integer(coverToCopy.shiftY);

        // TODO make pattern class constructor accessible
        this.pattern = coverToCopy.pattern;

        this.tileType = new TileType(coverToCopy.tileType);
        this.grout = new Grout(coverToCopy.grout);
        this.startsWithFullTile = new Boolean(coverToCopy.startsWithFullTile);
    }

    /*
    public static Cover createCoverWithDefaultParameters()
    {
        Pattern pattern = new DefaultPattern();
        TileType tileType = TileType.createTileWithDefaultParameters();
        Grout grout = Grout.createGroutWithDefaultParameters();
        return new Cover(0, 0, pattern, tileType, grout, true);
    }

     */

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
        System.out.println(pattern);
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
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
