package com.migrou.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class ObjetoUtil {

    public static <T> T deStringJsonParaObjeto(String objetoEmString, Class<T> clazz) {

        T obj = null;

        try {
            obj = new Gson().fromJson(objetoEmString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    public static String deObjetoParaStringJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
