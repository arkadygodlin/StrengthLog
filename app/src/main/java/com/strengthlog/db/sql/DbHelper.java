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
    db.execSQL(ProgramContract.SQL_CREATE_ENTRIES);
    db.execSQL(ExerciseContract.SQL_CREATE_ENTRIES);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    // This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over
    db.execSQL(LogContract.SQL_DELETE_ENTRIES);
    db.execSQL(ProgramContract.SQL_DELETE_ENTRIES);
    db.execSQL(ExerciseContract.SQL_DELETE_ENTRIES);
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
  values.put(LogContract.Entry.COLUMN_NAME_ENTRY_ID, item.key);
  values.put(LogContract.Entry.COLUMN_NAME_DATE, item.date);
  values.put(LogContract.Entry.COLUMN_NAME_WEIGHT, item.weight);
  values.put(LogContract.Entry.COLUMN_NAME_REPS, item.reps);
  values.put(LogContract.Entry.COLUMN_NAME_SETS, item.sets);
  values.put(LogContract.Entry.COLUMN_NAME_COMMENT, item.comment);
  long retVal = db.insert(LogContract.Entry.TABLE_NAME, null, values);
  db.close();
  return retVal;
}

  public List<LogContract.EntryHolder> getAllEntryLogs()
  {
    List<LogContract.EntryHolder> workoutData = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    // How you want the results sorted in the resulting Cursor
    String sortOrder = LogContract.Entry.COLUMN_NAME_DATE + " DESC";

    Cursor cursor = db.query(LogContract.Entry.TABLE_NAME,  // The table to query
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
      item.date = cursor.getString(2);
      item.weight = cursor.getFloat(3);
      item.reps = cursor.getInt(4);
      item.sets = cursor.getInt(5);
      item.comment = cursor.getString(6);
      workoutData.add(item);
    }

    cursor.close();
    db.close();
    return workoutData;
  }

  public long insertProgram(ProgramContract.EntryHolder item)
  {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(ProgramContract.Entry.COLUMN_NAME_ENTRY_ID, item.key);
    values.put(ProgramContract.Entry.COLUMN_NAME_PROGRAM, item.program);
    values.put(ProgramContract.Entry.COLUMN_NAME_WORKOUT, item.workout);
    values.put(ProgramContract.Entry.COLUMN_NAME_EXERCISE, item.exercise);
    long retVal = db.insert(ProgramContract.Entry.TABLE_NAME, null, values);
    db.close();
    return retVal;
  }

  public List<ProgramContract.EntryHolder> getProgramEntries()
  {
    List<ProgramContract.EntryHolder> workoutData = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(ProgramContract.Entry.TABLE_NAME,  // The table to query
      null,                                       // The columns to return
      null,                                       // The columns for the WHERE clause
      null,                                       // The values for the WHERE clause
      null,                                       // don't group the rows
      null,                                       // don't filter by row groups
      null                                   // The sort order
    );
    ProgramContract.EntryHolder item;
    while (cursor.moveToNext())
    {
      item = new ProgramContract.EntryHolder();
      item.key = cursor.getString(1);
      item.program = cursor.getString(2);
      item.workout = cursor.getString(3);
      item.exercise = cursor.getString(4);
      workoutData.add(item);
    }

    cursor.close();
    db.close();
    return workoutData;
  }

  public boolean isProgramExists(ProgramContract.EntryHolder item)
  {
    List<ProgramContract.EntryHolder> workoutData = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    String selection = ProgramContract.Entry.COLUMN_NAME_PROGRAM + " = ?" + " and " +
                       ProgramContract.Entry.COLUMN_NAME_WORKOUT + " = ?" + " and " +
                       ProgramContract.Entry.COLUMN_NAME_EXERCISE + " = ?";
    String[] selectionArgs = {item.program, item.workout, item.exercise};

    Cursor cursor = db.query(ProgramContract.Entry.TABLE_NAME,  // The table to query
      null,                                       // The columns to return
      selection,                                       // The columns for the WHERE clause
      selectionArgs,                                       // The values for the WHERE clause
      null,                                       // don't group the rows
      null,                                       // don't filter by row groups
      null                                   // The sort order
    );
    boolean found = cursor.moveToNext();
    cursor.close();
    db.close();
    return found;
  }

  public long insertExercise(ExerciseContract.EntryHolder item)
  {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(ExerciseContract.Entry.COLUMN_NAME_ENTRY_ID, item.key);
    values.put(ExerciseContract.Entry.COLUMN_NAME_EXERCISE, item.exercise);
    long retVal = db.insert(ExerciseContract.Entry.TABLE_NAME, null, values);
    db.close();
    return retVal;
  }

  public List<ExerciseContract.EntryHolder> getExerciseEntries()
  {
    List<ExerciseContract.EntryHolder> workoutData = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(ExerciseContract.Entry.TABLE_NAME,  // The table to query
      null,                                       // The columns to return
      null,                                       // The columns for the WHERE clause
      null,                                       // The values for the WHERE clause
      null,                                       // don't group the rows
      null,                                       // don't filter by row groups
      null                                   // The sort order
    );
    ExerciseContract.EntryHolder item;
    while (cursor.moveToNext())
    {
      item = new ExerciseContract.EntryHolder();
      item.key = cursor.getString(1);
      item.exercise = cursor.getString(2);
      workoutData.add(item);
    }

    cursor.close();
    db.close();
    return workoutData;
  }

}