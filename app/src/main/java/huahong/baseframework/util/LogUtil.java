package huahong.baseframework.util;

import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2017/11/10.
 */

public class LogUtil {

    private LogUtil() {}

    private static boolean isDebug = true;
    private static final String TAG = "com.orhanobut.logger.Logger";


    public static void e(String tag,Object o) {
        if(isDebug) {
            Logger.e(tag, o);
        }
    }

    public static void e(Object o) {
        LogUtil.e(TAG,o);
    }

    public static void w(String tag,Object o) {
        if(isDebug) {
            Logger.w(tag, o);
        }
    }

    public static void w(Object o) {
        LogUtil.w(TAG,o);
    }

    public static void d(String msg) {
        if(isDebug) {
            Logger.d(msg);
        }
    }

    public static void i(String msg) {
        if(isDebug) {
            Logger.i(msg);
        }
    }


}
