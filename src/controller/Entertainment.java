package controller;
import java.util.*;
import java.util.stream.Collectors;

import model.*;


/**
 * The Entertainment class provides  phone recommendations for user
 * Who have "entertainment" usage preference
 * It uses the java streams to :
 * Filter phones with usage = "entertainment"
 * match the user's budget range
 * match the OS Preference
 * Finally it helps in sorting the mobiles based on the highest sentiment score(ie the review), returns the top 3 results
 */
public class Entertainment implements RecommendationStrategy {

    /**
     /**
     * Recommends suitable phones for entertainment users based on their budget and OS preference
     * @param phones the full list of available mobiles
     * @param pref the users chosen preferences such as budget and OS
     * @return a list of up to 3 phones that best match the user's needs
     */


    @Override
    public List<phone> recommend(List<phone> phones, user_preference pref) {
        return phones.stream()
                .filter(p -> p.getUsage().equalsIgnoreCase("entertainment"))
                .filter(p -> p.getPrice() >= pref.getMinBudget())
                .filter(p -> p.getPrice() <= pref.getMaxBudget())
                .filter(p -> pref.getOsPreference().equalsIgnoreCase("no preference")
                        || p.getOs().equalsIgnoreCase(pref.getOsPreference()))
                .sorted(Comparator.comparing(phone::getSentiment).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }



}



/**
 * In this particular class i use te java stream interface where makes the list into a sequence of elements and consist of various functionalities , make the process of searching easier.
 */