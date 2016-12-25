package com.focuschina.ehealth_lib.util;

import android.util.Log;

import com.focuschina.ehealth_lib.config.AppConfig;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 打印工具类
 */
public class LogUtil {
    public static boolean debug = AppConfig.isPrint();
    public static final String TAG_URL = "url";
    public static final String TAG_TEST = "aaa";

    /**
     * 异常
     */
    public static void err(Exception e) {
        if (debug) {
            throw new RuntimeException(e);
        } else {
            if (e != null) {
                LogUtil.e("error", Log.getStackTraceString(e));
            }
        }
    }

    /**
     *
     */
    public static void test(Object... objs) {
        i(TAG_TEST, objs);
    }

    /**
     * 长文件本
     */
    public static void testL(String str) {
        iL(TAG_TEST, str);
    }

    /**
     * toString无法实现的 (map,list用test既可)
     * 反射字断
     */
    public static void testObj(Object obj) {
        Method[] bfs = obj.getClass().getDeclaredMethods();
        for (Method m : bfs) {
            if (m.getName() == "toString") {
                i(TAG_TEST, obj);
                return;
            }
        }
        Map<String, Object> map = ClassUtil.toMap(obj);
        i(TAG_TEST, map);
    }


    //====================================================
    // log可以直接打 map,list,
    //	可以直接 toString 进行转换
    //=====================================================
    public static void i(String tag, Object... objs) {
        if (!debug) {
            return;
        }
        if (objs == null) {
            Log.i(tag, "null");
            return;
        }
        String temp = "";
        for (int i = 0; i < objs.length; i++) {
            temp += objs[i] + "\n";
        }
        Log.i(tag, temp);
    }

    public static void e(String tag, Object... objs) {
        if (!debug) {
            return;
        }
        if (objs == null) {
            Log.e(tag, "null");
            return;
        }
        String str = "";
        for (int i = 0; i < objs.length; i++) {
            str += objs[i] + "\n";
        }
        Log.e(tag, str);
    }

    /**
     * 分断打印超长的文字
     */
    private static final int length = 2000;

    public static void iL(String tag, String str) {
        try {
            int index = 0;
            String substr = null;
            while (str.length() > (index + 1) * length) {
                substr = str.substring(index * length, (index + 1) * length);
                Log.i(tag, substr);
                index++;
            }
            substr = str.substring(index * length, str.length());
            Log.i(tag, substr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
