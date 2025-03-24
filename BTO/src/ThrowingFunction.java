@FunctionalInterface
// This is required due to checked exceptions (Java forces you to handle them if there is a possibility they appear)
// Java's functional interface Function<T, R> does not allow you to handle these exceptions
// T here will be used to call the constructor of the Class we want to create.
public interface ThrowingFunction<T> {
    T apply(String[] x) throws Exception;
}