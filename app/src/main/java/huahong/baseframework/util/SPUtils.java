package huahong.baseframework.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2017/11/10.
 */

public class SPUtils {

    private SPUtils() {}

    private static SPUtils mSPUtils;

    private SharedPreferences mSp;

    private String name = "app";


    public static SPUtils getInstance() {

        if (mSPUtils == null) {
            synchronized (SPUtils.class) {
                if (mSPUtils == null) {
                    mSPUtils = new SPUtils();
                }
            }
        }

        return mSPUtils;
    }


    private void init(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        }
    }


    /**
     * 保存
     * @param context
     * @param key
     * @param value
     */
    public void save(Context context,String key, Object value) {
        init(context);

        if (value instanceof String) {

            mSp.edit().putString(key,(String) value).apply();
        }else if (value instanceof Boolean) {

            mSp.edit().putBoolean(key,(Boolean) value).apply();
        }else if (value instanceof Long) {

            mSp.edit().putLong(key,(Long)value).apply();
        }else if (value instanceof Integer) {
            mSp.edit().putInt(key,(Integer) value).apply();

        }else if (value instanceof Float) {

            mSp.edit().putFloat(key,(Float) value).apply();

        }

    }


    public String getString(Context context,String key,String defaultValue) {
        init(context);

       return mSp.getString(key,defaultValue);
    }

    public Boolean getBoolean(Context context,String key,Boolean defaultValue) {
        init(context);

        return mSp.getBoolean(key,defaultValue);
    }

    public long getLong(Context context,String key,long defaultValue) {
        init(context);

        return mSp.getLong(key,defaultValue);
    }


    public int getInt(Context context,String key,int defaultValue) {
        init(context);

        return mSp.getInt(key,defaultValue);
    }


    public float getFloat(Context context,String key,float defaultValue) {
        init(context);

        return mSp.getFloat(key,defaultValue);
    }






    public void clear(Context context) {
        init(context);
        mSp.edit().clear().apply();
    }
}
