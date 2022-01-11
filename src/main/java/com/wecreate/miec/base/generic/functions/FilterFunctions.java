package com.wecreate.miec.base.generic.functions;

import com.wecreate.miec.base.generic.valueprovider.GenericDateValueProvider;
import com.wecreate.miec.base.generic.valueprovider.GenericDoubleValueProvider;
import com.wecreate.miec.base.generic.valueprovider.GenericStringValueProvider;
import com.wecreate.miec.base.generic.valueprovider.ListMathFunctionsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class FilterFunctions {

    public static final String STRING_CONTAINS_FILTER = "contains";
    public static final String DATE_BETWEEN = "dateBetween";
    public static final String IS_WEEKDAY = "isWeekDay";


    GenericDateValueProvider genericDateValueProvider;
    GenericDoubleValueProvider genericDoubleValueProvider;
    GenericStringValueProvider genericStringValueProvider;
    ListMathFunctionsProvider listMathFunctionsProvider;

    @Autowired
    public FilterFunctions(GenericDateValueProvider genericDateValueProvider, GenericDoubleValueProvider genericDoubleValueProvider, GenericStringValueProvider genericStringValueProvider, ListMathFunctionsProvider listMathFunctionsProvider) {
        this.genericDateValueProvider = genericDateValueProvider;
        this.genericDoubleValueProvider = genericDoubleValueProvider;
        this.genericStringValueProvider = genericStringValueProvider;
        this.listMathFunctionsProvider = listMathFunctionsProvider;;
    }

    public Function<Object, Boolean> fieldContains(String value, String fieldIdentifier, Class clazz ) {
        return (object) -> genericStringValueProvider.getValue(new Object(), fieldIdentifier, clazz).contains(value);
    }

    public Function<Object, Boolean> fieldBetween(LocalDate valueFrom, LocalDate valueTo, String fieldIdentifier, Class clazz ) {
        return (object) -> {
            LocalDate value = genericDateValueProvider.getValue(new Object(), fieldIdentifier, clazz);
            return value.isAfter(valueFrom) && value.isBefore(valueTo);
        };
    }

    public Function<Object, Boolean> isInWeekDays(List<Integer> days, String fieldIdentifier, Class clazz) {
        return (object) -> {
            LocalDate value = genericDateValueProvider.getValue(new Object(), fieldIdentifier, clazz);
            return days.contains(value.getDayOfWeek().getValue());
        };
    }

}
