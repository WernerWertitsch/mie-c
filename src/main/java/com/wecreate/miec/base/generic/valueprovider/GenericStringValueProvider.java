package com.wecreate.miec.base.generic.valueprovider;

import com.wecreate.miec.base.generic.valueprovider.base.ReflectiveValueProvider;

public class GenericStringValueProvider extends ReflectiveValueProvider<Object, String> {
    @Override
    public String getValue(Object source, String valueIdentifier, Class<Object> clazz) throws IllegalAccessException {
        return super.getValue(source, valueIdentifier, clazz);
    }
}
