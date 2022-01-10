package com.wecreate.miec.base.generic.valueprovider;

import com.wecreate.miec.base.generic.valueprovider.base.ReflectiveValueProvider;
import org.springframework.stereotype.Component;

@Component
public class GenericDoubleValueProvider extends ReflectiveValueProvider<Object, Double> {
    @Override
    public Double getValue(Object source, String valueIdentifier, Class<Object> clazz) throws IllegalAccessException {
        return super.getValue(source, valueIdentifier, clazz);
    }
}
