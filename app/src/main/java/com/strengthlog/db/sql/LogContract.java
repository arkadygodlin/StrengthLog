package com.strengthlog.db.sql;

import android.provider.BaseColumns;

/**
 * Created by agodlin on 10/23/2015.
 */

public class LogContract {
  private static final String TEXT_TYPE = " TEXT";
  private static final String INT_TYPE = " INTEGER";
  private static final String FLOAT_TYPE = " REAL";
  private static final String COMMA_SEP = ",";

  public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LogEntry.TABLE_NAME +
            " (" +
            LogEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            LogEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            LogEntry.COLUMN_NAME_PROGRAM + TEXT_TYPE + COMMA_SEP +
            LogEntry.COLUMN_NAME_WORKOUT + TEXT_TYPE + COMMA_SEP +
            LogEntry.COLUMN_NAME_EXERCISE + TEXT_TYPE + COMMA_SEP +
            LogEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
            LogEntry.COLUMN_NAME_WEIGHT + FLOAT_TYPE + COMMA_SEP +
            LogEntry.COLUMN_NAME_REPS + INT_TYPE + COMMA_SEP +
            LogEntry.COLUMN_NAME_SETS + INT_TYPE + COMMA_SEP +
            LogEntry.COLUMN_NAME_COMMENT + TEXT_TYPE +
            " )";

  public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + LogEntry.TABLE_NAME;

  public static abstract class LogEntry implements BaseColumns {
    public static final String TABLE_NAME = "log";
    public static final String COLUMN_NAME_ENTRY_ID = "entryid";
    public static final String COLUMN_NAME_PROGRAM = "program";
    public static final String COLUMN_NAME_WORKOUT = "workout";
    public static final String COLUMN_NAME_EXERCISE = "exercise";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_WEIGHT = "weight";
    public static final String COLUMN_NAME_REPS = "reps";
    public static final String COLUMN_NAME_SETS = "sets";
    public static final String COLUMN_NAME_COMMENT = "comment";
  }

  public static class EntryHolder {
    public String key = "";
    public String program = "";
    public String workout = "";
    public String exercise = "";
    public String date = "";
    public float weight = 0;
    public int reps = 0;
    public int sets = 0;
    public String comment = "";
    public EntryHolder(){}

    public EntryHolder(int sets, String program, String workout, String exercise, String date, float weight,
                       int reps, String key, String comment) {
      this.sets = sets;
      this.program = program;
      this.workout = workout;
      this.exercise = exercise;
      this.date = date;
      this.weight = weight;
      this.reps = reps;
      this.key = key;
      this.comment = comment;
    }
  }
}