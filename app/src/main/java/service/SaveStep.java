package service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import db.DBDao;
import utils.Date;
import utils.SaveKeyValues;
import utils.StepDetector;

public class SaveStep extends Service  {

    private DBDao dbDao;
    public SaveStep(){}
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.e("测试服务","start");
        int cus_step_len= SaveKeyValues.getIntValues("length",70);
        int cus_weight= SaveKeyValues.getIntValues("weight",50);
        dbDao=new DBDao(this);
        boolean result=isActivityRunning(this);
        int step;
        if(result){
            step= StepDetector.CURRENT_STEP;
        }else {
            step= StepDetector.CURRENT_STEP+ SaveKeyValues.getIntValues("sport_steps",0);
        }
        double distance_values = step * cus_step_len * 0.01 *0.001;//km
        String distance_Str = formatDouble(distance_values);
        double heat_values = cus_weight * distance_values * 1.036;//cls
        String heat_Str = formatDouble(heat_values);
        //获取日期
        Map<String,Object> map = Date.getDate();
        int year = (int) map.get("year");
        int month = (int) map.get("month");
        int day = (int) map.get("day");
        String date = (String) map.get("date");

        //save

        ContentValues contentValues=new ContentValues();
        contentValues.put("date",date);
        contentValues.put("year",year);
        contentValues.put("month",month);
        contentValues.put("day",day);
        contentValues.put("steps",step);
        contentValues.put("hot",heat_Str);
        contentValues.put("length", distance_Str);

        long reBack=dbDao.insertValue("step",contentValues);
        if(reBack>0){
            SaveKeyValues.putIntValues("sport_steps",0);
            StepDetector.CURRENT_STEP=0;

        }


    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("测试服务", "结束了！");
    }
    public static boolean isActivityRunning(Context mContext){
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(1);
        if(info != null && info.size() > 0){
            ComponentName component = info.get(0).topActivity;
            if(component.getPackageName().equals(mContext.getPackageName())){
                return true;
            }
        }
        return false;
    }
    private String formatDouble(Double doubles) {
        DecimalFormat format = new DecimalFormat("####.##");
        String distanceStr = format.format(doubles);
        return distanceStr.equals("0") ? "0.00" : distanceStr;
    }
}



