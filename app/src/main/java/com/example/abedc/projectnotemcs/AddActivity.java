package com.example.abedc.projectnotemcs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText title, desc;
    Button add, cancel;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        SharedPreferences sh = getSharedPreferences("userData", Context.MODE_PRIVATE);
        username = sh.getString("username", "");

        init();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = true;
                if (title.getText().toString().length() < 5) {
                    check = false;
                    Toast.makeText(AddActivity.this, "Title should 5 words or more", Toast.LENGTH_SHORT).show();
                }
                if (desc.getText().toString().length() > 500) {
                    Toast.makeText(AddActivity.this, "Description should less than 500", Toast.LENGTH_SHORT).show();
                    check = false;
                }

                if (check == true) {
                    String date = datePicker();
                    Toast.makeText(AddActivity.this, "Add new note success!", Toast.LENGTH_SHORT).show();
                    DatabaseNote databaseNote = new DatabaseNote(getApplicationContext());
                    databaseNote.insertNewNote(username, title.getText().toString(), desc.getText().toString(), date);
                    startActivity(new Intent(AddActivity.this, MainActivity.class));
                }
            }
        });

    }

    public void init() {
        title = (EditText) findViewById(R.id.etInputTitle_AddForm);
        desc = (EditText) findViewById(R.id.etInputDesc_AddForm);
        add = (Button) findViewById(R.id.btnAdd_AddForm);
        cancel = (Button) findViewById(R.id.btnCancel_AddForm);
    }

    public String datePicker() {
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Date d = c.getTime();
        String date = new SimpleDateFormat("E, d MMMM yy").format(d);
        return date;
    }
}
