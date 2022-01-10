package old;


import old.model.ConditionedSingleValue;

public class SimpleNumber implements ConditionedSingleValue {
    Double value=0.0;

    @Override
    public Double getValue() {
        return value;
    }
}
