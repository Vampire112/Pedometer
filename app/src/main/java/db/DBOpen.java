package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 傻不拉几 on 2017/11/30.
 */
public class DBOpen extends SQLiteOpenHelper {
    private static String DB_NAME = "LLH_STEP";// 数据库名称
    private static int DB_VERSION = 1;// 数据库版本号

    final static String Create_Table_sql=
            "create table step (_id integer primary key autoincrement ," +
                    " date varchar(20),year integer,month integer," +
                    "day integer,steps integer,hot varchar(20),length varchar(20))";

    public DBOpen(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Create_Table_sql);//建表
        Log.e("数据库：",DB_NAME+"已经建立");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
