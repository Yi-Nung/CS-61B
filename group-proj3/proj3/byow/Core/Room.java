package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static byow.Core.RandomUtils.uniform;

public class Room implements Serializable {
    private static final int WIDTH = 90;
    private static final int HEIGHT = 30;

    private int w;
    private int l;
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    private static List<Room> rooms = new ArrayList<>();

    public Room(int x, int y, int w, int l) {
        this.w = w;
        this.l = l;
        this.x1 = x;
        this.x2 = x + w - 1;
        this.y1 = y;
        this.y2 = y + l - 1;
    }

    public static int[] returnCoord(Room r) {
        int[] coord = new int[6];
        coord[0] = r.x1;
        coord[1] = r.x2;
        coord[2] = r.y1;
        coord[3] = r.y2;
        coord[4] = r.w;
        coord[5] = r.l;
        return coord;
    }

    public static Room createPositionRoom(TETile[][] world, Random random) {
        int w = uniform(random, 8, 12);
        int l = uniform(random, 8, 12);
        int x = uniform(random, 0, WIDTH - w);
        int y = uniform(random, 0, HEIGHT - l);

        while (overlap(world, w, l, x, y)) {
            w = uniform(random, 6, 10);
            l = uniform(random, 6, 10);
            x = uniform(random, 0, WIDTH - w);
            y = uniform(random, 0, HEIGHT - l);
        }
        return new Room(x, y, w, l);
    }

    private static boolean overlap(TETile[][] world, int w, int l, int x, int y) {
        for (int i = x; i < x + w; i += 1) {
            for (int j = y; j < y + l; j += 1) {
                if (!world[i][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void addRoom(TETile[][] world, Room r, TETile wall, TETile floor) {
        int w = r.w;
        int l = r.l;
        int xStart = r.x1;
        int yStart = r.y1;

        for (int i = xStart + 1; i < xStart + w - 1; i += 1) {
            for (int j = yStart + 1; j < yStart + l - 1; j += 1) {
                world[i][j] = floor;
            }
        }
        for (int i = xStart; i < xStart + w; i += 1) {
            world[i][yStart] = wall;
            world[i][yStart + l - 1] = wall;
        }
        for (int j = yStart + 1; j < yStart + l - 1; j += 1) {
            world[xStart][j] = wall;
            world[xStart + w - 1][j] = wall;
        }
        rooms.add(r);
    }

    public static List<Room> generateRooms(TETile[][] world, Random random) {
        int numRooms = uniform(random, 10, 20);
        for (int i = 0; i < numRooms; i += 1) {
            Room r = createPositionRoom(world, random);
            addRoom(world, r, Tileset.WALL, Tileset.FLOOR);
        }
        return rooms;
    }
}
