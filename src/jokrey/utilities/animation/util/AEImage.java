package jokrey.utilities.animation.util;

//TODO WORK IN PROGESS - NEVER USED IN A PRACTICAL APPLICATION
public class AEImage {
    private final int[][] data;
    public AEImage(int[][] data) {
        this.data=data;
    }

    public int[][] getData() {
        return data;
    }

    public AEColor getColorAt(int x, int y) {
        return new AEColor(getData()[x][y]);
    }

    @Override public int hashCode() {
        int i = 0;
        for(int x=0;x<data.length;x++)
            for(int y=0;y<data[x].length;y++)
                i+=data[x][y];
        return super.hashCode();
    }
}
