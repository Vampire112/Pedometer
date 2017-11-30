package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by 傻不拉几 on 2017/11/29.
 */
public class SaveKeyValues {
    public static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void createSharedPreferences(Context context){
        String appName=context.getPackageName()+".Step_history";
        Log.e("存储的文件名",appName);
        sharedPreferences=context.getSharedPreferences(appName,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }
    public static boolean isUnreate(){
        boolean result=(sharedPreferences==null)?true:false;
        if(result){
            Log.e("worn:","sharedPreferences尚未创建");

        }
         return result;
    }
    public static boolean putStringValues(String key,String values){
        if (isUnreate()){
            return false;
        }
        editor.putString(key,values);
        return editor.commit();
    }

    /**
     * 取出String类型的值
     * @param key
     * @param defValue
     * @return
     */
    public static String getStringValues(String key,String defValue){
        if (isUnreate()){
            return null;
        }
        String string_value = sharedPreferences.getString(key,defValue);
        return string_value;
    }

    /**
     * 存入int类型的值
     * @param key
     * @param values
     * @return
     */
    public static boolean putIntValues(String key,int values){
        if (isUnreate()){
            return false;
        }
        editor.putInt(key, values);
        return editor.commit();
    }

    /**
     * 取出int类型的值
     * @param key
     * @param defValue
     * @return
     */
    public static int getIntValues(String key,int defValue){
        if (isUnreate()){
            return 0;
        }
        int int_value = sharedPreferences.getInt(key, defValue);
        return int_value;
    }

    /**
     * 存入long类型的值
     * @param key
     * @param values
     * @return
     */
    public static boolean putLongValues(String key,long values){
        if (isUnreate()){
            return false;
        }
        editor.putLong(key, values);
        return editor.commit();
    }

    /**
     * 取出long类型的值
     * @param key
     * @param defValue
     * @return
     */
    public static long getLongValues(String key,long defValue){
        if (isUnreate()){
            return 0;
        }
        long long_value = sharedPreferences.getLong(key, defValue);
        return long_value;
    }
    /**
     * 存入float类型的值
     * @param key
     * @param values
     * @return
     */
    public static boolean putFloatValues(String key,float values){
        if (isUnreate()){
            return false;
        }
        editor.putFloat(key, values);
        return editor.commit();
    }

    /**
     * 取出float类型的值
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloatValues(String key,float defValue){
        if (isUnreate()){
            return 0;
        }
        float float_value = sharedPreferences.getFloat(key, defValue);
        return float_value;
    }

    /**
     * 存入boolean类型的值
     * @param key
     * @param values
     * @return
     */
    public static boolean putBooleanValues(String key,boolean values){
        if (isUnreate()){
            return false;
        }
        editor.putBoolean(key, values);
        return editor.commit();
    }

    /**
     * 取出boolean类型的值
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getFloatValues(String key,boolean defValue){
        if (isUnreate()){
            return false;
        }
        boolean boolean_value = sharedPreferences.getBoolean(key, defValue);
        return boolean_value;
    }

    /**
     * 清空数据
     */
    public static boolean deleteAllValues(){
        if (isUnreate()){
            return false;
        }
        editor.clear();
        return editor.commit();
    }

    /**
     * 删除指定数据
     * @param key
     * @return
     */
    public static boolean removeKeyForValues(String key){
        if (isUnreate()){
            return false;
        }
        editor.remove(key);
        return editor.commit();
    }
}



