package com.wecreate.miec.base.generic.valueprovider.base;
import org.springframework.util.ReflectionUtils;

public class ReflectiveValueProvider<I,O> implements ValueProvider<I,O> {

    public O getValue(I source, String valueIdentifier, Class<I> clazz) throws IllegalAccessException {
        return (O) ReflectionUtils.findField(clazz, valueIdentifier).get(source);
    }

    @Override
    public boolean isValue(I inputObject, String fullObjectAndValueIdentifier, Class<I> clazz, O compareValue) throws IllegalAccessException {
        return getValue(inputObject, fullObjectAndValueIdentifier, clazz).equals(compareValue);
    }


}
