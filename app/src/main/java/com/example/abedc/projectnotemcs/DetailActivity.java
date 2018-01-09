package com.example.abedc.projectnotemcs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    TextView title, desc;
    Button edit, remove;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();

        SharedPreferences sh = getSharedPreferences("userData", Context.MODE_PRIVATE);
        username = sh.getString("username", "");

        final Intent i = getIntent();
        title.setText("Title : "+i.getStringExtra("title"));
        desc.setText(i.getStringExtra("desc"));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, EditNoteActivity.class);
                intent.putExtra("title2",i.getStringExtra("title"));
                intent.putExtra("desc2", i.getStringExtra("desc"));
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseNote databaseNote = new DatabaseNote(getApplicationContext());
                databaseNote.delete(username, i.getStringExtra("title"));
                Toast.makeText(DetailActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
            }
        });
    }

    public void init() {
        title = (TextView) findViewById(R.id.tvTitleNote_DetailForm);
        desc = (TextView) findViewById(R.id.tvDescTitle_DetailForm);
        edit = (Button) findViewById(R.id.btnEdit_DetailForm);
        remove = (Button) findViewById(R.id.btnRemove_DetailForm);
    }
}
