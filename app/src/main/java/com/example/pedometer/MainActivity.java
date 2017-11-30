package com.example.pedometer;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import db.DBOpen;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton sprot_btn,find_btn,my_btn,about_our_btn;
    private DBOpen dbOpen;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        radioGroup=(RadioGroup)findViewById(R.id.ui_btn_group);
        sprot_btn=(RadioButton)findViewById(R.id.sport_btn);
        find_btn=(RadioButton)findViewById(R.id.find_btn);
        my_btn=(RadioButton)findViewById(R.id.mine_btn);
        about_our_btn=(RadioButton)findViewById(R.id.our_btn);


        Bundle bundle=new Bundle();
        bundle.putBoolean("is_launch",false);
        fragment_sport sport=new fragment_sport();
        sport.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.frag_sport,sport).commit();
    }
}
