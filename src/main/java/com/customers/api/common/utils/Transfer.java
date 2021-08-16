package com.customers.api.common.utils;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Transfer<DTO, E> {

    public void transferDTOToEntity(DTO dto, E entity) throws InvocationTargetException, IllegalAccessException {
        for (Field field : Arrays.asList(FieldUtils.getAllFields(dto.getClass()))) {
            String uppercase = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

            Method get = ReflectionUtils.findMethod(dto.getClass(), "get" + uppercase);

            if (get != null) {
                Object object = get.invoke(dto);
                if (object != null) {
                    Method set = ReflectionUtils.findMethod(entity.getClass(), "set" + uppercase, object.getClass());
                    if (set != null) {
                        set.invoke(entity, object);
                    }
                }
            }
        }
    }

}
