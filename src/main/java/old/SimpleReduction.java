package old;

import old.model.ConditionedSingleValue;

import java.util.Arrays;
import java.util.List;

public class SimpleReduction extends AbstractOperation {

    ConditionedSingleValue startingValue;
    List<ConditionedSingleValue> values;

    public SimpleReduction(ConditionedSingleValue startingValue, ConditionedSingleValue... reductions) {
        this(startingValue, Arrays.asList(reductions));
    }

    public SimpleReduction(ConditionedSingleValue startingValue, List<ConditionedSingleValue> reductions) {
        super("simple reduction");
        this.values=values;
        this.startingValue = startingValue;
    }

    @Override
    public Double getValue() {
        return this.startingValue.getValue() - values.stream().filter(cv -> cv.useResult()).map(cv -> cv.getValue()).reduce(0.0, Double::sum);
    }
}
