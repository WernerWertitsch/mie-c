package old;

public interface ValueSourceProvider<T, K> {
    public T getValueSource(K key);
}
