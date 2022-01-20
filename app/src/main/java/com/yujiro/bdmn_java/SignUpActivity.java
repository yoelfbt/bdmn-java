package com.yujiro.bdmn_java;

import static com.yujiro.bdmn_java.DatabaseHelper.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText username,email,password,role;
    Button signUp,edit;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseHelper = new DatabaseHelper(this);
        username = findViewById(R.id.et_username);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        role = findViewById(R.id.et_role);
        signUp = findViewById(R.id.btn_signUp);
        edit = findViewById(R.id.btn_edit);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel();
                userModel.setUsername(username.getText().toString());
                userModel.setEmail(email.getText().toString());
                userModel.setPassword(password.getText().toString());
                userModel.setRole(role.getText().toString());
                databaseHelper.addUser(userModel);

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        
        editData();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", username.getText().toString());
                contentValues.put("username", email.getText().toString());
                contentValues.put("email", password.getText().toString());
                contentValues.put("role", role.getText().toString());

                sqLiteDatabase = databaseHelper.getReadableDatabase();
                long rEdit = sqLiteDatabase.update(TABLE_NAME,contentValues,"id="+id, null);
                if (rEdit!=-1){
                    Toast.makeText(SignUpActivity.this,"Data Sukses di Update",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void editData() {
        if (getIntent().getBundleExtra("userdata")!=null){
            Bundle bundle = getIntent().getBundleExtra("userdata");
            id=Integer.parseInt(bundle.getString("id"));
            username.setText(bundle.getString("id"));
            email.setText(bundle.getString("username"));
            password.setText(bundle.getString("email"));
            role.setText(bundle.getString("role"));

            signUp.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);

        }
    }
}