package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {

/** Tests the plip class
 *  @authr Yi-Nung Huang
 */

    @Test
    public void testReplicate() {
        // TODO
        Clorus c = new Clorus(2);
        Clorus rep = c.replicate();
        assertEquals(1, c.energy(), 0.01);
        assertEquals(1, c.energy(), 0.01);
        assertNotEquals(c, rep);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // If energy >= 1 and any Plips are seen, the Clorus will attack one randomly
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> onePlip = new HashMap<Direction, Occupant>();
        onePlip.put(Direction.TOP, new Plip(2));
        onePlip.put(Direction.BOTTOM, new Empty());
        onePlip.put(Direction.LEFT, new Impassible());
        onePlip.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(onePlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);

        // If energy < 1 and any Plips are seen, the Clorus will attack one randomly
        c = new Clorus(0.2);
        actual = c.chooseAction(onePlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);

        // If energy >= 1 and no Plips; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // If energy >= 1 and no Plips; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);

        // If energy < 1 and no Plips; move to a random empty square.
        c = new Clorus(.99);

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expected, actual);

        // If energy < 1 and no Plips; move to a random empty square.
        c = new Clorus(.99);

        actual = c.chooseAction(allEmpty);
        unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);

    }
}
