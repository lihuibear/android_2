package com.example.stage_2.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * 创建一个数据库类继承
 * 通过创建子类MyDatabaseHelper继承SQLiteOpenHelper类，实现它的一些方法来对数据库进行操作。
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stage_2.db";
    private static final int DATABASE_VERSION = 1;//定义一个数据库版本，是一个整数类型；
    private static final String TABLE_NAME = "contact";//定义一个表名;
    //需要定义标题
    private static final String CONTACT_ID = "phone_id";//序列
    private static final String CONTACT_NAME = "phone_name";//名字
    private static final String CONTACT_PHONE = "phone_phone";//电话
    private static final String CONTACT_ADDRESS = "phone_address";//地址
    private static final String CONTACT_UNIT = "phone_unit";//单位名称
    private static final String CONTACT_EMAIL = "phone_email";//Email
    private static final String CONTACT_QQ = "phone_qq";//QQ号码
    private Context context;

    /*
    构造函数
    要传上下文和名字以及工厂还有一个版本，
     */
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /*
    第一次创建数据库的时候使用回调方法
    数据出第一次创建的时候才会被调用，其他时候不会再调用了，只调用一次
    创建contact表
    id,name,phone,address,unit,,email,qq;
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String query =
                " CREATE TABLE " + TABLE_NAME +
                        " ( " + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CONTACT_NAME + " TEXT, " +
                        CONTACT_PHONE + " TEXT, " +
                        CONTACT_ADDRESS + " TEXT, " +
                        CONTACT_UNIT + " TEXT, " +
                        CONTACT_EMAIL + " TEXT, " +
                        CONTACT_QQ + " TEXT); ";
        //执行SQL
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(db);
    }

    //创建一个添加方法addPhone()
    //将创建SQLite数据库对象，这样只需要名命该数据库，使用一个关键字，指向SQLite开放式帮助类
    //helper类，获取可写入的数据库
    public long addPhone(String name, String phone, String address, String unit, String email, String qq) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONTACT_NAME, name);
        cv.put(CONTACT_PHONE, phone);
        cv.put(CONTACT_ADDRESS, address);
        cv.put(CONTACT_UNIT, unit);
        cv.put(CONTACT_EMAIL, email);
        cv.put(CONTACT_QQ, qq);

        long result = -1;
        try {
            result = db.insert(TABLE_NAME, null, cv);
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常信息
        } finally {
            db.close(); // 确保数据库在操作后关闭
        }

        return result; // 返回插入的行 ID 或 -1
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public long updateData(String id, String name, String phone, String address, String unit, String email, String qq) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONTACT_NAME, name);
        cv.put(CONTACT_PHONE, phone);
        cv.put(CONTACT_ADDRESS, address);
        cv.put(CONTACT_UNIT, unit);
        cv.put(CONTACT_EMAIL, email);
        cv.put(CONTACT_QQ, qq);

        long result = -1;
        try {
            result = db.update(TABLE_NAME, cv, CONTACT_ID + "=?", new String[]{id});
            Log.i("UpdateResult", "Update result for ID " + id + ": " + result);
        } catch (Exception e) {
            Log.e("DatabaseError", "Error updating data: " + e.getMessage());
        } finally {
            db.close(); // 确保数据库在操作后关闭
        }

        return result; // 返回更新的行数或 -1
    }


    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "phone_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Log.d("DatabaseHelper", "Deleting all data from " + TABLE_NAME);
            db.execSQL("DELETE FROM " + TABLE_NAME);
            Toast.makeText(context, "All data deleted successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("DatabaseError", "Error deleting all data: " + e.getMessage());
        } finally {
            db.close(); // 确保数据库在操作后关闭
        }
    }

    //查询
    public Cursor queryUser(String keyword) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                CONTACT_NAME + " LIKE '%" + keyword + "%' OR " +
                CONTACT_PHONE + " LIKE '%" + keyword + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
