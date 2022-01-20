package com.yujiro.bdmn_java;

import static com.yujiro.bdmn_java.DatabaseHelper.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.myRecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
    }

    private void displayData() {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME+"",null);
        ArrayList<UserModel> modelArrayList = new ArrayList<>();
        while (cursor.moveToNext()){
            String id = String.valueOf(cursor.getInt(0));
            String fusername = cursor.getString(1);
            String femail = cursor.getString(2);
            String fpassword = cursor.getString(3);
            String frole = cursor.getString(4);
            modelArrayList.add(new UserModel(id,fusername,femail,fpassword,frole));
        }
        cursor.close();
        userAdapter = new UserAdapter(this, R.layout.uset_item, modelArrayList, sqLiteDatabase);
        recyclerView.setAdapter(userAdapter);
    }
}