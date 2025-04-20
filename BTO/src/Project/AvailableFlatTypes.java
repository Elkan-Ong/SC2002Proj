package Project;

/**
 * Stores the available Flat types that are available throughout all projects
 * Note: for expansion: add 4-Room, 5-Room etc.
 * Extra Note: Conditions for eligibility need to be considered separately.
 * While the program supports easy addition of flat types, it does not support easy addition of eligibility criteria.
 */
public interface AvailableFlatTypes {
    /**
     * Array of all available Flat types
     */
    String[] availableTypes = { "2-Room", "3-Room" };
}
