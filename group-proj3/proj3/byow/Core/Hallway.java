package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;

public class Hallway {
    public static void createHallway(TETile[][] world, Room a, Room b) {
        int[] coordA = Room.returnCoord(a);
        int[] coordB = Room.returnCoord(b);

        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;

        // IF OVERLAP
        // if B's left x is between A's x
        if (coordA[0] <= coordB[0] && coordB[0] <= coordA[1] - 3) {
            whenBLeftXBetweenAX(world, coordA, coordB);
            return;
        }

        // if B's right x is between A's x
        if (coordA[0] + 3 <= coordB[1] && coordB[1] <= coordA[1]) {
            whenBRightXBetweenAX(world, coordA, coordB);
            return;
        }

        // if B's lower y is between A's y
        if (coordA[2] <= coordB[2] && coordB[2] <= coordA[3] - 3) {
            whenBLowerYBetweenAY(world, coordA, coordB);
            return;
        }

        // if B's upper y is between A's y
        if (coordA[2] + 3 <= coordB[3] && coordB[3] <= coordA[3]) {
            whenBUpperYBetweenAY(world, coordA, coordB);
            return;
        }

        // IF B is bigger than A
        // if B is either on left or right of A
        if (upperB >= upperA - 2 && lowerB <= lowerA + 2) {
            whenBLeftRightOfA(world, coordA, coordB);
            return;
        }
        // if B is either above or below A
        if (rightB >= rightA - 2 && leftB <= leftA + 2) {
            whenBAboveBelowA(world, coordA, coordB);
            return;
        }

        // IF NOT OVERLAP
        // if B is to the right of A
        if (leftB > leftA) {
            notOverBRightOfA(world, coordA, coordB);
            return;
        }

        // if B is to the left of A
        if (leftB < leftA) {
            notOverBLeftOfA(world, coordA, coordB);
            return;
        }
    }


    private static void whenBLeftXBetweenAX(TETile[][] world, int[] coordA, int[] coordB) {
        // if B is below A
        if (coordB[3] < coordA[2]) {
            for (int j = coordB[3]; j <= coordA[2]; j += 1) {
                world[coordB[0]][j] = Tileset.WALL;
                world[coordB[0] + 1][j] = Tileset.FLOOR;
                world[coordB[0] + 2][j] = Tileset.WALL;
            }
        }
        // if B is above A
        if (coordB[2] > coordA[3]) {
            for (int j = coordA[3]; j <= coordB[2]; j += 1) {
                world[coordB[0]][j] = Tileset.WALL;
                world[coordB[0] + 1][j] = Tileset.FLOOR;
                world[coordB[0] + 2][j] = Tileset.WALL;
            }
        }
    }

    private static void whenBRightXBetweenAX(TETile[][] world, int[] coordA, int[] coordB) {
        // if B is below A
        if (coordB[3] < coordA[2]) {
            for (int j = coordB[3]; j <= coordA[2]; j += 1) {
                world[coordB[1]][j] = Tileset.WALL;
                world[coordB[1] - 1][j] = Tileset.FLOOR;
                world[coordB[1] - 2][j] = Tileset.WALL;
            }
        }
        // if B is above A
        if (coordB[2] > coordA[3]) {
            for (int j = coordA[3]; j <= coordB[2]; j += 1) {
                world[coordB[1]][j] = Tileset.WALL;
                world[coordB[1] - 1][j] = Tileset.FLOOR;
                world[coordB[1] - 2][j] = Tileset.WALL;
            }
        }
    }

