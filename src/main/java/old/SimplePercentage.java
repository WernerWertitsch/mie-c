package old;

import old.model.ConditionedSingleValue;

public class SimplePercentage extends AbstractOperation {

    ConditionedSingleValue fullValue;
    ConditionedSingleValue percentage;

    public SimplePercentage(ConditionedSingleValue value, ConditionedSingleValue percentage) {
        super("simple percentage");
        this.fullValue = value;
        this.percentage = percentage;
    }

    @Override
    public Double getValue() {
        return this.fullValue.getValue()*percentage.getValue()/100;
    }
}
