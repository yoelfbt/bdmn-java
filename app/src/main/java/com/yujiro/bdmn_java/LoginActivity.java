package com.yujiro.bdmn_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    TextView Etemail, Etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);
        Button btnSignUp = findViewById(R.id.btn_signUp);
        Button btnLogin = findViewById(R.id.btn_login);
        Etemail = (EditText) findViewById(R.id.et_email);
        Etpassword = (EditText) findViewById(R.id.et_password);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Etemail.getText().toString();
                String password = Etpassword.getText().toString();

                if (email.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkemailpass = databaseHelper.checkusernamePassword(email,password);
                    if (checkemailpass == true){
                        Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "User tidak terdaftar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}