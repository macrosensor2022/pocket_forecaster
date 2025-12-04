package model;

/**
 * The user_preference class stores the preferences selected by the user
 * These prefernces includes:
 * usage type(eg:Casual , gaming, business etc)
 * minimum and maximum budget
 * Operating system preferences (Android , IOS or no preference)
 * This information used by recommendation stratergies
 * To filter and suggest the suitable mobile
 */
public class user_preference {

    private String usage;
    private double minBudget;
    private double maxBudget;
    private String osPreference;

    /**
     * Creates a user preference object with all required details.
     *
     * @param usage the type of phone usage (casual, gaming, camera, etc.)
     * @param minBudget the lowest price the user can afford
     * @param maxBudget the highest price the user can afford
     * @param osPreference preferred operating system (Android/iOS/no preference)
     */
    public user_preference(String usage, double minBudget, double maxBudget, String osPreference) {
        this.usage = usage;
        this.minBudget = minBudget;
        this.maxBudget = maxBudget;
        this.osPreference = osPreference;
    }

    /**
     * Default constructor for empty preference
     */
    public user_preference() {}

    /**
     * @return usage type  selected by the user
     */
    public String getUsage() { return usage; }

    /**
     * @return minBudget
     */
    public double getMinBudget() { return minBudget; }

    /**
     * @return maxbudget
     */
    public double getMaxBudget() { return maxBudget; }

    /**
     * @return osPreference (Android/IOS/noPreference)
     */
    public String getOsPreference() { return osPreference; }

    //Sets the usage type
    public void setUsage(String usage) { this.usage = usage; }

    //Sets the minbudget
    public void setMinBudget(double minBudget) { this.minBudget = minBudget; }

    //sets the max budget
    public void setMaxBudget(double maxBudget) { this.maxBudget = maxBudget; }

    //Sets the operating system preference
    public void setOsPreference(String osPreference) { this.osPreference = osPreference; }


    /**
     * Returns a string with the user's preference details.
     */
    @Override
    public String toString() {
        return usage + "|" + minBudget + "-" + maxBudget + "|" + osPreference;
    }
}
