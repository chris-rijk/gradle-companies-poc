package poc.common.auditing;

import java.time.Instant;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;

public class TestDbBase {

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("DB_AUDITING_DRIVER", "org.h2.Driver");
        System.setProperty("DB_AUDITING_URL", "jdbc:h2:mem:nucleus");
        System.setProperty("DB_AUDITING_USER", "sa");
        System.setProperty("DB_AUDITING_PASS", "");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {
    }

    public static void assertInRange(Instant before, Instant between, Instant after) {
        before = before.minusSeconds(2);
        after = after.plusSeconds(2);
        assertTrue(before + " > " + between, between.isAfter(before) || between.equals(before));
        assertTrue(after + " < " + between, between.isBefore(after) || between.equals(after));
    }
}
