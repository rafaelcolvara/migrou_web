package com.migrou.implementacoes.pessoas;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;

public class Interpreter {

    public static <T> T parseToPojo(Object[][] obj, Class<T> clazz) {
        try {
            T pojo = clazz.newInstance();
            int i = 0;
            for (Field f : clazz.getDeclaredFields()) {
                if (pojo.getClass().getDeclaredField(f.getName())!=null)
                    if (obj[0].length>=i)
                    setField(pojo, f, obj[0][i++]);
                else
                    System.out.println("Campo nao existe no pojo");
            }
            return pojo;
        } catch (Exception e) {
            return null;
        }
    }

    private static <T> void setField(T pojo, Field f, Object value) {
        try {
            Class<T> clazz = (Class<T>) pojo.getClass();
            Method setMethod = clazz.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), f.getType());
            if (value instanceof BigInteger) {
                value = ((BigInteger) value).longValue();
            }
            setMethod.invoke(pojo, value);

        } catch (Exception e) {
            System.out.println(f.getName() + " - " + value.toString());
            e.printStackTrace();
        }
    }
}
