package controller;

import model.*;
import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * The RecomendationController class manages the entire recomendation process
 * It : Loads the additional bonus phone suggestion from the CSV file
 * Applies the selected recomendation strategy (strategy pattern)
 * Provides  main , bonus, and additional premium recomendation
 */
public class RecommendationController {

    private RecommendationStrategy strategy;
    private phone_library plibrary;
    private Map<String, List<String>> bonusMap = new HashMap<>();

    /**
     * Creates a RecommendationController with access to the phone library.
     * Also loads bonus suggestions from a CSV file.
     *
     * @param plibrary the phone library containing all phone data
     */

    public RecommendationController(phone_library plibrary) {
        this.plibrary = plibrary;
        loadBonusCSV("bonus_suggestions.csv");
    }
    /**
     * Sets the current strategy for recommendations.
     *
     * @param strategy the recommendation strategy to apply
     */

    public void setStrategy(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }
    /**
     * Loads extra bonus phone suggestions from a CSV file.
     * These suggestions are based on the user's usage type.
     *
     * @param csvFileName CSV file containing bonus phone data
     */
    private void loadBonusCSV(String csvFileName) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(csvFileName);
            if (is == null) throw new RuntimeException(csvFileName + " not found in resources");

            try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
                String[] row;
                reader.readNext(); // skip header
                while ((row = reader.readNext()) != null) {
                    String usage = row[0].trim().toLowerCase();
                    String name = row[2].trim();
                    bonusMap.putIfAbsent(usage, new ArrayList<>());
                    bonusMap.get(usage).add(name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------------------- Main Recommendations ---------------------
    /**
     * Generates phone recommendations using the current strategy
     * and user preferences.
     *
     * @param pref the user's preferences such as usage, OS, and budget
     * @return a list of recommended phones
     */
    public List<phone> getRecommendation(user_preference pref) {
        if (strategy == null) throw new IllegalArgumentException("Strategy not set");
        List<phone> all = plibrary.getallphones();
        return strategy.recommend(all, pref);
    }

    // --------------------- Bonus Suggestions ---------------------
    /**
     * Returns bonus suggestions (phone names) based on the user's usage type.
     * Bonus suggestions come from a separate CSV file.
     *
     * @param pref user preference containing usage type
     * @return list of bonus phone names
     */
    public List<String> getBonusSuggestions(user_preference pref) {
        return bonusMap.getOrDefault(pref.getUsage().toLowerCase(), new ArrayList<>());
    }

    // --------------------- Additional Premium Recommendations ---------------------
    /**
     * Returns additional phone recommendations that fall slightly
     * above the user's budget (up to 15% extra).
     * If none exist, it falls back to the main strategy recommendations.
     *
     * @param pref the user's budget and preferences
     * @return list of additional premium phone options
     */
    public List<phone> getAdditionalRecommendations(user_preference pref) {
        if (strategy == null) throw new IllegalArgumentException("Strategy not set");
        return plibrary.getAdditionalRecommendation(pref, strategy);
    }
}
