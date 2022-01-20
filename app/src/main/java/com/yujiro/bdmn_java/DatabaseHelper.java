package com.yujiro.bdmn_java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user_db";
    public static final String TABLE_NAME = "users";
    private static final String ID = "id";
    private static final String username = "username";
    private static final String email = "email";
    private static final String password = "password";
    private static final String role = "role";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table_query = "CREATE TABLE if not EXISTS "+ TABLE_NAME +
                "("+
                ID+" INTEGER PRIMARY KEY," +
                username+" TEXT, "+
                email+" TEXT, "+
                password+" TEXT, "+
                role+" TEXT"+
                ")";
        sqLiteDatabase.execSQL(table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void addUser(UserModel userModel){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(username, userModel.getUsername());
        contentValues.put(email, userModel.getEmail());
        contentValues.put(password, userModel.getPassword());
        contentValues.put(role, userModel.getRole());
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public boolean checkusernamePassword(String email, String password){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from users where email = ? and password = ?", new String[]{email,password});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
}
