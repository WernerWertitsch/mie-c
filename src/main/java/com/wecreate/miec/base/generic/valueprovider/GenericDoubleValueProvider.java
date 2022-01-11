package com.wecreate.miec.base.generic.valueprovider;

import com.wecreate.miec.base.generic.valueprovider.base.ReflectiveValueProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenericDoubleValueProvider extends ReflectiveValueProvider<Object, Double> {
    @Override
    public Double getValue(Object source, String valueIdentifier, Class<Object> clazz)  {
        try {
            return super.getValue(source, valueIdentifier, clazz);
        } catch (IllegalAccessException e) {
            log.error(String.format("Could not extract String from field %s using class %s, with object %s", valueIdentifier, clazz.toString(), source), e);
            return 0.00;
        }
    }
}
