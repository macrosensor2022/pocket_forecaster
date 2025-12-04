package model;

import java.util.List;

/**
 * The phone class represents a mobile phone with details such as
 * name , brand , price, operating system , usage types and reviews
 * Purchase links , sentiment scores and total number of reviews
 * The model is used throughout the system for recommendation and display purposes
 */
public class phone {

    public String name;
    public String brand;
    public double price;
    public String os;
    public String usage;
    public List<String> reviews; // List instead of String
    public String purchaseLink;
    public double sentiment;
    public int totalReviews;
    /**
     * Creates a phone object with all its details.
     *
     * @param name the name/model of the phone
     * @param brand  the brand
     * @param price  the price of the phone
     * @param os  operating system (e.g., Android, iOS)
     * @param usage recommended usage type (e.g., casual, gaming, camera)
     * @param purchaseLink direct purchase link for the phone
     * @param reviews list of user reviews
     * @param sentiment sentiment score calculated from reviews
     * @param totalReviews total number of reviews available
     */
    public phone(String name, String brand, double price, String os, String usage,
                 String purchaseLink, List<String> reviews, double sentiment, int totalReviews) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.os = os;
        this.usage = usage;
        this.purchaseLink = purchaseLink;
        this.reviews = reviews;
        this.sentiment = sentiment;
        this.totalReviews = totalReviews;
    }


    /** @return list of reviews for the phone */
    public List<String> getReviews() { return reviews; }

    /** @return phone name */
    public String getName() { return name; }

    /** @return phone brand */
    public String getBrand() { return brand; }

    /** @return phone price */
    public double getPrice() { return price; }

    /** @return operating system */
    public String getOs() { return os; }

    /** @return usage type (casual, gaming, business, etc.) */
    public String getUsage() { return usage; }

    /** @return purchase link URL */
    public String getPurchaseLink() { return purchaseLink; }

    /** @return sentiment score of the phone */
    public double getSentiment() { return sentiment; }

    /** @return total number of user reviews */
    public int getTotalReviews() { return totalReviews; }

    /**
     * Returns a string containing all phone details in a compact format.
     */
    @Override
    public String toString() {
        return name + "|" + brand + "|" + price + "|" + os + "|" + usage + "|" +
                reviews + "|" + purchaseLink + "|" + sentiment + "|" + totalReviews;
    }
}
