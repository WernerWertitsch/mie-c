package com.wecreate.miec.base.generic.valueprovider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ListMathFunctionsProvider  {

    @Autowired
    GenericDoubleValueProvider genericDoubleValueProvider;

    public Double getSum(List<Object> source, String valueIdentifier, Class clazz) {
        Double sum = Double.valueOf(0.0);
        source.stream().map(item -> getDoubleValue(source, valueIdentifier, clazz)).reduce( (l,r) -> l + r);
        return sum;
    }

    public Double getPercentage(Object source, String valueIdentifier, Class clazz, double percent) {
        return getDoubleValue(source, valueIdentifier, clazz)*percent/100;
    }

    public Double multiply(Object source, String valueIdentifier, Class clazz, double multiplier) {
        return getDoubleValue(source, valueIdentifier, clazz)*multiplier;
    }

    public Double substractAllFrom(Double startValue, List<Object> subsctractions, String valueIdentifier, Class clazz) {
        return startValue - getSum(subsctractions.subList(1, subsctractions.size()), valueIdentifier, clazz);
    }

    private Double getDoubleValue(Object source, String valueIdentifier, Class clazz) {
        try {
            return genericDoubleValueProvider.getValue(source, valueIdentifier, clazz);
        }catch(Exception e) {
            log.error(String.format("Could not get double value for field %s, for class %s of object %s! Returning 0.0 for sum, RESULT WILL BE WRONG!", valueIdentifier, clazz.toString(), source));
            return 0.0;
        }
    }
}
