package Misc.Filter;

import Project.HDBProject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains the method to apply the filter to a list of projects
 */
public interface ApplyFilter {
    /**
     * Applies the filter to a list of projects by checking if a given project meets all the criteria of the filter
     * @param projects List of projects to be filtered
     * @param filter filter to be applied
     * @return list of projects that have been filtered
     */
    default List<HDBProject> applyFilter(List<HDBProject> projects, UserFilter filter) {
        return projects.stream()
                .filter(project -> {
                    // Neighbourhood check
                    boolean neighbourhoodMatch = filter.getNeighbourhoods().isEmpty() ||
                            filter.getNeighbourhoods().contains(project.getNeighbourhood());

                    // Flats check: at least one flat must match all flat-related filters
                    boolean hasMatchingFlat = project.getFlatType().stream().anyMatch(flat -> {
                        boolean flatTypeMatch = filter.getTypes().isEmpty() ||
                                filter.getTypes().contains(flat.getType());
                        boolean minPriceMatch = filter.getMinPrice() == -1 || flat.getPrice() >= filter.getMinPrice();
                        boolean maxPriceMatch = filter.getMaxPrice() == -1 || flat.getPrice() <= filter.getMaxPrice();
                        return flatTypeMatch && minPriceMatch && maxPriceMatch;
                    });

                    return neighbourhoodMatch && hasMatchingFlat;
                })
                .collect(Collectors.toList());
    }
}
