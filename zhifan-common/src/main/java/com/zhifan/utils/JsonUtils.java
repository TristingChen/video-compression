package com.zhifan.utils;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

/**
 * @author Miracle
 * @date 2022/4/24 10:09
 */
public class JsonUtils {

    public static JSONObject toJsonObj(Map<String, Object> map) throws JSONException {
        JSONObject jsonData = new JSONObject();
        for (String key : map.keySet()) {
            jsonData.put(key, map.get(key));
        }
        return jsonData;
    }

    public static Object fromJsonToJava(JSONObject json, Class pojo) throws Exception{
        // 首先得到pojo所定义的字段
        Field[] fields = pojo.getDeclaredFields();
        // 根据传入的Class动态生成pojo对象
        Object obj = pojo.newInstance();
        for(Field field: fields){
            // 设置字段可访问（必须，否则报错）
            field.setAccessible(true);
            // 得到字段的属性名
            String name = field.getName();
            // 这一段的作用是如果字段在JSONObject中不存在会抛出异常，如果出异常，则跳过。
            try{
                json.get(name);
            }catch(Exception ex){
                continue;
            }
            if(json.get(name) != null && !"".equals(json.getString(name))){
                // 根据字段的类型将值转化为相应的类型，并设置到生成的对象中。
                if(field.getType().equals(Long.class) || field.getType().equals(long.class)){
                    field.set(obj, Long.parseLong(json.getString(name)));
                }else if(field.getType().equals(String.class)){
                    field.set(obj, json.getString(name));
                } else if(field.getType().equals(Double.class) || field.getType().equals(double.class)){
                    field.set(obj, Double.parseDouble(json.getString(name)));
                } else if(field.getType().equals(Integer.class) || field.getType().equals(int.class)){
                    field.set(obj, Integer.parseInt(json.getString(name)));
                } else if(field.getType().equals(Date.class)){
                    field.set(obj, Date.parse(json.getString(name)));
                } else{
                    continue;
                }
            }
        }
        return obj;
    }


    /**
     * 用于带类型的jsonObject转换
     * @param json
     * @param pojo
     * @return
     * @param <T>
     * @throws Exception
     */
    private static<T> T jsonObjectToJava(JSONObject json, Class<T> pojo) throws Exception{
        // 首先得到pojo所定义的字段
        Field[] fields = pojo.getDeclaredFields();
        // 根据传入的Class动态生成pojo对象
        T obj = pojo.getDeclaredConstructor().newInstance();
        for(Field field: fields){
            // 设置字段可访问（必须，否则报错）
            field.setAccessible(true);
            // 得到字段的属性名
            String name = field.getName();
            // 这一段的作用是如果字段在JSONObject中不存在会抛出异常，如果出异常，则跳过。
            try{
                json.get(name);
            }catch(Exception ex){
                continue;
            }

            if(json.get(name) != null && !"".equals(json.getString(name))){
                // 根据字段的类型将值转化为相应的类型，并设置到生成的对象中。
                if(field.getType().equals(Long.class) || field.getType().equals(long.class)){
                    field.set(obj, Long.parseLong(json.getString(name)));
                }else if(field.getType().equals(String.class)){
                    field.set(obj, json.getString(name));
                } else if(field.getType().equals(Double.class) || field.getType().equals(double.class)){
                    field.set(obj, Double.parseDouble(json.getString(name)));
                } else if(field.getType().equals(Integer.class) || field.getType().equals(int.class)){
                    field.set(obj, Integer.parseInt(json.getString(name)));
                } else if(field.getType().equals(Date.class)){
                    field.set(obj, Date.parse(json.getString(name)));
                }else{
                    field.set(obj, json.get(name));
                }
            }
        }
        return obj;
    }
}
