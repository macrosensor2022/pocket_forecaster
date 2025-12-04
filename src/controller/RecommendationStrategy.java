package controller;
import java.util.List;
import java.util.Scanner.*;
import model.*;


/**
 * It is interface where it gets the list of phones and the user preference based on it , the strategies are divided and different classes are formed.
 */
public interface RecommendationStrategy {
    List<phone> recommend(List<phone> phones,user_preference pref);

}


