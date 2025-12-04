import model.user_preference;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * /unit tests for the user_preference class
 * This test verifies :
 * Object creation using parameterized and default constructors
 * Getter and Setter behavior
 * String Representation format
 * Correct updating of preference values
 */
public class TestUserPreference {

    /**
     * Tests the parameterized constructor and validates that
     * all getter methods return the correct initialized values.
     */
    @Test
    public void testConstructorAndGetters() {
        user_preference pref = new user_preference("Gaming", 500, 1500, "Android");
        assertEquals("Gaming", pref.getUsage());
        assertEquals(500, pref.getMinBudget());
        assertEquals(1500, pref.getMaxBudget());
        assertEquals("Android", pref.getOsPreference());
    }

    /**
     * Tests the default constructor by verifying that
     * setter methods correctly update the values and that
     * the getters retrieve the updated values.
     */
    @Test
    public void testDefaultConstructorAndSetters() {
        user_preference pref = new user_preference();
        pref.setUsage("Casual");
        pref.setMinBudget(200);
        pref.setMaxBudget(1000);
        pref.setOsPreference("iOS");

        assertEquals("Casual", pref.getUsage());
        assertEquals(200, pref.getMinBudget());
        assertEquals(1000, pref.getMaxBudget());
        assertEquals("iOS", pref.getOsPreference());
    }


    /**
     * Tests the user_preferencetoString() method to ensure
     * the output format matches the expected
     * usage|minBudget-maxBudget|osPreference
     *
     */
    @Test
    public void testToString() {
        user_preference pref = new user_preference("Productivity", 800, 2000, "No Preference");
        String expected = "Productivity|800.0-2000.0|No Preference";
        assertEquals(expected, pref.toString());
    }


    /**
     * Test updating an existing user_preference object valus using setters
     * and verifies that the modified values are reflected correctly via getters
     */
    @Test
    public void testUpdateValues() {
        user_preference pref = new user_preference("Casual", 300, 1200, "Android");
        pref.setUsage("Gaming");
        pref.setMinBudget(400);
        pref.setMaxBudget(1500);
        pref.setOsPreference("iOS");

        assertEquals("Gaming", pref.getUsage());
        assertEquals(400, pref.getMinBudget());
        assertEquals(1500, pref.getMaxBudget());
        assertEquals("iOS", pref.getOsPreference());
    }


}
