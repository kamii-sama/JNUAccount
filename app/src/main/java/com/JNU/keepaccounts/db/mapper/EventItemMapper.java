package com.JNU.keepaccounts.db.mapper;

import android.database.sqlite.SQLiteDatabase;

import com.JNU.keepaccounts.db.DatabaseHelper;

public class EventItemMapper {
    DatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    public EventItemMapper(DatabaseHelper songGuoDatabaseHelper){
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();

    }
}
