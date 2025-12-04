import controller.phone_library;
import model.phone;
import org.junit.jupiter.api.Test;
import view.recom_portal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Unit tests for the recom_portal class
 * This class helps in validating:
 * usage String conversion
 * Recommendation filtering by range, OS, and budget
 * Review summarization and the count
 * Display the behavior for empty phone lists
 *
 */
public class TestRecomPortal {
    /**
     * Tests the usage mapping method to ensure that
     * input integers return the correct usage category string.
     * Also checks fallback behavior for invalid values.
     */
    @Test
    public void testGetUsageString() {
        recom_portal portal = new recom_portal();
        assertEquals("Casual", portal.getUsageString(1));
        assertEquals("Entertainment", portal.getUsageString(2));
        assertEquals("Gaming", portal.getUsageString(3));
        assertEquals("Photography", portal.getUsageString(4));
        assertEquals("Productivity", portal.getUsageString(5));
        assertEquals("Casual", portal.getUsageString(999)); // default
    }
    /**
     * Tests recommendation behavior when the phone library is empty.
     * The result should always be an empty list.
     */
    @Test
    public void testFindRecommendationsEmptyLibrary() {
        recom_portal portal = new recom_portal(new phone_library());
        List<phone> result = portal.findRecommendationsByRange(100, 1000, "Android", 1);
        assertTrue(result.isEmpty(), "No phones should be returned for empty library");
    }
    /**
     * Tests filtering by OS ("iOS") in addition to price range and usage type.
     * Ensures only matching phones are returned.
     */
    @Test
    public void testFindRecommendationsByOS() {
        phone_library lib = new phone_library();
        lib.addphone(new phone("iPhone 14", "Apple", 1099, "iOS", "productivity",
                "https://buy.com/iphone14", Collections.singletonList("great battery"), 95, 100));
        lib.addphone(new phone("Pixel 7", "Google", 699, "Android", "casual",
                "https://buy.com/pixel7", Collections.singletonList("smooth experience"), 85, 150));

        recom_portal portal = new recom_portal(lib);
        List<phone> result = portal.findRecommendationsByRange(500, 1200, "iOS", 5);
        assertEquals(1, result.size());
        assertEquals("iPhone 14", result.get(0).getName());
    }
    /**
     * Tests filtering by price range alone when OS is not restricted.
     * Ensures only phones within the given budget are returned.
     */
    @Test
    public void testFindRecommendationsByBudget() {
        phone_library lib = new phone_library();
        lib.addphone(new phone("iPhone 14", "Apple", 1099, "iOS", "productivity",
                "https://buy.com/iphone14", Collections.singletonList("great battery"), 95, 100));
        lib.addphone(new phone("Pixel 7", "Google", 699, "Android", "casual",
                "https://buy.com/pixel7", Collections.singletonList("smooth experience"), 85, 150));

        recom_portal portal = new recom_portal(lib);
        List<phone> result = portal.findRecommendationsByRange(600, 800, "No Preference", 1);
        assertEquals(1, result.size());
        assertEquals("Pixel 7", result.get(0).getName());
    }
    /**
     * Tests the review summarization logic.
     * The summary should return the first review from the list.
     */
    @Test
    public void testSummarizeReview() {
        recom_portal portal = new recom_portal();
        phone p = new phone("Pixel 7", "Google", 699, "Android", "casual",
                "https://buy.com/pixel7", Arrays.asList("Amazing phone", "Good camera"), 85, 150);
        String summary = portal.summarizeReview(p);
        assertEquals("Amazing phone", summary);
    }

    /**
     * Tests the method that counts the number of reviews for a phone.
     */
    @Test
    public void testCountReviews() {
        recom_portal portal = new recom_portal();
        phone p = new phone("Pixel 7", "Google", 699, "Android", "casual",
                "https://buy.com/pixel7", Arrays.asList("Amazing phone", "Good camera"), 85, 150);
        int count = portal.countReviews(p);
        assertEquals(2, count);
    }

    /**
     * Tests displaying phones when the list is empty.
     * Expected behavior: prints "No phones available."
     */
    @Test
    public void testDisplayPhonesEmpty() {
        recom_portal portal = new recom_portal();
        portal.displayPhones(Collections.emptyList()); // prints "No phones available."
    }

}
