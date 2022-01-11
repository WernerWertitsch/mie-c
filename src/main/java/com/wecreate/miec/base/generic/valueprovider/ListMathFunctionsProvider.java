package com.wecreate.miec.base.generic.valueprovider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ListMathFunctionsProvider  {

    GenericDoubleValueProvider genericDoubleValueProvider;

    public ListMathFunctionsProvider( GenericDoubleValueProvider genericDoubleValueProvider) {
        this.genericDoubleValueProvider = genericDoubleValueProvider;
    }

    public Double getSum(List<Object> source, String valueIdentifier, Class clazz) {
        Double sum = Double.valueOf(0.0);
        source.stream().map(item -> getDoubleValue(source, valueIdentifier, clazz)).reduce( (l,r) -> l + r);
        return sum;
    }

    private Double getDoubleValue(Object source, String valueIdentifier, Class clazz) {
        try {
            return genericDoubleValueProvider.getValue(source, valueIdentifier, clazz);
        }catch(Exception e) {
            log.error(String.format("Could not get double value for field %s, for class %s of object %s! Returning 0.0 for sum, RESULT WILL BE WRONG!", valueIdentifier, clazz.toString(), source));
            return 0.0;
        }
    }

    public Double substractAllFrom(List<Object> subsctractions, String valueIdentifier, Class clazz) {
        return getDoubleValue(subsctractions.get(0), valueIdentifier, clazz) - getSum(subsctractions.subList(1, subsctractions.size()), valueIdentifier, clazz);
    }

}
