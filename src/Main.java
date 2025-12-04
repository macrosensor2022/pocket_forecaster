import static spark.Spark.*;
import com.google.gson.Gson;
import controller.*;
import model.*;
import view.recom_portal;
import java.util.*;

/**
 * Entry point for the Pocket Forecaster backend server.
 * Uses Spark Java to expose REST API endpoints for smartphone recommendations.
 * Handles  setup, initializes the phone library, and routes requests to
 * the recommendation engine.
 */
public class Main {
    /**
     * Starts the Spark server, configures CORS, initializes components,
     * and defines the /api/recommendations endpoint.
     */
    public static void main(String[] args) {

        port(4567); // Spark server port

        /**
         * -------------------------------------------
         * CORS CONFIGURATION
         * Allows cross-origin requests from frontend
         * -------------------------------------------
         */
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");
            res.header("Access-Control-Allow-Methods", "*");
        });

        /**
         * Initializing the libraries
         */
        phone_library library = new phone_library();
        recom_portal portal = new recom_portal(library);

        /**
         * RECOMMENDATION API ROUTE
         * GET /api/recommendations
         *
         * Returns:
         *  - main recommendations
         *  - additional recommendations
         *  - bonus suggestions
         *
         * Accepts query params:
         *  - minBudget
         *  - maxBudget
         *  - os
         *  - usageChoice
         */
        get("/api/recommendations", (req, res) -> {
            try {
                // Parse query params
                int minBudget = Integer.parseInt(req.queryParams("minBudget"));
                int maxBudget = Integer.parseInt(req.queryParams("maxBudget"));
                String os = req.queryParams("os");
                int usageChoice = Integer.parseInt(req.queryParams("usageChoice"));

                String usage = portal.getUsageString(usageChoice);

                if (os == null || os.isBlank()) os = "No Preference";
                os = os.trim();

                // Create user preferences
                user_preference pref = new user_preference(usage, minBudget, maxBudget, os);

                // Strategy selection
                RecommendationStrategy strategy = switch (usage) {
                    case "Casual" -> new CasualRecom();
                    case "Entertainment" -> new Entertainment();
                    case "Gaming" -> new GamingRecom();
                    case "Photography" -> new PhotographyRecom();
                    case "Productivity" -> new Productivity();
                    default -> new CasualRecom();
                };

                portal.controller.setStrategy(strategy);

                // Main recommendations
                List<phone> mainRecs = portal.controller.getRecommendation(pref);

                // Filter by OS if needed
                if (!os.equalsIgnoreCase("No Preference")) {
                    String finalOs = os.toLowerCase().trim();
                    mainRecs = mainRecs.stream()
                            .filter(p -> p.getOs() != null && p.getOs().toLowerCase().contains(finalOs))
                            .toList();
                }

                // Additional recommendations
                List<phone> additionalRecs = portal.controller.getAdditionalRecommendations(pref);

                // Bonus suggestions
                List<String> bonusRecs = portal.controller.getBonusSuggestions(pref);

                // Prepare JSON output
                Map<String, Object> response = new HashMap<>();
                response.put("main", mainRecs);
                response.put("additional", additionalRecs);
                response.put("bonus", bonusRecs);

                res.type("application/json");
                return new Gson().toJson(response);

            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return new Gson().toJson(Map.of("error", e.getMessage()));
            }
        });
    }
}
