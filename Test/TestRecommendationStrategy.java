import model.*;
import controller.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for validating different recommendation strategies used in the phone
 * recommendation system . Each strategy evaluates mobile phones based on user preferences
 * such as category , budget , and operating system
 *
 * The test ensure that:
 * Correct phones are recommended for each category
 * Budget and OS constraints are included
 * Strategies filter and return expected results based on given rules
 */
public class TestRecommendationStrategy {
    /**
     * Tests the Gaming Recommendation Strategy.
     * Ensures that the strategy recommends only gaming phones that match
     * the user's os preference and fall within the specified budget.
     * Expected output: Only the Galaxy S23 matches the criteria.
     */
    @Test
    public void testGamingStrategy() {
        List<String> r1 = List.of("premium");
        List<String> r2 = List.of("high-end");
        List<String> r3 = List.of("smooth");
        List<String> r4 = List.of("premium");
        List<String> r5 = List.of("budget");
        phone p1 = new phone("iPhone 14","Apple",1200,"iOS","casual","https://buy.com/iphone14",r1,90,200);
        phone p2 = new phone("Galaxy S23","Samsung",999,"Android","gaming","https://buy.com/galaxys23",r2,88,300);
        phone p3 = new phone("Pixel 8","Google",799,"Android","casual","https://buy.com/pixel8",r3,80,400);
        phone p4 = new phone("iPhone 14 Pro","Apple",1400,"iOS","gaming","https://buy.com/iphone14pro",r4,95,500);
        phone p5 = new phone("Moto G Power","Motorola",250,"Android","casual","https://buy.com/motogpower",r5,50,600);
        List<phone> allPhones = List.of(p1,p2,p3,p4,p5);
        user_preference pref = new user_preference("gaming", 0, 1000, "Android");
        RecommendationStrategy strategy = new GamingRecom();
        List<phone> recommendation = strategy.recommend(allPhones, pref);
        assertEquals(List.of(p2), recommendation);
    }
    /**
     * Tests the Casual Recommendation Strategy.
     * Ensures the strategy returns no phones when none meet the user's
     * budget, OS, and casual-use preference constraints.
     * Expected output: Empty list.
     */
    @Test
    public void testCasualStrategy() {

        List<String> r1 = List.of("premium");
        List<String> r2 = List.of("high-end");
        List<String> r3 = List.of("smooth");
        List<String> r4 = List.of("premium");
        List<String> r5 = List.of("budget");
        phone p1 = new phone("iPhone 14","Apple",1200,"iOS","casual","https://buy.com/iphone14",r1,90,100);
        phone p2 = new phone("Galaxy S23","Samsung",999,"Android","gaming","https://buy.com/galaxys23",r2,88,200);
        phone p3 = new phone("Pixel 8","Google",799,"Android","casual","https://buy.com/pixel8",r3,80,300);
        phone p4 = new phone("iPhone 14 Pro","Apple",1400,"iOS","gaming","https://buy.com/iphone14pro",r4,95,400);
        phone p5 = new phone("Moto G Power","Motorola",250,"Android","casual","https://buy.com/motogpower",r5,50,500);
        List<phone> allPhones = List.of(p1,p2,p3,p4,p5);
        user_preference pref = new user_preference("casual", 0, 400, "iOS");
        RecommendationStrategy strategy = new CasualRecom();
        List<phone> recommendation = strategy.recommend(allPhones, pref);
        assertEquals(List.of(), recommendation);
    }

