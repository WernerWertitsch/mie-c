package old;

import old.model.ConditionedSingleValue;

public abstract class AbstractOperation implements ConditionedSingleValue {
    String name;
    public AbstractOperation(String name) {
        this.name = name;
    }

}
