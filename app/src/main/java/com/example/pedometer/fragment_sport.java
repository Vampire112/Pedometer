package com.example.pedometer;

import android.content.Context;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import db.DBDao;
import db.DBOpen;
import mrkj.library.wheelview.circlebar.CircleBar;
import service.SaveStep;
import service.StepCounterService;
import utils.SaveKeyValues;
import utils.StepDetector;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * create an instance of this fragment.
 */
public class fragment_sport extends Fragment {


    private static final int STEP_PROGRESS=2;//步数
    private View view;

    private CircleBar circleBar;//进度条
    private TextView show_mileage,show_heat,want_steps;

    //进度
    private int custom_steps;
    private int custom_steps_length;
    private int custom_weight;
    private Thread get_step_thread;
    private Intent step_service;
    private boolean isStop;
    private Double distance_values;
    private int steps_values;
    private Double heat_values;
    private Context context;
    private int duration;//动画时间
    private DBOpen dbOpen;//数据库
    private DBDao dbDao;
    private Intent save_service;

    private Handler handler=new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {

                case STEP_PROGRESS:
                    //把步数显示在显示条上
                    steps_values= StepDetector.CURRENT_STEP;
                    circleBar.update(steps_values,duration);
                    duration=0;
                    //保存数据

                    SaveKeyValues.putIntValues("sport_steps",steps_values);
                    ContentValues contentValues=new ContentValues();

                    //contentValues.put(steps,"steps_values");
                    //dbDao.insertValue(step,contentValues);

                    Log.e("执行了", ":" + steps_values);
                    distance_values=steps_values*custom_steps_length*0.1*0.001;//路程
                    Log.e("里程",":"+distance_values+"km");
                    show_mileage.setText(formatDouble(distance_values) + context.getString(R.string.km));
                    SaveKeyValues.putStringValues("sport_distance",
                            formatDouble(distance_values));
                    //消耗热量:跑步热量（kcal）＝体重（kg）×距离（公里）×1.036
                    heat_values = custom_weight * distance_values * 1.036;
                    //展示信息
                    show_heat.setText(formatDouble(heat_values) + context.getString(R.string.cal));
                    //存值
                    SaveKeyValues.putStringValues("sport_heat",
                            formatDouble(heat_values));
                    break;
            }
            return false;


        }

    });

    /**
     *
     * @param doubles
     * @return
     */
    private String formatDouble(Double doubles) {
        DecimalFormat format = new DecimalFormat("####.##");
        String distanceStr = format.format(doubles);
        return distanceStr.equals("0") ? "0.00" : distanceStr;
    }

    /**
     *
     * @param context
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context=context;

    }
    //创建视图
    @Nullable
    @Override
    //待续....
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        view=inflater.inflate(R.layout.fragment_fragment_sport,null);

        initView();
        initValues();

        setNature();
        if(StepDetector.CURRENT_STEP>custom_steps){
            Toast.makeText(getContext(),"你已经达到目标步数",Toast.LENGTH_LONG).show();
        }
        //弹窗
        /**
         if(SaveKeyValues.getIntValues("do_hint",0) == 1
         && (System.currentTimeMillis() > (SaveKeyValues.
         getLongValues("show_hint",0)+ Constant.DAY_FOR_24_HOURS))){

         AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
         alert.setTitle("FBI_worn");
         alert.setMessage("肥猪，你想不想减肥了吗");
         alert.setPositiveButton("点击确定不再提示！",
         new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        SaveKeyValues.putIntValues("do_hint" , 0);
        }
        });
         alert.create();
         alert.show();
         }
         **/
        return view;
    }
    /**
     * InitValues
     * 初始化数据
     *待续。。。
     */
    private void initValues(){
        isStop=false;
        duration=800;
        SaveKeyValues.createSharedPreferences(context);
        custom_steps=SaveKeyValues.getIntValues("step_plan",6000);
        custom_steps_length=SaveKeyValues.getIntValues("length",70);
        custom_weight=SaveKeyValues.getIntValues("weight",50);
        Log.e("步数", custom_steps + "步");
        Log.e("步长", custom_steps_length + "厘米");
        Log.e("体重", custom_weight + "公斤");
        //开启计步服务
        int history_values=SaveKeyValues.getIntValues("sport_steps",0);
        Log.e("获取存储的值",":"+history_values);
        int service_values=StepDetector.CURRENT_STEP;

        Log.e("关闭程序后的值:",""+service_values);
        boolean isLaunch=getArguments().getBoolean("is_launch",false);
        if(isLaunch){

            StepDetector.CURRENT_STEP=history_values+service_values;
        }
        step_service = new Intent(getContext(),StepCounterService.class);
        getContext().startService(step_service);
        save_service=new Intent(getContext(), SaveStep.class);
        getContext().startService(save_service);
    }
    /**
     * initView
     * 初始化控件
     */
    private void initView(){
        circleBar=(CircleBar) view.findViewById(R.id.show_progress);
        // city_name=(TextView)view.findViewById(R.id.city_name);
        // city_temperature=(TextView)view.findViewById(R.id.temperature);
        // city_air_quality=(TextView)view.findViewById(R.id.air_quality);

        show_mileage=(TextView)view.findViewById(R.id.mileage_txt);
        show_heat=(TextView)view.findViewById(R.id.heat_txt);
        want_steps=(TextView)view.findViewById(R.id.want_steps);

    }
    /**
     * setNature
     * 设置属性
     * 待续...
     */
    private void setNature(){
        circleBar.setcolor(R.color.theme_blue_two);
        circleBar.setMaxstepnumber(custom_steps);
        getServiceValue();
        want_steps.setText("今日目标："+custom_steps+"步");

    }
    /**
     * 下载数据
     * 待续。。。
     */
    private void downLoadDataFromNet(){
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }
    /**
     * 获取服务
     * getServiceValue
     * 待续。。。
     */
    private void getServiceValue(){
        if(get_step_thread==null){
            get_step_thread=new Thread(){
                @Override
                public void run(){
                    super.run();
                    while (!isStop){
                        try{
                            Thread.sleep(1000);
                            if(StepCounterService.FLAG){
                                handler.sendEmptyMessage(STEP_PROGRESS);
                            }
                        }catch(InterruptedException e){e.printStackTrace();}
                    }
                }

            };
            get_step_thread.start();
        }

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(get_step_thread);
        isStop=true;
        get_step_thread=null;
        steps_values=0;
        duration=800;

    }



}
