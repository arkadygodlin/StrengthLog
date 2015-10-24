package com.strengthlog.db.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agodlin on 4/10/2015.
 */
public class DbHelper extends SQLiteOpenHelper
{
  // If you change the database schema, you must increment the database version.
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "StrengthLog.db";

  public DbHelper(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);

  }

  public void onCreate(SQLiteDatabase db)
  {
    db.execSQL(LogContract.SQL_CREATE_ENTRIES);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    // This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over
    db.execSQL(LogContract.SQL_DELETE_ENTRIES);
    onCreate(db);
  }

  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    onUpgrade(db, oldVersion, newVersion);
  }

  public void deleteDataBase(Context context)
  {
    context.deleteDatabase(DATABASE_NAME);
  }

  public long insertLog(LogContract.EntryHolder item)
  {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(LogContract.LogEntry.COLUMN_NAME_ENTRY_ID, item.key);
    values.put(LogContract.LogEntry.COLUMN_NAME_PROGRAM, item.program);
    values.put(LogContract.LogEntry.COLUMN_NAME_WORKOUT, item.workout);
    values.put(LogContract.LogEntry.COLUMN_NAME_EXERCISE, item.exercise);
    values.put(LogContract.LogEntry.COLUMN_NAME_DATE, item.date);
    values.put(LogContract.LogEntry.COLUMN_NAME_WEIGHT, item.weight);
    values.put(LogContract.LogEntry.COLUMN_NAME_REPS, item.reps);
    values.put(LogContract.LogEntry.COLUMN_NAME_SETS, item.sets);
    values.put(LogContract.LogEntry.COLUMN_NAME_COMMENT, item.comment);
    return db.insert(LogContract.LogEntry.TABLE_NAME, null, values);
  }

  public List<LogContract.EntryHolder> getAllEntryLogs()
  {
    List<LogContract.EntryHolder> workoutData = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    // How you want the results sorted in the resulting Cursor
    String sortOrder = LogContract.LogEntry.COLUMN_NAME_DATE + " DESC";

    Cursor cursor = db.query(LogContract.LogEntry.TABLE_NAME,  // The table to query
      null,                                       // The columns to return
      null,                                       // The columns for the WHERE clause
      null,                                       // The values for the WHERE clause
      null,                                       // don't group the rows
      null,                                       // don't filter by row groups
      sortOrder                                   // The sort order
    );
    LogContract.EntryHolder item;
    while (cursor.moveToNext())
    {
      item = new LogContract.EntryHolder();
      item.key = cursor.getString(1);
      item.program = cursor.getString(2);
      item.workout = cursor.getString(3);
      item.date = cursor.getString(4);
      item.weight = cursor.getFloat(5);
      item.reps = cursor.getInt(6);
      item.sets = cursor.getInt(7);
      item.comment = cursor.getString(8);
      workoutData.add(item);
    }

    cursor.close();

    return workoutData;
  }
}