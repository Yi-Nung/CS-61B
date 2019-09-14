package byow.Core;

import java.io.Serializable;

public class Avatar implements Serializable {

    public int xPos;
    public int yPos;

    public Avatar(int x, int y) {
        xPos = x;
        yPos = y;
    }
}