    /**
     * Tests the Entertainment Recommendation Strategy.
     * Ensures that the strategy selects the phone classified under
     * entertainment that matches the user's OS preference and budget.
     * Expected output: iPhone 14 Pro.
     */
    @Test
    public void testEntertainmentStrategy() {

        List<String> r1 = List.of("premium");
        List<String> r2 = List.of("high-end");
        List<String> r3 = List.of("smooth");
        List<String> r4 = List.of("premium");
        List<String> r5 = List.of("budget");
        phone p1 = new phone("iPhone 14","Apple",1200,"iOS","casual","https://buy.com/iphone14",r1,90,500);
        phone p2 = new phone("Galaxy S23","Samsung",999,"Android","gaming","https://buy.com/galaxys23",r2,88,600);
        phone p3 = new phone("Pixel 8","Google",799,"Android","casual","https://buy.com/pixel8",r3,80,800);
        phone p4 = new phone("iPhone 14 Pro","Apple",1400,"iOS","entertainment","https://buy.com/iphone14pro",r4,95,80);
        phone p5 = new phone("Moto G Power","Motorola",250,"Android","casual","https://buy.com/motogpower",r5,50,30);
        List<phone> allPhones = List.of(p1,p2,p3,p4,p5);
        user_preference pref = new user_preference("entertainment", 0, 1500, "iOS");
        RecommendationStrategy strategy = new Entertainment();
        List<phone> recommendation = strategy.recommend(allPhones, pref);
        assertEquals(List.of(p4), recommendation);
    }
    /**
     * Tests the Productivity Recommendation Strategy.
     * Ensures that the strategy selects phones suitable for productivity,
     * falling within the user’s specified budget and OS preference.
     * Expected output: Moto G Power.
     */
    @Test
    public void testProductivityStrategy() {

        List<String> r1 = List.of("premium");
        List<String> r2 = List.of("high-end");
        List<String> r3 = List.of("smooth");
        List<String> r4 = List.of("premium");
        List<String> r5 = List.of("budget");
        phone p1 = new phone("iPhone 14","Apple",1200,"iOS","casual","https://buy.com/iphone14",r1,90,100);
        phone p2 = new phone("Galaxy S23","Samsung",999,"Android","gaming","https://buy.com/galaxys23",r2,88,200);
        phone p3 = new phone("Pixel 8","Google",799,"Android","casual","https://buy.com/pixel8",r3,80,300);
        phone p4 = new phone("iPhone 14 Pro","Apple",1400,"iOS","entertainment","https://buy.com/iphone14pro",r4,95,400);
        phone p5 = new phone("Moto G Power","Motorola",250,"Android","productivity","https://buy.com/motogpower",r5,50,500);
        List<phone> allPhones = List.of(p1,p2,p3,p4,p5);
        user_preference pref = new user_preference("productivity", 0, 500, "Android");
        RecommendationStrategy strategy = new Productivity();
        List<phone> recommendation = strategy.recommend(allPhones, pref);
        assertEquals(List.of(p5), recommendation);
    }

    /**
     * Tests the Photography Recommendation Strategy.
     * Ensures that the strategy selects phones marked for photography
     * while matching the OS and price range defined by the user.
     * Expected output: iPhone 14.
     */
    @Test
    public void testPhotographyStrategy() {
        List<String> r1 = List.of("premium");
        List<String> r2 = List.of("high-end");
        List<String> r3 = List.of("smooth");
        List<String> r4 = List.of("premium");
        List<String> r5 = List.of("budget");
        phone p1 = new phone("iPhone 14","Apple",1200,"iOS","photography","https://buy.com/iphone14",r1,90,700);
        phone p2 = new phone("Galaxy S23","Samsung",999,"Android","gaming","https://buy.com/galaxys23",r2,88,600);
        phone p3 = new phone("Pixel 8","Google",799,"Android","casual","https://buy.com/pixel8",r3,80,400);
        phone p4 = new phone("iPhone 14 Pro","Apple",1400,"iOS","entertainment","https://buy.com/iphone14pro",r4,95,500);
        phone p5 = new phone("Moto G Power","Motorola",250,"Android","productivity","https://buy.com/motogpower",r5,50,300);
        List<phone> allPhones = List.of(p1,p2,p3,p4,p5);
        user_preference pref = new user_preference("photography", 0, 1500, "iOS");
        RecommendationStrategy strategy = new PhotographyRecom();
        List<phone> recommendation = strategy.recommend(allPhones, pref);
        assertEquals(List.of(p1), recommendation);
    }
}
