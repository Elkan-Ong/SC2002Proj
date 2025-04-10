package Misc.Filter;

import java.util.ArrayList;
import java.util.List;

public class UserFilter implements CreateFilter, ApplyFilter {
    private List<String> neighbourhoods = null;
    private List<String> types = null;
    private long maxPrice;
    private long minPrice;

    public UserFilter() {
        neighbourhoods = new ArrayList<>();
        types = new ArrayList<>();
        maxPrice = -1;
        minPrice = -1;
    }

    public List<String> getNeighbourhoods() {
        return neighbourhoods;
    }

    public void addNeighbourhood(String neighbourhood) {
        this.neighbourhoods.add(neighbourhood);
    }

    public List<String> getTypes() {
        return types;
    }

    public void addType(String type) {
        types.add(type);
    }

    public long getMaxPrice() {
        return maxPrice;
    }

    public long getMinPrice() {
        return minPrice;
    }

    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

}
