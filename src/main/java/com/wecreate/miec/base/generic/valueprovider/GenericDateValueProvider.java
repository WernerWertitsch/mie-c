package com.wecreate.miec.base.generic.valueprovider;

import com.wecreate.miec.base.generic.valueprovider.base.ReflectiveValueProvider;

import java.time.LocalDate;

public class GenericDateValueProvider extends ReflectiveValueProvider<Object, LocalDate> {
    //TODO maybe do some ranges here, or some timezone stuff

    @Override
    public LocalDate getValue(Object source, String valueIdentifier, Class<Object> clazz) throws IllegalAccessException {
        return super.getValue(source, valueIdentifier, clazz);
    }

    @Override
    public boolean isValue(Object inputObject, String fullObjectAndValueIdentifier, Class<Object> clazz, LocalDate compareValue) throws IllegalAccessException {
        return getValue(inputObject, fullObjectAndValueIdentifier, clazz).equals(compareValue);
    }
}
