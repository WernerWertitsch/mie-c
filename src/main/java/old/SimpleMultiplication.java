package old;

import old.model.ConditionedSingleValue;

import java.util.Arrays;
import java.util.List;

public class SimpleMultiplication extends AbstractOperation {
    List<ConditionedSingleValue> values;

    public SimpleMultiplication(ConditionedSingleValue... values) {
        this(Arrays.asList(values));
    }

    public SimpleMultiplication(List<ConditionedSingleValue> values) {
        super("simple-multiplication");
        this.values=values;
    }

    @Override
    public Double getValue() {
        return values.stream().filter(cv -> cv.useResult()).map(cv -> cv.getValue()).reduce(0.0, (a,b) -> a*b);
    }
}
