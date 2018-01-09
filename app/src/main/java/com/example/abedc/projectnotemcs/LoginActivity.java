package com.example.abedc.projectnotemcs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.os.Build.VERSION_CODES.M;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        receive();
        init();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseUser databaseUser = new DatabaseUser(getApplicationContext());
                Cursor c = databaseUser.getAllUser();
                boolean check = false;

                while (c.moveToNext()) {
                    String username1 = c.getString(0);
                    String password1 = c.getString(1);
                    if (username1.equals(username.getText().toString()) && password1.equals(password.getText().toString())) {
                        check = true;
                    }
                }

                if (check == true) {
                    SharedPreferences sh = getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();

                    editor.putString("username", username.getText().toString());
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivity.this, "Username / Password is not match", Toast.LENGTH_SHORT).show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    public void init() {
        username = (EditText) findViewById(R.id.etInputUsername_LoginForm);
        password = (EditText) findViewById(R.id.etInputPassword_LoginForm);
        login = (Button) findViewById(R.id.btnLogin_LoginForm);
        register = (Button) findViewById(R.id.btnRegistration_LoginForm);
    }

    public void receive() {
        requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, PackageManager.PERMISSION_GRANTED);
        if (Build.VERSION.SDK_INT >= M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};
                requestPermissions(permissions, 1);
            }
        }
    }
}
