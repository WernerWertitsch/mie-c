package old;

import old.model.ConditionedSingleValue;

import java.util.Arrays;
import java.util.List;

public class SimpleDivision extends AbstractOperation {
    List<ConditionedSingleValue> values;

    public SimpleDivision(ConditionedSingleValue... values) {
        this(Arrays.asList(values));
    }

    public SimpleDivision(List<ConditionedSingleValue> values) {
        super("simple-multiplication");
        this.values=values;
    }

    @Override
    public Double getValue() {
        return values.stream().filter(cv -> cv.useResult()).map(cv -> cv.getValue()).reduce(0.0, (a,b) -> a/b);
    }
}
