package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static class Position {
        private int x;
        private int y;
        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /** Compute the width for rth row from the bottom (r = 0). */
    private static int computeRowWidth(int r, int s) {
        if (r < s) {
            return s + r * 2;
        } else {
            return s + (s - 1) * 2 - (r - s) * 2;
        }
    }

    /** Compute the starting x position for rth row from the bottom. */
    private static int computeStartX(Position p, int r, int s) {
        int x = p.x;
        int y = p.y;
        if (r < s) {
            return x - r;
        } else {
            return x - (s - 1) + (r - s);
        }
    }

    private static void addHexagon(TETile[][] world, Position p, int s, TETile t) {

        for (int r = 0; r < 2 * s; r += 1) {
            int yCoord = p.y + r;
            int StartX = computeStartX(p, r, s);
            int rowWidth = computeRowWidth(r, s);
            for (int i = StartX; i < StartX + rowWidth; i += 1) {
                world[i][yCoord] = TETile.colorVariant(t, 32, 32, 32, new Random(12345));
            }
        }
    }

    public static void main(String[] args) {
        int width = 60;
        int height = 30;

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addHexagon(world, new Position(40, 10), 3, Tileset.FLOWER);


        ter.renderFrame(world);
    }
}
