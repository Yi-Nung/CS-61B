package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 90;
    public static final int HEIGHT = 30;
    private long seed;
    private String seedStr = "";
    private Avatar point;
    private TETile[][] result = null;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        TETile[][] finalWorldFrame = null;
        DrawMenu.createMenu();

        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(100);
        }
        char c1 = StdDraw.nextKeyTyped();
        if (c1 == 'N' || c1 == 'n') {
            StdDraw.clear(Color.BLACK);
            userSeed();
            seed = Long.parseLong(seedStr);
            Random random = new Random(seed);
            ter.initialize(90, 35, 0, 5);
            finalWorldFrame = interactWithInputString("N" + seedStr + "S");
            ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);

            while (!StdDraw.hasNextKeyTyped()) {
                ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);
                showType(finalWorldFrame);
                StdDraw.pause(100);
            }

            char c3 = StdDraw.nextKeyTyped();
            while (c3 != ':') {
                movement(point, finalWorldFrame, c3);
                ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);
                while (!StdDraw.hasNextKeyTyped()) {
                    ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);
                    showType(finalWorldFrame);
                    StdDraw.pause(100);
                }
                c3 = StdDraw.nextKeyTyped();
            }

            while (!StdDraw.hasNextKeyTyped()) {
                ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);
                showType(finalWorldFrame);
                StdDraw.pause(100);
            }

            char c4 = StdDraw.nextKeyTyped();
            if (c4 == 'q' || c4 == 'Q') {
                result = finalWorldFrame;
                saveWorld(new SaveGame(finalWorldFrame, point.xPos, point.yPos, seed));
                System.exit(0);
            }
        } else if (c1 == 'L' || c1 == 'l') {
            SaveGame saved = loadWorld();
            int avatarX = saved.avatarPosx;
            int avatarY = saved.avatarPosy;
            finalWorldFrame = CreateWorld.generateWorld(new Random(saved.seed));
            point = new Avatar(avatarX, avatarY);
            finalWorldFrame[avatarX][avatarY] = Tileset.AVATAR;
            ter.initialize(90, 35, 0, 5);
            ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);

            while (!StdDraw.hasNextKeyTyped()) {
                ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);
                showType(finalWorldFrame);
                StdDraw.pause(100);
            }
            char c3 = StdDraw.nextKeyTyped();
            while (c3 != ':') {
                movement(point, finalWorldFrame, c3);
                ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);
                while (!StdDraw.hasNextKeyTyped()) {
                    ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);
                    showType(finalWorldFrame);
                    StdDraw.pause(100);
                }
                c3 = StdDraw.nextKeyTyped();
            }

            while (!StdDraw.hasNextKeyTyped()) {
                ter.renderFrameMy(finalWorldFrame, point.xPos, point.yPos);
                showType(finalWorldFrame);
                StdDraw.pause(100);
            }

            char c4 = StdDraw.nextKeyTyped();
            if (c4 == 'q' || c4 == 'Q') {
                result = finalWorldFrame;
                saveWorld(new SaveGame(finalWorldFrame, point.xPos, point.yPos, seed));
                System.exit(0);
            }
        } else if (c1 == 'Q' || c1 == 'q') {
            System.exit(0);
        }
    }

    private static SaveGame loadWorld() {
        File f = new File("./save.txt");
        if (f.exists()) {
            try {
                System.out.println("txt exist load");
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                SaveGame loadWorld = (SaveGame) os.readObject();
                os.close();
                fs.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        TETile[][] X = CreateWorld.generateWorld(new Random(123));
        Avatar y = CreateWorld.putAvatar(X);
        return new SaveGame(X, y.xPos, y.yPos, 123);
    }
    static class SaveGame implements Serializable {

        private TETile[][] map;
        private int avatarPosx;
        private int avatarPosy;
        private long seed;

        private SaveGame(TETile[][] map, int x, int y, long seed) {
            this.map = map;
            this.avatarPosx = x;
            this.avatarPosy = y;
            this.seed = seed;
        }
    }

    private static void saveWorld(SaveGame x) {
        File f = new File("./save.txt");
        System.out.println("txt exist begin");
        try {
            if (!f.exists()) {
                System.out.println("txt exist");
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            System.out.println("txt exist end");
            os.writeObject(x);
            System.out.println("txt exist end2");
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = null;

        StringInput s = new StringInput(input);
        List<Character> seeds = new ArrayList<>();
        while (s.possibleNextInput()) {
            char c1 = s.getNextKey();
            if (c1 == 'N' || c1 == 'n') {
                if (s.possibleNextInput()) {
                    char c2 = s.getNextKey();
                    while (s.possibleNextInput() && !(c2 == 'S' || c2 == 's')) {
                        seeds.add(c2);
                        c2 = s.getNextKey();
                    }
                    String seedsStr = toStr(seeds);
                    Long seedLong = Long.parseLong(seedsStr);
                    Random r = new Random(seedLong);
                    finalWorldFrame = CreateWorld.generateWorld(r);
                    point = CreateWorld.putAvatar(finalWorldFrame);

                    if (s.possibleNextInput()) {
                        char c3 = s.getNextKey();
                        while (s.possibleNextInput() && c3 != ':') {
                            movement(point, finalWorldFrame, c3);
                            c3 = s.getNextKey();
                        }
                        if (c3 != ':') {
                            movement(point, finalWorldFrame, c3);
                        } else {
                            char c4 = s.getNextKey();
                            if (c4 == 'q' || c4 == 'Q') {
                                result = finalWorldFrame;
                                return finalWorldFrame;
                            }
                        }
                    }
                }
            }
            if (c1 == 'L' || c1 == 'l') {
                SaveGame saved = loadWorld();
                int avatarX = saved.avatarPosx;
                int avatarY = saved.avatarPosy;
                finalWorldFrame = CreateWorld.generateWorld(new Random(saved.seed));
                Avatar point = new Avatar(avatarX, avatarY);
                finalWorldFrame[avatarX][avatarY] = Tileset.AVATAR;

                if (s.possibleNextInput()) {
                    char c3 = s.getNextKey();
                    while (s.possibleNextInput() && c3 != ':') {
                        movement(point, finalWorldFrame, c3);
                        c3 = s.getNextKey();
                    }
                    if (c3 != ':') {
                        movement(point, finalWorldFrame, c3);
                    } else {
                        char c4 = s.getNextKey();
                        if (c4 == 'q' || c4 == 'Q') {
                            result = finalWorldFrame;
                            return finalWorldFrame;
                        }
                    }
                }
            }
        }
        return finalWorldFrame;
    }

    private void showType(TETile[][] world) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(WIDTH / 2, 2.5, WIDTH / 2, 2.5);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.line(0, 3, WIDTH, 3);
        StdDraw.text(50,1.5,DisplayTime.displayTime());
        int currX = (int) StdDraw.mouseX();
        int currY  = (int) StdDraw.mouseY();
        Font font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        try {
            if (world[currX][currY - 5].equals(Tileset.FLOOR)) {
                StdDraw.text(3, 1.5, Tileset.FLOOR.description());
            } else if (world[currX][currY - 5].equals(Tileset.AVATAR)) {
                StdDraw.text(3, 1.5, Tileset.AVATAR.description());
            } else if (world[currX][currY - 5].equals(Tileset.NOTHING)) {
                StdDraw.text(3, 1.5, Tileset.NOTHING.description());
            } else if (world[currX][currY - 5].equals(Tileset.WALL)) {
                StdDraw.text(3, 1.5, Tileset.WALL.description());
            }
            StdDraw.show();
        }
        catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    private static String toStr(List<Character> seedsNS) {
        String result = "";
        for (char i: seedsNS) {
            result += i;
        }
        return result;
    }

    public void userSeed() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(0.5, 0.7, "Your seeds?");
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(100);
        }
        char c1 = StdDraw.nextKeyTyped();
        if (c1 != 's' && c1 != 'S') {
            String i = Character.toString(c1);
            seedStr += i;
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(0.3 + (double) seedStr.length() / 50, 0.5, i);
            userSeed();
        }
    }

    private void movement(Avatar ava, TETile[][] world, char i) {
        if (i == 'w') {
            ava.yPos += 1;
            if (world[ava.xPos][ava.yPos].equals(Tileset.WALL)) {
                ava.yPos -= 1;
                world[ava.xPos][ava.yPos] = Tileset.AVATAR;
                return;
            } else {
                world[ava.xPos][ava.yPos] = Tileset.AVATAR;
                world[ava.xPos][ava.yPos - 1] = Tileset.FLOOR;
            }
        }
        if (i == 'a') {
            ava.xPos -= 1;
            if (world[ava.xPos][ava.yPos].equals(Tileset.WALL)) {
                ava.xPos += 1;
                world[ava.xPos][ava.yPos] = Tileset.AVATAR;
                return;
            } else {
                world[ava.xPos][ava.yPos] = Tileset.AVATAR;
                world[ava.xPos + 1][ava.yPos] = Tileset.FLOOR;
            }
        }
        if (i == 's') {
            ava.yPos -= 1;
            if (world[ava.xPos][ava.yPos].equals(Tileset.WALL)) {
                ava.yPos += 1;
                world[ava.xPos][ava.yPos] = Tileset.AVATAR;
                return;
            } else {
                world[ava.xPos][ava.yPos] = Tileset.AVATAR;
                world[ava.xPos][ava.yPos + 1] = Tileset.FLOOR;
            }
        }
        if (i == 'd') {
            ava.xPos += 1;
            if (world[ava.xPos][ava.yPos].equals(Tileset.WALL)) {
                ava.xPos -= 1;
                world[ava.xPos][ava.yPos] = Tileset.AVATAR;
                return;
            } else {
                world[ava.xPos][ava.yPos] = Tileset.AVATAR;
                world[ava.xPos - 1][ava.yPos] = Tileset.FLOOR;
            }
        }
    }
}
