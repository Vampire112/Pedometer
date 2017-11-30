package service;


import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import utils.StepDetector;

/**
 * Created by 傻不拉几 on 2017/11/26.
 */
public class StepCounterService extends Service {

    private static final String TAG="StepCounterService";
    public static boolean FLAG=false;

    private SensorManager manager;//传感器服务
    public StepDetector detector;//计步算法



    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.e(TAG,"后台服务开启了");
        FLAG=true;
        detector=new StepDetector(this);
        detector.walk=1;

        manager=(SensorManager)this.getSystemService(SENSOR_SERVICE);//获取传感器服务
        //注册传感器并监听
        manager.registerListener(detector,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                                      SensorManager.SENSOR_DELAY_NORMAL);
        //SENSOR_DELAY_NORMAL默认传感器速度
        //TYPE_ACCELEROMETER加速度传感器

  //      powerManager=(PowerManager)this.getSystemService(Context.POWER_SERVICE);//电源服务
//        wakeLock=powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP,"S");
        //ACQUIRE_CAUSES_WAKEUP：正常唤醒锁实际上并不打开照明。相反，一旦打开他们会一直仍然保持(例如来世user的activity)。
        // 当获得wakelock，这个标志会使屏幕或/和键盘立即打开。一个典型的使用就是可以立即看到那些对用户重要的通知
         //wakeLock.acquire();


        //设置定时服务
        /**

        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        calendar=Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,0);
        //intent=new Intent(this,)发送广播
        */
         }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        return START_STICKY;//START_STICKY:内存足够时重启服务
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        FLAG=false;
        Log.e(TAG,"服务停止");

        if(detector!=null){
            manager.unregisterListener(detector);
        }
    //    if(wakeLock!=null){
      //      wakeLock.release();
        //}
    }
}


