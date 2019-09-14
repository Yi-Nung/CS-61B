package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class DrawMenu {
    public static void createMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 35);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.7, "CS61B: THE GAME");
        font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.5, "New Game (N)");
        StdDraw.text(0.5, 0.4, "Load Game (L)");
        StdDraw.text(0.5, 0.3, "Quit Game (Q)");
    }

    public static void createMenu(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 35);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.7, "CS61B: THE GAME");
        font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.5, "New Game (N)");
        StdDraw.text(0.5, 0.4, "Load Game (L)");
        StdDraw.text(0.5, 0.3, "Quit Game (Q)");
    }
}