    private static void whenBLowerYBetweenAY(TETile[][] world, int[] coordA, int[] coordB) {
        // if B is to the right of A
        if (coordB[0] > coordA[1]) {
            for (int i = coordA[1]; i <= coordB[0]; i += 1) {
                world[i][coordB[2]] = Tileset.WALL;
                world[i][coordB[2] + 1] = Tileset.FLOOR;
                world[i][coordB[2] + 2] = Tileset.WALL;
            }
        }
        // if B is to the left of A
        if (coordB[1] < coordA[0]) {
            for (int i = coordB[1]; i <= coordA[0]; i += 1) {
                world[i][coordB[2]] = Tileset.WALL;
                world[i][coordB[2] + 1] = Tileset.FLOOR;
                world[i][coordB[2] + 2] = Tileset.WALL;
            }
        }
    }

    private static void whenBUpperYBetweenAY(TETile[][] world, int[] coordA, int[] coordB) {
        // if B is to the right of A
        if (coordB[0] > coordA[1]) {
            for (int i = coordA[1]; i <= coordB[0]; i += 1) {
                world[i][coordB[3]] = Tileset.WALL;
                world[i][coordB[3] - 1] = Tileset.FLOOR;
                world[i][coordB[3] - 2] = Tileset.WALL;
            }
        }
        // if B is to the left of A
        if (coordB[1] < coordA[0]) {
            for (int i = coordB[1]; i <= coordA[0]; i += 1) {
                world[i][coordB[3]] = Tileset.WALL;
                world[i][coordB[3] - 1] = Tileset.FLOOR;
                world[i][coordB[3] - 2] = Tileset.WALL;
            }
        }
    }

    private static void whenBLeftRightOfA(TETile[][] world, int[] coordA, int[] coordB) {
        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;

        // if B is to the right of A
        if (leftB > rightA) {
            for (int i = rightA; i <= leftB; i += 1) {
                world[i][yMidA - 1] = Tileset.WALL;
                world[i][yMidA] = Tileset.FLOOR;
                world[i][yMidA + 1] = Tileset.WALL;
            }
        }
        // if B is to the left of A
        if (rightB < leftA) {
            for (int i = rightB; i <= leftA; i += 1) {
                world[i][yMidA - 1] = Tileset.WALL;
                world[i][yMidA] = Tileset.FLOOR;
                world[i][yMidA + 1] = Tileset.WALL;
            }
        }
    }

    private static void whenBAboveBelowA(TETile[][] world, int[] coordA, int[] coordB) {
        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;

        // if B is above A
        if (lowerB > upperA) {
            for (int j = upperA; j <= lowerB; j += 1) {
                world[xMidA - 1][j] = Tileset.WALL;
                world[xMidA][j] = Tileset.FLOOR;
                world[xMidA + 1][j] = Tileset.WALL;
            }
        }
        // if B is below A
        if (upperB < lowerA) {
            for (int j = upperB; j <= lowerA; j += 1) {
                world[xMidA - 1][j] = Tileset.WALL;
                world[xMidA][j] = Tileset.FLOOR;
                world[xMidA + 1][j] = Tileset.WALL;
            }
        }
    }

    private static void notOverBRightOfA(TETile[][] world, int[] coordA, int[] coordB) {
        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;


        // if B is above A
        if (lowerB > lowerA) {
            for (int j = yMidA - 1; j <= lowerB; j += 1) {
                world[xMidB - 1][j] = Tileset.WALL;
                world[xMidB][j] = Tileset.FLOOR;
                world[xMidB + 1][j] = Tileset.WALL;
            }
            for (int i = rightA; i <= xMidB - 1; i += 1) {
                world[i][yMidA - 1] = Tileset.WALL;
                world[i][yMidA] = Tileset.FLOOR;
                world[i][yMidA + 1] = Tileset.WALL;
            }
            world[xMidB][yMidA - 1] = Tileset.WALL;
        }
        // if B is below A
        if (lowerB < lowerA) {
            for (int j = upperB; j <= yMidA + 1; j += 1) {
                world[xMidB - 1][j] = Tileset.WALL;
                world[xMidB][j] = Tileset.FLOOR;
                world[xMidB + 1][j] = Tileset.WALL;
            }
            for (int i = rightA; i <= xMidB - 1; i += 1) {
                world[i][yMidA - 1] = Tileset.WALL;
                world[i][yMidA] = Tileset.FLOOR;
                world[i][yMidA + 1] = Tileset.WALL;
            }
            world[xMidB][yMidA + 1] = Tileset.WALL;
        }
    }

