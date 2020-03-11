package com.lingyi.autiovideo.test.utils;

import android.util.Log;

import com.bnc.activity.utils.T01Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * json数据解析工具
 */
public class JsonUtil {

    /**
     * 用于解析json的类
     */
    private static Gson GSON = new Gson();

    /**
     * 把json字符串转换为JavaBean
     *
     * @param json      json字符串
     * @param beanClass JavaBean的Class
     * @return
     */
    public static <T> T json2Bean(String json, Class<T> beanClass) {
        T bean = null;
        try {
            bean = GSON.fromJson(json, beanClass);
        } catch (Exception e) {
            Log.i("JsonUtil", "解析json数据时出现异常\njson = " + json, e);
        }
        return bean;
    }

    /**
     * 把json字符串转换为JavaBean。如果json的根节点就是一个集合，则使用此方法<p>
     * type参数的获取方式为：Type type = new TypeToken<集合泛型>(){}.getType();
     *
     * @param json json字符串
     * @return type 指定要解析成的数据类型
     */
    public static <T> T json2Collection(String json, Type type) {
        T bean = null;
        try {
            bean = GSON.fromJson(json, type);
        } catch (Exception e) {
            T01Log.e("JsonUtil", "解析json数据时出现异常\njson = " + json, e);
            return null;
        }
        return bean;
    }


    /**
     * 时间格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 创建GSON
     *
     * @return
     */
    public static Gson getGson() {
        return new GsonBuilder().serializeNulls().setDateFormat(DATE_FORMAT).create();
    }

    /**
     * 将对象转化为字符串
     *
     * @param obj
     * @return
     */
    public String Object2Json2(Object obj) {
        return getGson().toJson(obj);
    }

    /**
     * 将对象转化为字符串(泛型实现)
     *
     * @param
     * @return
     */
    public static <T> String t2Json2(T t) {
        return getGson().toJson(t);
    }

    /**
     * 将字符转化为对象
     *
     * @param <T>
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T json2T(String jsonString, Class<T> clazz) {
        return getGson().fromJson(jsonString, clazz);
    }

}