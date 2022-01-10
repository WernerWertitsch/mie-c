package old;

import old.model.ConditionedSingleValue;

import java.util.Arrays;
import java.util.List;

public class SimpleAddition extends AbstractOperation {

    List<ConditionedSingleValue> values;

    public SimpleAddition(ConditionedSingleValue... values) {
        this(Arrays.asList(values));
    }

    public SimpleAddition(List<ConditionedSingleValue> values) {
        super("simple-addition");
        this.values=values;
    }

    @Override
    public Double getValue() {
        return values.stream().filter(cv -> cv.useResult()).map(cv -> cv.getValue()).reduce(0.0, Double::sum);
    }
}
