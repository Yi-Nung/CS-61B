package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import huglife.HugLifeUtils;

public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * creates a clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /**
     * should always return the color red = 34, green = 0, blue = 231.
     */
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    /**
     * If attacks another creature, gain that creature’s energy.
     */
    public void attack(Creature c) {
        this.energy += c.energy();
    }

    /**
     * Cloruses should lose 0.03 units of energy when moving.
     */
    public void move() {
        energy -= 0.03;
    }


    /**
     * CLoruses should lose 0.01 units of energy when staying.
     */
    public void stay() {
        energy -= 0.01;
    }

    /**
     * Cloruses and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Clorus.
     */
    public Clorus replicate() {
        this.energy = this.energy * 0.5;
        return new Clorus(this.energy);
    }

    /**
     * Cloruses take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if any Plips are seen,
     * the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one,
     * it will REPLICATE to a random empty square.
     * 4. Otherwise, the Clorus will MOVE to a random empty square.
     * <p>
     * Returns an object of type Action.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("empty")) {
                emptyNeighbors.addFirst(dir);
            }
            if (neighbors.get(dir).name().equals("plip")) {
                plipNeighbors.addFirst(dir);
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        else if (plipNeighbors.size() > 0) {
            return new Action(Action.ActionType.ATTACK, huglife.HugLifeUtils.randomEntry(plipNeighbors));
        }

        // Rule 3
        else if (energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, huglife.HugLifeUtils.randomEntry(emptyNeighbors));
        }


        // Rule 4
        return new Action(Action.ActionType.MOVE, huglife.HugLifeUtils.randomEntry(emptyNeighbors));
    }
}
