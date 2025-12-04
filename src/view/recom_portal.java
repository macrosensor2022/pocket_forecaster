package view;

import model.*;
import controller.*;
import java.util.*;

/**
 * Main console interface for the Pocket forecaster application.
 * This class handles user interaction,displays menus
 * recommendation logic to the controller, and prints recommended
 * smartphones based on budget, OS, and usage preferences.
 */
public class recom_portal {

    private phone_library plibrary;
    public RecommendationController controller;
    private List<phone> allphones;
    /**
     * Creates a new recommendation portal with an existing phone library.
     *
     * @param library The phone_library instance containing all phone data.
     */
    public recom_portal(phone_library library) {
        this.plibrary = library;
        this.controller = new RecommendationController(plibrary);
        this.allphones = plibrary.getallphones();
    }

    /**
     * Default constructor that initializes a new phone library.
     */
    public recom_portal() {
        this(new phone_library());
    }

    /**
     * Entry point for the console-based interface.
     */
    public static void main(String[] args) {
        recom_portal portal = new recom_portal();
        portal.run();
    }

    /**
     * Runs the main console loop for user interaction.
     * Displays menu options and handles user choices.
     */
    public void run() {
        Scanner input = new Scanner(System.in);
        boolean running = true;

        System.out.println("--------------------------------------------");
        System.out.println("Welcome to the Pocket Forecaster");
        System.out.println("--------------------------------------------");
        System.out.println("Find your smartphone based on budget, OS, and usage!");

        while (running) {
            System.out.println("\n------- Menu -------");
            System.out.println("1: View all phones");
            System.out.println("2: Find recommended phones");
            System.out.println("3: View store selling the phone");
            System.out.println("4: Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter a number.");
                input.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> displayPhones(allphones);
                case 2 -> handleRecommendations(input);
                case 3 -> viewStoreLink(input);
                case 4 -> {
                    System.out.println("Thank you for using Pocket Forecaster!");
                    running = false;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
        input.close();
    }

    /**
     * Displays a list of phones to the console.
     *
     * @param phones List of phone objects to display.
     */
    public void displayPhones(List<phone> phones) {
        if (phones.isEmpty()) {
            System.out.println("No phones available.");
            return;
        }
        int i = 1;
        for (phone p : phones) {
            System.out.println(i + ". " + p.getBrand() + " " + p.getName() + " – ₹" + p.getPrice() +
                    " | " + p.getOs() + " | Usage: " + p.getUsage());
            i++;
        }
    }

    /**
     * Handles the recommendation flow by collecting user input,
     * generating recommendations, and displaying results.

     */
    private void handleRecommendations(Scanner input) {
        System.out.print("Enter minimum budget: ");
        double minBudget = input.nextDouble();
        System.out.print("Enter maximum budget: ");
        double maxBudget = input.nextDouble();
        input.nextLine(); // consume newline

        System.out.print("Enter OS (Android/iOS/No Preference): ");
        String os = input.nextLine();

        System.out.println("Enter usage (1-5):");
        System.out.println("1. Casual\n2. Entertainment\n3. Gaming\n4. Photography\n5. Productivity");
        int usageChoice = input.nextInt();

        List<phone> recommendations = findRecommendationsByRange(minBudget, maxBudget, os, usageChoice);

        if (recommendations.isEmpty()) {
            System.out.println("No phones found for your criteria.");
        } else {
            System.out.println("\nRecommended Phones:");
            int count = 1;
            for (phone p : recommendations) {
                System.out.println(count + ") " + p.getBrand() + " " + p.getName() + " – ₹" + p.getPrice());
                System.out.println("\"" + summarizeReview((phone) p.getReviews()) + "\"");
                System.out.println("Sentiment: " + p.getSentiment() + "% Positive (" + countReviews(p) + " reviews)");
                System.out.println("Buy → " + p.getPurchaseLink() + "\n");
                count++;
            }

            // Bonus suggestions
            user_preference pref = new user_preference(getUsageString(usageChoice), minBudget, maxBudget, os);
            List<String> bonus = controller.getBonusSuggestions(pref);
            if (!bonus.isEmpty()) {
                System.out.println("Bonus Suggestions (" + getUsageString(usageChoice) + " Edition):");
                for (String item : bonus) {
                    System.out.println(item);
                }
            }
        }
    }

    /**
     * Displays a purchase link for a phone selected by the user.

     */
    public void viewStoreLink(Scanner input) {
        displayPhones(allphones);
        System.out.print("Enter phone number to view store link: ");
        int num = input.nextInt();

        if (num > 0 && num <= allphones.size()) {
            phone selectedMobile = allphones.get(num - 1);
            String link = selectedMobile.getPurchaseLink();
            if (link != null && !link.isEmpty()) {
                System.out.println("Buy " + selectedMobile.getName() + " here: " + link);
            } else {
                System.out.println("No purchase link available.");
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // ---------------------------- USAGE HELPER ----------------------------
    public String getUsageString(int usageChoice) {
        return switch (usageChoice) {
            case 1 -> "Casual";
            case 2 -> "Entertainment";
            case 3 -> "Gaming";
            case 4 -> "Photography";
            case 5 -> "Productivity";
            default -> "Casual";
        };
    }

    /**
     * Generates phone recommendations based on budget, OS, and usage.
     * Applies the appropriate strategy pattern based on usage type.
     * @param minBudget Minimum budget value.
     * @param maxBudget Maximum budget value.
     * @param os OS preference (Android/iOS/No Preference).
     * @param usageChoice Numeric usage selector.
     * @return List of phones matching recommendation criteria.
     */
    public List<phone> findRecommendationsByRange(double minBudget, double maxBudget,
                                                  String os, int usageChoice) {
        String usage = getUsageString(usageChoice);
        String OS = (os != null && !os.isBlank()) ? os.trim() : "No Preference";

        // Create user preference
        user_preference pref = new user_preference(usage, minBudget, maxBudget, OS);

        // Select strategy dynamically
        RecommendationStrategy strategy = switch (usage) {
            case "Casual" -> new CasualRecom();
            case "Entertainment" -> new Entertainment();
            case "Gaming" -> new GamingRecom();
            case "Photography" -> new PhotographyRecom();
            case "Productivity" -> new Productivity();
            default -> new CasualRecom();
        };

        controller.setStrategy(strategy);

        List<phone> recommendation = controller.getRecommendation(pref);
        if (recommendation == null) return new ArrayList<>();

        // Filter by budget and OS one last time
        return recommendation.stream()
                .filter(p -> p.getPrice() >= minBudget && p.getPrice() <= maxBudget)
                .filter(p -> OS.equalsIgnoreCase("No Preference") || p.getOs().equalsIgnoreCase(OS))
                .toList();
    }

    /**
     * Counts total number of reviews for a phone.
     *
     * @param p Phone object.
     * @return Number of reviews available.
     */
    public int countReviews(phone p) {
        if (p.reviews == null) return 0;
        return p.reviews.size();
    }

    /**
     * Returns a short review summary (first review) for the phone.
     * @param p Phone object.
     * @return First review text, or empty string if unavailable.
     */
    public String summarizeReview(phone p) {
        if (p.reviews == null || p.reviews.isEmpty()) return "";
        return p.reviews.get(0);
    }

}
