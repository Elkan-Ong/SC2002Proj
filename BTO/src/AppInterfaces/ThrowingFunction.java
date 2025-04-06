package AppInterfaces;

/**
 * Helper function to read in User data
 * this function is required due to the constructor possibly throwing exception (due to Date objects)
 * also for convenience since all User types are read the same
 * @param <T> generic User type
 */
@FunctionalInterface
public interface ThrowingFunction<T> {
    T apply(String s, String s1, int i, String s2, String s3) throws Exception;
}