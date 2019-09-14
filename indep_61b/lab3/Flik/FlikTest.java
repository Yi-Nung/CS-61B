import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    @Test
    public void testFlik() {
        assertTrue(Flik.isSameNumber(500, 500));
        assertTrue(Flik.isSameNumber(-2, -2));
        assertTrue(Flik.isSameNumber(0, 0));
    }

}
