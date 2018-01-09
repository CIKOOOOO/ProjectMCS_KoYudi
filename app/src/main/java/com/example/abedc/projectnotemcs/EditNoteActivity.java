package com.example.abedc.projectnotemcs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {

    EditText title, desc;
    Button edit, cancel;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        init();
        SharedPreferences sh = getSharedPreferences("userData", Context.MODE_PRIVATE);
        username = sh.getString("username", "");

        Intent i = getIntent();
        final String title2 = i.getStringExtra("title2");
        final String desc2 = i.getStringExtra("desc2");

        title.setText(title2);
        desc.setText(desc2);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseNote databaseNote = new DatabaseNote(getApplicationContext());
                databaseNote.editNoteTitle(username, title.getText().toString(),title2,desc2);
                databaseNote.editNoteDesc(username, desc.getText().toString(),title2,desc2);
                Toast.makeText(EditNoteActivity.this, "Success to edit!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditNoteActivity.this, MainActivity.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditNoteActivity.this, "Failed to edit!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditNoteActivity.this, MainActivity.class));
            }
        });

    }

    public void init() {
        title = (EditText) findViewById(R.id.etInputTitle_EditForm);
        desc = (EditText) findViewById(R.id.etInputDesc_EditForm);
        edit = (Button) findViewById(R.id.btnEdit_EditForm);
        cancel = (Button) findViewById(R.id.btnCancel_EditForm);
    }
}
