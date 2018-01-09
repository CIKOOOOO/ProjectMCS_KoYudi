package com.example.abedc.projectnotemcs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abedc on 30/12/2017.
 */

public class DatabaseUser extends SQLiteOpenHelper {
    public DatabaseUser(Context context) {
        super(context, "user", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table user("
                + "username varchar not null,"
                + "password varchar not null,"
                + "phoneNumber varchar not null,"
                + "gender varchar not null"
                + ")");
    }

    public void insertNewUser(String username2, String password2, String phoneNumber2, String gender2) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("insert into user values("
                + "'" + username2 + "',"
                + "'" + password2 + "',"
                + "'" + phoneNumber2 + "',"
                + "'" + gender2 + "'"
                + ")");
    }

    public Cursor getAllUser() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("select * from user", null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