    private static void notOverBLeftOfA(TETile[][] world, int[] coordA, int[] coordB) {
        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;

        // if B is above A
        if (lowerB > lowerA) {
            for (int j = yMidA - 1; j <= lowerB; j += 1) {
                world[xMidB - 1][j] = Tileset.WALL;
                world[xMidB][j] = Tileset.FLOOR;
                world[xMidB + 1][j] = Tileset.WALL;
            }
            for (int i = xMidB + 1; i <= leftA; i += 1) {
                world[i][yMidA - 1] = Tileset.WALL;
                world[i][yMidA] = Tileset.FLOOR;
                world[i][yMidA + 1] = Tileset.WALL;
            }
            world[xMidB][yMidA - 1] = Tileset.WALL;
        }
        // if B is below A
        if (lowerB < lowerA) {
            for (int j = upperB; j <= yMidA + 1; j += 1) {
                world[xMidB - 1][j] = Tileset.WALL;
                world[xMidB][j] = Tileset.FLOOR;
                world[xMidB + 1][j] = Tileset.WALL;
            }
            for (int i = xMidB + 1; i <= leftA; i += 1) {
                world[i][yMidA - 1] = Tileset.WALL;
                world[i][yMidA] = Tileset.FLOOR;
                world[i][yMidA + 1] = Tileset.WALL;
            }
            world[xMidB][yMidA + 1] = Tileset.WALL;
        }
    }


    public static boolean checkIntersect(TETile[][] world, Room a, Room b) {
        int[] coordA = Room.returnCoord(a);
        int[] coordB = Room.returnCoord(b);

        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;

        // IF OVERLAP
        // if B's left x is between A's x
        if (coordA[0] <= coordB[0] && coordB[0] <= coordA[1] - 3) {
            return whenBLeftXBetweenAXCheck(world, coordA, coordB);
        }

        // if B's right x is between A's x
        if (coordA[0] + 3 <= coordB[1] && coordB[1] <= coordA[1]) {
            return whenBRightXBetweenAXCheck(world, coordA, coordB);
        }

        // if B's lower y is between A's y
        if (coordA[2] <= coordB[2] && coordB[2] <= coordA[3] - 3) {
            return whenBLowerYBetweenAYCheck(world, coordA, coordB);
        }

        // if B's upper y is between A's y
        if (coordA[2] + 3 <= coordB[3] && coordB[3] <= coordA[3]) {
            return whenBUpperYBetweenAYCheck(world, coordA, coordB);
        }

        // IF B is bigger than A
        // if B is either on left or right of A
        if (upperB >= upperA - 2 && lowerB <= lowerA + 2) {
            // if B is to the right of A
            return whenBLeftRightOfACheck(world, coordA, coordB);
        }

        // if B is either above or below A
        if (rightB >= rightA - 2 && leftB <= leftA + 2) {
            return whenBAboveBelowACheck(world, coordA, coordB);
        }

        // IF NOT OVERLAP
        // if B is to the right of A
        if (leftB > leftA) {
            return notOverBRightOfACheck(world, coordA, coordB);
        }

        // if B is to the left of A
        if (leftB < leftA) {
            return notOverBLeftOfACheck(world, coordA, coordB);
        }
        return false;
    }

