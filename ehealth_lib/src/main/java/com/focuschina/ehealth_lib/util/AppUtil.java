package com.focuschina.ehealth_lib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Ning Jiajun
 * @ClassName: AppUtil
 * @Description: APP支持工具类
 * @date 2014-11-18 上午9:41:35
 */
public class AppUtil {
    final static int BUFFER_SIZE = 4096;

    /**
     * 设置控件的Selector
     *
     * @param onResId
     * @param offResId
     * @param context
     * @return
     */
    public static StateListDrawable setImageButtonState(int onResId, int offResId, Context context) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]
                {android.R.attr.stateNotNeeded}, context.getResources().getDrawable(onResId));
        states.addState(new int[]
                {android.R.attr.state_pressed, android.R.attr.state_enabled}, context.getResources().getDrawable(onResId));
        // states.addState(new int[]
        // { android.R.attr.state_focused, android.R.attr.state_enabled },
        // context.getResources().getDrawable(offResId));
        states.addState(new int[]
                {android.R.attr.state_enabled}, context.getResources().getDrawable(offResId));
        // states.addState(new int[]
        // { -android.R.attr.state_enabled }, context.getResources().getDrawable(onResId));
        return states;
    }

    /**
     * 设置Selector。
     */
    public static StateListDrawable newSelector(Context context, BitmapDrawable normal, BitmapDrawable pressed) {
        StateListDrawable bg = new StateListDrawable();
//        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);  
//        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);  
//        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);  
//        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable); 

        // View.PRESSED_ENABLED_STATE_SET  
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_STATE_SET  
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.EMPTY_STATE_SET  
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * 设置输入框光标位置
     *
     * @param editText
     */
    public static void setEditTextCursorPosition(EditText editText) {
        CharSequence text = editText.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * @param context
     * @return 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "1.00.00";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            if (null != pi.versionName && pi.versionName.length() > 0) {
                versionName = pi.versionName;
            }
        } catch (Exception e) {
            LogUtil.err(e);
        }
        return versionName;
    }

    public static String getPackageNames(Context context) {
        String packageName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            if (null != pi.packageName) {
                packageName = pi.packageName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.err(e);
        }
        return packageName;
    }


    /***
     * 获得手机设备的串号
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // Get deviceId
        String deviceId = tm.getDeviceId();
        // If running on an emulator
        if (deviceId == null || deviceId.trim().length() == 0 || deviceId.matches("0+")) {
            deviceId = (new StringBuilder("EMU")).append((new Random(System.currentTimeMillis())).nextLong())
                    .toString();
        }
        return deviceId;
    }

    /**
     * 获取设备屏幕密度
     *
     * @return
     */
    public static float getWindowDensity(Activity curActivity) {
        DisplayMetrics dm = new DisplayMetrics();
        curActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    /**
     * zhaoxuan
     * 获取设备屏幕密度
     *
     * @return
     */
    public static float getWindowDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public static int getWindowWidthPix(Activity context) {
        int ver = Build.VERSION.SDK_INT;
        Display display = context.getWindowManager().getDefaultDisplay();
        int width = 0;
        if (ver < 13) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            width = dm.widthPixels;
        } else {
            Point point = new Point();
            display.getSize(point);
            width = point.x;
        }
        return width;
    }

    /**
     * 设置对话框参数
     *
     * @param width
     * @param height
     */
    public static void setDialogParams(Dialog dialog, int width, int height) {
        WindowManager.LayoutParams dialogParams = dialog.getWindow().getAttributes();
        dialogParams.width = width;
        dialogParams.height = height;
        dialog.getWindow().setAttributes(dialogParams);
    }

    /**
     * dp to px 另一种
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * zhaoxuan
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getWindowDensity(context) + 0.5f);
    }

    /**
     * zhaoxuan
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getWindowDensity(context) + 0.5f);
    }

    /**
     * zhaoxuan
     * 将InputStream转换成String
     *
     * @param in InputStream
     * @return String
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), "UTF-8");   //"ISO-8859-1"
    }

    /**
     * 判断值是否为空
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    public static int getWindowHeightPix(Activity context) {
        int ver = Build.VERSION.SDK_INT;
        Display display = context.getWindowManager().getDefaultDisplay();
        int height = 0;
        if (ver < 13) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            height = dm.heightPixels;
        } else {
            Point point = new Point();
            display.getSize(point);
            height = point.y;
        }
        return height;
    }

    public static boolean isPostLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        Resources resources = context.getResources();
        if (isPostLolipop()) {
            return resources.getDrawable(resId, context.getTheme());
        } else {
            return resources.getDrawable(resId);
        }
    }

    /**
     * 得到屏幕宽高较小的一那个
     */
    public static int getPhotoSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels > metrics.heightPixels ? metrics.heightPixels : metrics.widthPixels;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    /**
     * {@link View#generateViewId()}要求API Level >= 17,而本工具类可兼容所有API Level
     * <p/>
     * 自动判断当前API Level,并优先调用{@link View#generateViewId()},即使本工具类与{@link View#generateViewId()}
     * 混用,也能保证生成的Id唯一
     * <p/>
     * =============
     * <p/>
     * while {@link View#generateViewId()} require API Level >= 17, this tool is compatibe with all API.
     * <p/>
     * according to current API Level, it decide weather using system API or not.<br>
     * so you can use  and {@link View#generateViewId()} in the
     * same time and don't worry about getting same id
     *
     * @author fantouchx@gmail.com
     */
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < 17) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

    /**
     * 判断是否支持特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String   regEx  =  "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
//        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        String regEx = "[&=]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
//        m.replaceAll("").trim();
        return m.find();
    }

    /**
     * @param @param  str
     * @param @return 设定文件
     * @return boolean    返回类型
     * @throws
     * @author zhaoxuan
     * @Title: isPositiveInteger
     * @Description: 判断str是否为正整数
     */
    public static boolean isPositiveInteger(String str) {
        if (isEmpty(str)) return false;
        return str.matches("[0-9]+");
        //+表示1个或多个（如"3"或"225"），*表示0个或多个（[0-9]*）（如""或"1"或"22"），?表示0个或1个([0-9]?)(如""或"7")
    }

    /**
     * 打印toast 用application context
     *
     * @param obj 入参对象
     */
    public static void showToast(Context context, Object obj) {
        if (null == context) return;
        try {
            if (obj instanceof Integer) { //res Id
                Toast.makeText(context, context.getString((Integer) obj), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, obj + "", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
