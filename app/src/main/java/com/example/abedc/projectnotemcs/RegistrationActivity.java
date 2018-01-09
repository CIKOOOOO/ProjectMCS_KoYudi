package com.example.abedc.projectnotemcs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {

    EditText username, password, confPassword,phoneNumber,inputPin;
    Button register, cancel,confirmPin;
    RadioGroup gender;
    String genderPrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rnd = new Random();
                String huruf1 = String.valueOf((char) (rnd.nextInt(26) + 'A'));
                String huruf2 = String.valueOf((char) (rnd.nextInt(26) + 'A'));
                String angka1 = String.valueOf(rnd.nextInt(9) + 0);
                String angka2 = String.valueOf(rnd.nextInt(9) + 0);
                String angka3 = String.valueOf(rnd.nextInt(9) + 0);
                final String confirmationCode = huruf1+huruf2+angka1+angka2+angka3;

                boolean check = true;
                if (username.getText().toString().length() < 5) {
                    Toast.makeText(RegistrationActivity.this, "username should 5 words or more", Toast.LENGTH_SHORT).show();
                    check = false;
                }
                if (password.getText().toString().length() < 5) {
                    check = false;
                    Toast.makeText(RegistrationActivity.this, "Password should 5 words or more", Toast.LENGTH_SHORT).show();
                }
                if (!confPassword.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(RegistrationActivity.this, "Password and Confirmation Password not match", Toast.LENGTH_SHORT).show();
                    check = false;
                }
                if (gender.getCheckedRadioButtonId() == R.id.rbMale_RegisForm) {
                    genderPrefix = "Male";
                } else if (gender.getCheckedRadioButtonId() == R.id.rbFemale_RegisForm) {
                    genderPrefix = "Female";
                } else {
                    check = false;
                    Toast.makeText(RegistrationActivity.this, "Choose gender !", Toast.LENGTH_SHORT).show();
                }

                final DatabaseUser databaseUser = new DatabaseUser(getApplicationContext());
                Cursor c = databaseUser.getAllUser();
                while (c.moveToNext()) {
                    String user = c.getString(0);
                    if (user.equals(username.getText().toString())) {
                        check = false;
                        Toast.makeText(RegistrationActivity.this, "Username must uniqe", Toast.LENGTH_SHORT).show();
                    }
                }

                if (check == true) {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber.getText().toString(),null,confirmationCode,null,null);

                    AlertDialog.Builder codeBuilder = new AlertDialog.Builder(RegistrationActivity.this);
                    View codeView = getLayoutInflater().inflate(R.layout.confirm_regis_pin, null);
                    inputPin = codeView.findViewById(R.id.etInputConfirmCode_ConfirmRegisPin);
                    confirmPin = codeView.findViewById(R.id.btnConfirm_ConfirmRegisPin);
                    confirmPin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(inputPin.getText().toString().equals(confirmationCode)){
                                Toast.makeText(RegistrationActivity.this, "Make Account success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                                databaseUser.insertNewUser(username.getText().toString(),password.getText().toString()
                                        ,phoneNumber.getText().toString(),genderPrefix);
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "Confirmation Code is not match", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, RegistrationActivity.class));
                            }
                        }
                    });

                    codeBuilder.setView(codeView);
                    AlertDialog codeAlert = codeBuilder.create();
                    codeAlert.show();
                }
            }
        });

    }

    public void init() {
        username = (EditText) findViewById(R.id.etInputUsername_RegistrationForm);
        password = (EditText) findViewById(R.id.etInputPassword_RegistrationForm);
        confPassword = (EditText) findViewById(R.id.etInputConfirmPassword_RegistrationForm);
        phoneNumber = (EditText) findViewById(R.id.etInputPhoneNumber_RegistrationForm);
        register = (Button) findViewById(R.id.btnRegis_RegisForm);
        cancel = (Button) findViewById(R.id.btnCancel_RegisForm);
        gender = (RadioGroup) findViewById(R.id.rgGender_RegisForm);
    }
}
