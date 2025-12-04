import model.phone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for the {@link phone} class.
 *
 * <p>This test class verifies:
 * <ul>
 *   <li>Correct initialization of all attributes through the constructor.</li>
 *   <li>Accurate return values from all getter methods.</li>
 *   <li>Proper formatting of the {@code toString()} output.</li>
 * </ul>
 */
public class TestPhone {

    /**
     * Tests whether a phone object is created correctly and all getter
     * methods return the values passed to the constructor.
     */
    @Test
    void testPhoneCreation() {

        List<String> reviews = List.of("Great phone");

        phone p = new phone(
                "iphone",
                "Apple",
                999.99,
                "ios",
                "casual",
                "https://apple.com",
                reviews,
                0.95,
                300
        );

        assertEquals("iphone", p.getName());
        assertEquals("Apple", p.getBrand());
        assertEquals(999.99, p.getPrice());
        assertEquals("ios", p.getOs());
        assertEquals("casual", p.getUsage());
        assertEquals(reviews, p.getReviews());   // FIXED → list comparison
        assertEquals("https://apple.com", p.getPurchaseLink());
        assertEquals(0.95, p.getSentiment());
        assertEquals(300, p.getTotalReviews());
    }

    /**
     * Verifies that the {@code toString()} method returns a correctly
     * formatted string where all attributes are pipe-separated.
     */
    @Test
    void testToStringFormat() {

        List<String> reviews = List.of("Great phone");

        phone p = new phone(
                "iphone",
                "Apple",
                999.99,
                "ios",
                "casual",
                "https://apple.com",
                reviews,
                0.95,
                400
        );

        String expected =
                "iphone|Apple|999.99|ios|casual|[Great phone]|https://apple.com|0.95|400";

        assertEquals(expected, p.toString());
    }
}
