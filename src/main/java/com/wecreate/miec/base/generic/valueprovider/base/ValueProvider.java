package com.wecreate.miec.base.generic.valueprovider.base;

public interface ValueProvider<I, O> {
    public O getValue(I inputObject, String fullObjectAndValueIdentifier, Class<I> clazz) throws IllegalAccessException;
    public boolean isValue(I inputObject, String fullObjectAndValueIdentifier, Class<I> clazz, O compareValue) throws IllegalAccessException;
}