    private static boolean whenBLeftXBetweenAXCheck(TETile[][] world, int[] coordA, int[] coordB) {
        if (coordB[3] < coordA[2]) {
            for (int j = coordB[3] + 1; j <= coordA[2] - 1; j += 1) {
                if (!world[coordB[0]][j].equals(Tileset.NOTHING)
                        || !world[coordB[0] + 1][j].equals(Tileset.NOTHING)
                        || !world[coordB[0] + 2][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        // if B is above A
        if (coordB[2] > coordA[3]) {
            for (int j = coordA[3] + 1; j <= coordB[2] - 1; j += 1) {
                if (!world[coordB[0]][j].equals(Tileset.NOTHING)
                        || !world[coordB[0] + 1][j].equals(Tileset.NOTHING)
                        || !world[coordB[0] + 2][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean whenBRightXBetweenAXCheck(TETile[][] world, int[] coordA, int[] coordB) {
        // if B is below A
        if (coordB[3] < coordA[2]) {
            for (int j = coordB[3] + 1; j <= coordA[2] - 1; j += 1) {
                if (!world[coordB[1]][j].equals(Tileset.NOTHING)
                        || !world[coordB[1] - 1][j].equals(Tileset.NOTHING)
                        || !world[coordB[1] - 2][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        // if B is above A
        if (coordB[2] > coordA[3]) {
            for (int j = coordA[3] + 1; j <= coordB[2] - 1; j += 1) {
                if (!world[coordB[1]][j].equals(Tileset.NOTHING)
                        || !world[coordB[1] - 1][j].equals(Tileset.NOTHING)
                        || !world[coordB[1] - 2][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean whenBLowerYBetweenAYCheck(TETile[][] world, int[] coordA, int[] coordB) {
    // if B is to the right of A
        if (coordB[0] > coordA[1]) {
            for (int i = coordA[1] + 1; i <= coordB[0] - 1; i += 1) {
                if (!world[i][coordB[2]].equals(Tileset.NOTHING)
                        || !world[i][coordB[2] + 1].equals(Tileset.NOTHING)
                        || !world[i][coordB[2] + 2].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        // if B is to the left of A
        if (coordB[1] < coordA[0]) {
            for (int i = coordB[1] + 1; i <= coordA[0] - 1; i += 1) {
                if (!world[i][coordB[2]].equals(Tileset.NOTHING)
                        || !world[i][coordB[2] + 1].equals(Tileset.NOTHING)
                        || !world[i][coordB[2] + 2].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean whenBUpperYBetweenAYCheck(TETile[][] world, int[] coordA, int[] coordB) {
        if (coordB[0] > coordA[1]) {
            for (int i = coordA[1] + 1; i <= coordB[0] - 1; i += 1) {
                if (!world[i][coordB[3]].equals(Tileset.NOTHING)
                        || !world[i][coordB[3] - 1].equals(Tileset.NOTHING)
                        || !world[i][coordB[3] - 2].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        // if B is to the left of A
        if (coordB[1] < coordA[0]) {
            for (int i = coordB[1] + 1; i <= coordA[0] - 1; i += 1) {
                if (!world[i][coordB[3]].equals(Tileset.NOTHING)
                        || !world[i][coordB[3] - 1].equals(Tileset.NOTHING)
                        || !world[i][coordB[3] - 2].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;

    }
    private static boolean whenBLeftRightOfACheck(TETile[][] world, int[] coordA, int[] coordB) {
        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;
        if (leftB > rightA) {
            for (int i = rightA + 1; i <= leftB - 1; i += 1) {
                if (!world[i][yMidA - 1].equals(Tileset.NOTHING)
                        || !world[i][yMidA].equals(Tileset.NOTHING)
                        || !world[i][yMidA + 1].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        // if B is to the left of A
        if (rightB < leftA) {
            for (int i = rightB + 1; i <= leftA - 1; i += 1) {
                if (!world[i][yMidA - 1].equals(Tileset.NOTHING)
                        || !world[i][yMidA].equals(Tileset.NOTHING)
                        || !world[i][yMidA + 1].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean whenBAboveBelowACheck(TETile[][] world, int[] coordA, int[] coordB) {
    // if B is above A
        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;
        if (lowerB > upperA) {
            for (int j = upperA + 1; j <= lowerB - 1; j += 1) {
                if (!world[xMidA - 1][j].equals(Tileset.NOTHING)
                        || !world[xMidA][j].equals(Tileset.NOTHING)
                        || !world[xMidA + 1][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        // if B is below A
        if (upperB < lowerA) {
            for (int j = upperB + 1; j <= lowerA - 1; j += 1) {
                if (!world[xMidA - 1][j].equals(Tileset.NOTHING)
                        || !world[xMidA][j].equals(Tileset.NOTHING)
                        || !world[xMidA + 1][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean notOverBRightOfACheck(TETile[][] world, int[] coordA, int[] coordB) {
        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;

        // if B is above A
        if (lowerB > lowerA) {
            for (int j = yMidA - 1; j <= lowerB - 1; j += 1) {
                if (!world[xMidB - 1][j].equals(Tileset.NOTHING)
                        || !world[xMidB][j].equals(Tileset.NOTHING)
                        || !world[xMidB + 1][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
            for (int i = rightA + 1; i <= xMidB - 2; i += 1) {
                if (!world[i][yMidA - 1].equals(Tileset.NOTHING)
                        || !world[i][yMidA].equals(Tileset.NOTHING)
                        || !world[i][yMidA + 1].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        // if B is below A
        if (lowerB < lowerA) {
            for (int j = upperB + 1; j <= yMidA + 1; j += 1) {
                if (!world[xMidB - 1][j].equals(Tileset.NOTHING)
                        || !world[xMidB][j].equals(Tileset.NOTHING)
                        || !world[xMidB + 1][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
            for (int i = rightA + 1; i <= xMidB - 2; i += 1) {
                if (!world[i][yMidA - 1].equals(Tileset.NOTHING)
                        || !world[i][yMidA].equals(Tileset.NOTHING)
                        || !world[i][yMidA + 1].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean notOverBLeftOfACheck(TETile[][] world, int[] coordA, int[] coordB) {
        int wA = coordA[4];
        int lA = coordA[5];
        int leftA = coordA[0];
        int rightA = coordA[1];
        int lowerA = coordA[2];
        int upperA = coordA[3];

        int wB = coordB[4];
        int lB = coordB[5];
        int leftB = coordB[0];
        int rightB = coordB[1];
        int lowerB = coordB[2];
        int upperB = coordB[3];

        int xMidB = leftB + wB / 2;
        int yMidA = lowerA + lA / 2;
        int xMidA = leftA + wA / 2;
        // if B is above A
        if (lowerB > lowerA) {
            for (int j = yMidA - 1; j <= lowerB - 1; j += 1) {
                if (!world[xMidB - 1][j].equals(Tileset.NOTHING)
                        || !world[xMidB][j].equals(Tileset.NOTHING)
                        || !world[xMidB + 1][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
            for (int i = xMidB + 2; i <= leftA - 1; i += 1) {
                if (!world[i][yMidA - 1].equals(Tileset.NOTHING)
                        || !world[i][yMidA].equals(Tileset.NOTHING)
                        || !world[i][yMidA + 1].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        // if B is below A
        if (lowerB < lowerA) {
            for (int j = upperB + 1; j <= yMidA + 1; j += 1) {
                if (!world[xMidB - 1][j].equals(Tileset.NOTHING)
                        || !world[xMidB][j].equals(Tileset.NOTHING)
                        || !world[xMidB + 1][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
            for (int i = xMidB + 2; i <= leftA - 1; i += 1) {
                if (!world[i][yMidA - 1].equals(Tileset.NOTHING)
                        || !world[i][yMidA].equals(Tileset.NOTHING)
                        || !world[i][yMidA + 1].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }



    public static void connectRooms(List<Room> rooms, TETile[][] world) {
        for (Room a: rooms) {
            for (Room b: rooms) {
                if (!a.equals(b)) {
                    if (!checkIntersect(world, a, b)) {
                        createHallway(world, a, b);
                    }
                }
            }
        }
    }
}
