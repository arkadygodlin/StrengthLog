package com.strengthlog.db.sql;

import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.strengthlog.entities.IEntryHolder;

public class ProgramExerciseContract
{
  private static final String TEXT_TYPE = " TEXT";
  private static final String INT_TYPE = " INTEGER";
  private static final String COMMA_SEP = ",";

  public static final String SQL_CREATE_ENTRIES =
    "CREATE TABLE " + Entry.TABLE_NAME +
      " (" +
      Entry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
      Entry.COLUMN_NAME_ENTRY_ID + INT_TYPE + COMMA_SEP +
      Entry.COLUMN_NAME_EXERCISE + TEXT_TYPE +
      " )";

  public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

  public static abstract class Entry implements BaseColumns {
    public static final String TABLE_NAME = "program_exercise";
    public static final String COLUMN_NAME_ENTRY_ID = "entryid";
    public static final String COLUMN_NAME_EXERCISE = "exercise";
  }

  public static class EntryHolder extends IEntryHolder {
    public int key = 0;
    public String exercise = "";

    @Override
    public int hashCode()
    {
      int result = 17;
      result = 31*result + key;
      result = 31*result + exercise.hashCode();
      return result;
    }

    @Override
    public String toString() {
      return String.format("%s,%s", key, exercise);
    }

    @Override
    public boolean equals(Object o)
    {
      if (o == this) return true;
      return this.exercise.equals(((EntryHolder)o).exercise) && this.key == ((EntryHolder)o).key;
    }

    public static ProgramExerciseContract.EntryHolder fromJson(String json){
      Gson gson = new Gson();
      ProgramExerciseContract.EntryHolder obj = gson.fromJson(json, ProgramExerciseContract.EntryHolder.class);
      return obj;
    }
  }
}