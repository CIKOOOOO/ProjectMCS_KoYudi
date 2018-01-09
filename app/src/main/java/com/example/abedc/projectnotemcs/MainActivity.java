package com.example.abedc.projectnotemcs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Note note = new Note();
    int counter = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAddNote:
                startActivity(new Intent(MainActivity.this, AddActivity.class));
                break;

            case R.id.miMaps :
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
                break;

            case R.id.miLogout:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sh = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String username = sh.getString("username", "");

        DatabaseNote databaseNote = new DatabaseNote(getApplicationContext());
        Cursor c = databaseNote.getUser(username);
        while (c.moveToNext()) {
            note.title[counter] = c.getString(1);
            note.desc[counter] = c.getString(2);
            note.date[counter] = c.getString(3);
            counter++;
        }

        CustomListView customListView = new CustomListView();
        listView = (ListView) findViewById(R.id.lvNote_MainForm);
        listView.setAdapter(customListView);
    }

    class CustomListView extends BaseAdapter {

        @Override
        public int getCount() {
            return counter;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_listview_mainform, null);
            TextView title2 = view.findViewById(R.id.tvTitleNote_CustomListViewMain);
            TextView date2 = view.findViewById(R.id.tvDate_CustomListViewMain);
            Button detail = view.findViewById(R.id.btnDetail_CustomListViewMain);

            title2.setText(note.title[i]);
            date2.setText(note.date[i]);

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("title", note.title[i]);
                    intent.putExtra("desc", note.desc[i]);
                    startActivity(intent);
                }
            });

            return view;
        }
    }
}
