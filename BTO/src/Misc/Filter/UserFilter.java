package Misc.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter any User type can create to indicate the projects they are interested in based on
 */
public class UserFilter implements CreateFilter, ApplyFilter {
    /**
     * Neighbourhoods of interest
     */
    private final List<String> neighbourhoods;

    /**
     * Flat types of interest
     */
    private final List<String> types ;

    /**
     * Maximum price of units that User is interested in
     */
    private long maxPrice;

    /**
     * Minimum price of units that User is interested in
     */
    private long minPrice;

    /**
     * Initializes default values of UserFilter
     */
    public UserFilter() {
        neighbourhoods = new ArrayList<>();
        types = new ArrayList<>();
        maxPrice = -1;
        minPrice = -1;
    }

    /**
     * Gets neighbourhoods of interest
     * @return neighbourhoods of interest
     */
    public List<String> getNeighbourhoods() {
        return neighbourhoods;
    }

    /**
     * Adds a neighbourhoods of interest to the list of neighbourhoods
     * @param neighbourhood neighbourhood to be added
     */
    public void addNeighbourhood(String neighbourhood) {
        this.neighbourhoods.add(neighbourhood);
    }

    /**
     * Gets Flat types of interest
     * @return Flat types of interest
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * Adds a Flat type of interest to the list of Flat types
     * @param type Flat type to be added
     */
    public void addType(String type) {
        types.add(type);
    }

    /**
     * Gets max price of Units
     * @return max price of Units
     */
    public long getMaxPrice() {
        return maxPrice;
    }

    /**
     * Gets min price of Units
     * @return min price of Units
     */
    public long getMinPrice() {
        return minPrice;
    }

    /**
     * Updates the max price of the Units
     * @param maxPrice new max price
     */
    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Updates the min price of the Units
     * @param minPrice new min price
     */
    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

}
