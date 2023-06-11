package com.JNU.keepaccounts.data.mapper;

import android.database.sqlite.SQLiteDatabase;

import com.JNU.keepaccounts.data.DatabaseHelper;

public class EventItemMapper {
    DatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    public EventItemMapper(DatabaseHelper songGuoDatabaseHelper){
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();

    }
}
