import model.phone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;

/**
 * Test suite for validating phone related operations such as csv parsing , brand filtering , price calculations, rating analysis*/
public class TestPhoneLibrary {

    /**
     * Helper method to load phone objects from the csv file
     * Expects csv to follow strict column order
     * name, brand, price, os , category , url , features, rating ,stock
     * @param filepath Absolute path of the csv path
     * @return List of phone objects created from the CSV rows
     * @throws IOException if the file cannot be read
     */

    private List<phone> loadPhonesFromCSV(String filepath) throws IOException {
        List<phone> phones = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String line;
        boolean isHeader = true;
        while ((line = br.readLine()) != null) {
            if (isHeader) {
                isHeader = false;
                continue;
            }
            String[] parts = line.split(",");
            String name = parts[0];
            String brand = parts[1];
            double price = Double.parseDouble(parts[2]);
            String os = parts[3];
            String category = parts[4];
            String url = parts[5];
            String features = parts[6];
            double rating = Double.parseDouble(parts[7]);
            int stock = Integer.parseInt(parts[8]);
            phones.add(new phone(name, brand, price, os, category, url, Collections.singletonList(features), rating, stock));
        }
        br.close();
        return phones;
    }
    /**
     * Tests whether the CSV is parsed correctly by validating:
     * - Total number of phones loaded
     * - Name, brand, and price of the first record
     *
     * @throws IOException If CSV file cannot be read
     */
    @Test
    public void testCSVParsing() throws IOException {
        List<phone> phones = loadPhonesFromCSV("D:\\mobile stuffs\\phones.csv");
        assertEquals(5, phones.size(), "CSV should contain exactly 5 phones");

        phone first = phones.get(0);
        assertEquals("iPhone 14", first.getName());
        assertEquals("Apple", first.getBrand());
        assertEquals(1099, first.getPrice(), 0.01);
    }

    /**
     * Tests whether the CSV list contains phones from specific brands.
     * Checks for Samsung and OnePlus. (for instance)
     * @throws IOException If CSV fails to load
     */
    @Test
    public void testFindByBrandInCSVList() throws IOException {
        List<phone> phones = loadPhonesFromCSV("D:\\mobile stuffs\\phones.csv");
        boolean hasSamsung = phones.stream().anyMatch(p -> p.getBrand().equals("Samsung"));
        boolean hasOnePlus = phones.stream().anyMatch(p -> p.getBrand().equals("OnePlus"));

        assertTrue(hasSamsung, "csv list should contain Samsung phone");
        assertTrue(hasOnePlus, "csv list should contain OnePlus phone");
    }

    /**
     * Tests total price calculation for all phones in the CSV.
     * Ensures that sum of all phone prices matches expected output.
     * @throws IOException If CSV cannot be read
     */
    @Test
    public void testTotalPrice() throws IOException {
        List<phone> phones = loadPhonesFromCSV("D:\\mobile stuffs\\phones.csv");
        double totalPrice = phones.stream().mapToDouble(p -> p.getPrice()).sum();
        assertEquals(3795, totalPrice, 0.01, "Total price of all phones should match CSV data");
    }

    /**
     * Tests average rating computation from loaded phone objects.
     * Ensures the calculated average sentiment score is above 80.
     * @throws IOException If CSV fails to load
     */
    @Test
    public void testAverageRating() throws IOException {
        List<phone> phones = loadPhonesFromCSV("D:\\mobile stuffs\\phones.csv");
        double avgRating = phones.stream().mapToDouble(p -> p.getSentiment()).average().orElse(0);
        assertTrue(avgRating > 80, "Average rating should be above 80");
    }




}
