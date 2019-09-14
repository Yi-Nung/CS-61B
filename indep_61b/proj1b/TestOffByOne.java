import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('c', 'd'));
        assertTrue(offByOne.equalChars('d', 'c'));
        assertTrue(offByOne.equalChars('%', '&'));
        assertTrue(offByOne.equalChars('C', 'D'));
        assertFalse(offByOne.equalChars('d', 'd'));
        assertFalse(offByOne.equalChars('j', 'd'));
        assertFalse(offByOne.equalChars('c', 'D'));
    }
}
