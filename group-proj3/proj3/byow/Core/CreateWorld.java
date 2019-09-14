package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class CreateWorld implements Serializable {
    private static final int WIDTH = 90;
    private static final int HEIGHT = 30;


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        List<Room> rooms = Room.generateRooms(world, new Random(877));
        Hallway.connectRooms(rooms, world);

        ter.renderFrame(world);
    }

    public static TETile[][] generateWorld(Random random) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        List<Room> rooms = Room.generateRooms(world, random);
        Hallway.connectRooms(rooms, world);
        return world;
    }



    public static Avatar putAvatar(TETile[][] world){
        for (int j = 0; j < HEIGHT; j += 1) {
            for (int i = 0; i < WIDTH; i += 1) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.AVATAR;
                    return new Avatar(i, j);
                }
            }
        }
        return null;
    }

}