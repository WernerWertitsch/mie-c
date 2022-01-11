package com.wecreate.miec.base.generic.functions;

import com.wecreate.miec.base.generic.valueprovider.GenericDateValueProvider;
import com.wecreate.miec.base.generic.valueprovider.GenericDoubleValueProvider;
import com.wecreate.miec.base.generic.valueprovider.GenericStringValueProvider;
import com.wecreate.miec.base.generic.valueprovider.ListMathFunctionsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Component
public class MathFunctions {
    public static final String SUM_FUNCTION = "sum";
    public static final String SUBSTRACTION_FUNCTION = "substract";
    public static final String STRING_FUNCTION = "string";
    public static final String DOUBLE_FUNCTION = "double";
    public static final String DATE_FUNCTION = "date";


    GenericDateValueProvider genericDateValueProvider;
    GenericDoubleValueProvider genericDoubleValueProvider;
    GenericStringValueProvider genericStringValueProvider;
    ListMathFunctionsProvider listMathFunctionsProvider;

    @Autowired
    public MathFunctions(GenericDateValueProvider genericDateValueProvider, GenericDoubleValueProvider genericDoubleValueProvider, GenericStringValueProvider genericStringValueProvider, ListMathFunctionsProvider listMathFunctionsProvider) {
        this.genericDateValueProvider = genericDateValueProvider;
        this.genericDoubleValueProvider = genericDoubleValueProvider;
        this.genericStringValueProvider = genericStringValueProvider;
        this.listMathFunctionsProvider = listMathFunctionsProvider;;
    }

    public Function<List<Object>, Double> getSumOfField(String fieldIdentifier, Class clazz) {
        return (objects) -> listMathFunctionsProvider.getSum(objects, fieldIdentifier, clazz);
    }

    public Function<List<Object>, Double> substractFromFirst(String fieldIdentifier, Class clazz) {
        return (objects -> listMathFunctionsProvider.substractAllFrom(objects, fieldIdentifier, clazz));
    }

    public Function<Object, String> getStringValue(String fieldIdentifier, Class clazz) {
        return (o) -> genericStringValueProvider.getValue(o, fieldIdentifier, clazz);
    }

    public Function<Object, Double> getDoubleValue(String fieldIdentifier, Class clazz) {
        return o -> genericDoubleValueProvider.getValue(o, fieldIdentifier, clazz);
    }

    public Function<Object, LocalDate> getLocalDateValue(String fieldIdentifier, Class clazz) {
        return o -> genericDateValueProvider.getValue(o, fieldIdentifier, clazz);
    }

}
