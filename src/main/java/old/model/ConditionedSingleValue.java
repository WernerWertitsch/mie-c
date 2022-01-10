package old.model;

public interface ConditionedSingleValue  {
    default boolean useResult() {
        return true;
    }

    default Double getValue() {
        return 0.0;
    }
}
