package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 傻不拉几 on 2017/11/30.
 */
public class DBDao {
    private DBOpen dbOpen;
    private SQLiteDatabase db;

    public DBDao(Context context) {
        dbOpen=new DBOpen(context);
        // 得到一个数据库对象
        db = dbOpen.getWritableDatabase();
    }

    // 添加数据
    public long insertValue(String table, ContentValues values) {

        long result = db.insert(table, null, values);
        return result;
    }

    // 修改数据
    public int updateValue(String table, ContentValues values, String whereClause, String[] whereArgs) {
        /**
         * table 要修改的表的表名 values 修改后的值 whereClause where关键子后的语句 id=？ whereArgs
         * 指的是whereClause里边？替换的值
         *
         */
        int result = db.update(table, values, whereClause, whereArgs);
        return result;
    }

    // 删除
    public int deleteValue(String table, String whereClause, String[] whereArgs) {
        /**
         * table 删除数据表的表名 whereClause 删除条件的where子句 whereArgs 删除条件占位符的值
         */
        return db.delete(table, whereClause, whereArgs);
    }

    // 查询

    public Cursor selectValue(String sql, String[] selectionArgs) {
        /**
         * sql sql语句，sql语句不要添加分好，应为系统会给我们自动填上 selectionArgs
         * sql语句的where部分可能包含占位符，所以需要用selectionArgs来取代 那个问号
         */
        // select name from person where name =? and sex = ?
        return db.rawQuery(sql, selectionArgs);

    }

    // 查询的第二种方式
    public Cursor selectValue2(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
                               String having, String orderBy) {
        /**
         * table 表名 columns 要查询的列 selection where后面的查询条件 selectionArgs
         * 取代查询条件中？的占位符 groupBy 根据摸个字段来分组 having having短语，进一步过滤筛选的 orderBy 排序
         */
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    // 关闭
    public void close() {
        db.close();
    }

    // 清空表中数据
    public void clear(String name) {
        db.execSQL("delete from " + name);
    }

    //返回一个全查询的游标
    public Cursor selectAll(String table){

        return selectValue2(table,null,null,null,null,null,null);
    }
    //返回查询指定列
    public Cursor selectColumn(String table ,String[] column){

        return selectValue2(table,column,null,null,null,null,null);
    }
}


