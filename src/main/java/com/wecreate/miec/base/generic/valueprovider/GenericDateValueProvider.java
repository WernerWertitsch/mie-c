package com.wecreate.miec.base.generic.valueprovider;

import com.wecreate.miec.base.generic.valueprovider.base.ReflectiveValueProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class GenericDateValueProvider extends ReflectiveValueProvider<Object, LocalDate> {
    //TODO maybe do some ranges here, or some timezone stuff

    @Override
    public LocalDate getValue(Object source, String valueIdentifier, Class<Object> clazz)  {
        try {
            return super.getValue(source, valueIdentifier, clazz);
        } catch (IllegalAccessException e) {
            log.error(String.format("Could not extract String from field %s using class %s, with object %s", valueIdentifier, clazz.toString(), source), e);
            return LocalDate.MIN;
        }
    }

    @Override
    public boolean isValue(Object inputObject, String fullObjectAndValueIdentifier, Class<Object> clazz, LocalDate compareValue)  {
        return getValue(inputObject, fullObjectAndValueIdentifier, clazz).equals(compareValue);
    }
}
