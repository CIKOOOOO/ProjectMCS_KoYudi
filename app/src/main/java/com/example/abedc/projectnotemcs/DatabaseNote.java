package com.example.abedc.projectnotemcs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abedc on 30/12/2017.
 */

public class DatabaseNote extends SQLiteOpenHelper {
    public DatabaseNote(Context context) {
        super(context, "note", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table note("
                + "username varchar not null,"
                + "title varchar not null,"
                + "description varchar not null,"
                + "date varchar not null"
                + ")");
    }

    public void insertNewNote(String username, String title, String desc, String date) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("insert into note values("
                + "'" + username + "',"
                + "'" + title + "',"
                + "'" + desc + "',"
                + "'" + date + "'"
                + ")");
    }

    public Cursor getUser(String username) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("select * from note where username = '" + username + "'", null);
    }

    public void delete(String username, String title) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("delete from note where username = '" + username + "' and title = '" + title + "'");
    }

    public void editNoteTitle(String username, String title, String title2, String desc2) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("update `note` set `title` = '" + title + "' " +
                "where `note`.`username` = '" + username + "' and `note`.`title` = '" + title2 + "' " +
                "and `note`.`description` = '" + desc2 + "'");
    }

    public void editNoteDesc(String username, String desc, String title2, String desc2) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("update `note` set `description` = '" + desc + "' " +
                "where `note`.`username` = '" + username + "' and `note`.`title` = '" + title2 + "' " +
                "and `note`.`description` = '" + desc2 + "'");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
