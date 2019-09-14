package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

public class Hud {
    private static final int WIDTH = 90;
    private static final int HEIGHT = 30;

    public static String displayHud(TETile[][] world, double mouseX, double mouseY) {
        String s = "";
        if (world[(int) mouseX][(int) mouseY].equals(Tileset.FLOOR)) {
            s = "FLOOR";
        } else if (world[(int) mouseX][(int) mouseY].equals(Tileset.WALL)) {
            s = "WALL";
        }
        return s;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(90, 35, 0, 5);

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

}
