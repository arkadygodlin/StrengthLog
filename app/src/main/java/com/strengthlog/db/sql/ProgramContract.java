package com.strengthlog.db.sql;

import android.provider.BaseColumns;

public class ProgramContract {
  private static final String TEXT_TYPE = " TEXT";
  private static final String COMMA_SEP = ",";

  public static final String SQL_CREATE_ENTRIES =
    "CREATE TABLE " + Entry.TABLE_NAME +
      " (" +
      Entry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
      Entry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
      Entry.COLUMN_NAME_PROGRAM + TEXT_TYPE + COMMA_SEP +
      Entry.COLUMN_NAME_WORKOUT + TEXT_TYPE + COMMA_SEP +
      Entry.COLUMN_NAME_EXERCISE + TEXT_TYPE +
      " )";

  public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

  public static abstract class Entry implements BaseColumns {
    public static final String TABLE_NAME = "program";
    public static final String COLUMN_NAME_ENTRY_ID = "entryid";
    public static final String COLUMN_NAME_PROGRAM = "program";
    public static final String COLUMN_NAME_WORKOUT = "workout";
    public static final String COLUMN_NAME_EXERCISE = "exercise";
  }

  public static class EntryHolder {
    public String key = "";
    public String program = "";
    public String workout = "";
    public String exercise = "";

    @Override
    public int hashCode()
    {
      int result = 17;
      result = 31*result + program.hashCode();
      result = 31*result + workout.hashCode();
      result = 31*result + exercise.hashCode();
      return result;
    }

  }
}