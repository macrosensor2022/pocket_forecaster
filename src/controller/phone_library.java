package controller;

import model.phone;
import model.store;
import model.user_preference;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.*;

/**
 * phone_library class manages all phones data used in the system
 * Loads the phones from the csv file, stores them in lists and provides helper methods for filtering , recomending , and batching the results
 * This class also works with different recommendation stratergies
 * (strategy pattern) to recommend phones based on user preference
 */
public class phone_library {

    private ArrayList<phone> phones = new ArrayList<>();
    private ArrayList<store> stores = new ArrayList<>();
    private List<phone> recommendation = new ArrayList<>();
    private int batchpointer = 0;

    /**
     * Loads the phone data from a csv file in the resources folder
     * @param csvFileName the csv file name inside the resources directory
     */
    public void loadCSV(String csvFileName) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(csvFileName);
            if (is == null) throw new RuntimeException(csvFileName + " not found in resources");

            try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
                String[] row;
                reader.readNext();

                while ((row = reader.readNext()) != null) {
                    String name = row[0];
                    String brand = row[1];
                    double price = Double.parseDouble(row[2]);
                    String os = row[3];
                    String usage = row[4];

                    List<String> reviewsList = Arrays.asList(row[5].split("\\|\\|"));
                    String purchaseLink = row[6];
                    double sentimentScore = Double.parseDouble(row[7]);
                    int totalReviews = reviewsList.size();

                    phone p = new phone(name, brand, price, os, usage,
                            purchaseLink, reviewsList, sentimentScore, totalReviews);

                    phones.add(p);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor that automatically loads the phone dataset when the library is created
     */

    public phone_library() {
        loadCSV("mobile_phone_1000_rec_sent_score_cleaned.csv");
    }
    /**
     * Returns all phones stored in the library.
     *
     * @return list of all phones
     */
    public List<phone> getallphones() {
        return new ArrayList<>(phones);
    }
    /**
     * Adds  new phone to the database.
     *
     * @param p the phone object to add
     */
    public void addphone(phone p) {
        phones.add(p);
    }
    /**
     * Adds a store to the library.
     *
     * @param s store object containing phones sold by that store
     */
    public void addstore(store s) {
        stores.add(s);
    }
    /**
     * Returns the list of stores that sell a specific phone.
     *
     * @param p the phone object
     * @return list of stores containing that phone
     */
    public List<store> getStoreForPhone(phone p) {
        List<store> available = new ArrayList<>();
        for (store s : stores)
            if (s.getPhones().contains(p)) available.add(s);
        return available;
    }

    // ------------------------- STRATEGY FILTERING -------------------------
    /**
     * Filters and generates recommendations based on the provided strategy.
     *
     * @param pref the user preferences (budget, OS, usage)
     * @param strategy the chosen recommendation strategy
     */
    public void filterWithStrategy(user_preference pref, RecommendationStrategy strategy) {
        recommendation.clear();
        recommendation = strategy.recommend(phones, pref);
        batchpointer = 0;
    }
    /**
     * Returns the next batch of recommended phones.
     *
     * @param batchsize number of phones to return
     * @return a list of phones in the current batch
     */
    public List<phone> getnextbatch(int batchsize) {
        List<phone> batch = new ArrayList<>();
        int end = Math.min(batchpointer + batchsize, recommendation.size());

        for (int i = batchpointer; i < end; i++)
            batch.add(recommendation.get(i));

        batchpointer = end;
        return batch;
    }
    /**
     * Checks if more recommendation batches are available.
     *
     * @return true if more batches remain, otherwise false
     */
    public boolean hasnextbatch() {
        return batchpointer < recommendation.size();
    }

    // ------------------------- ADDITIONAL RECOMMENDATIONS -------------------------
    /**
     * Provides additional phone suggestions slightly above the user's budget.
     * If none are found, it falls back to the strategy's recommendation.
     *
     * @param pref  user preferences
     * @param strategy the recommendation strategy to use
     * @return list of additional recommended phones
     */
    public List<phone> getAdditionalRecommendation(user_preference pref, RecommendationStrategy strategy) {
        List<phone> all = getallphones();
        List<phone> extra = new ArrayList<>();

        double max = pref.getMaxBudget();
        double upper = max * 1.15;

        for (phone p : all) {
            if (p.getPrice() > max && p.getPrice() <= upper) {
                extra.add(p);
            }
        }
        // If no phones found slightly above budget,fallback to main strategy
        if (extra.isEmpty()) {
            extra.addAll(strategy.recommend(all, pref));
        }

        return extra;
    }
}
