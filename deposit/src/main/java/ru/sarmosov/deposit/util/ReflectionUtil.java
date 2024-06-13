package ru.sarmosov.deposit.util;

import java.lang.reflect.Field;

public class ReflectionUtil {

    private static String[] nullableFields = {"id", "customerId", "description"};

    public static boolean isFieldsNotNull(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isFieldNullable(field)) continue;
            System.out.println(field.getName());
            field.setAccessible(true);
            try {
                System.out.println(field.get(obj));
                if (field.get(obj) == null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private static boolean isFieldNullable(Field field) {
        for (String fieldName : nullableFields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

}
