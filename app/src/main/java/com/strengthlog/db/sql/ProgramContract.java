package com.strengthlog.db.sql;

import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.strengthlog.entities.IEntryHolder;

public class ProgramContract {
  private static final String TEXT_TYPE = " TEXT";
  private static final String COMMA_SEP = ",";

  public static final String SQL_CREATE_ENTRIES =
    "CREATE TABLE " + Entry.TABLE_NAME +
      " (" +
      Entry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
      Entry.COLUMN_NAME_PROGRAM + TEXT_TYPE + COMMA_SEP +
      Entry.COLUMN_NAME_WORKOUT + TEXT_TYPE +
      " )";

  public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

  public static abstract class Entry implements BaseColumns {
    public static final String TABLE_NAME = "program";
    public static final String COLUMN_NAME_PROGRAM = "program";
    public static final String COLUMN_NAME_WORKOUT = "workout";
  }

  public static class EntryHolder extends IEntryHolder {
    public String program = "";
    public String workout = "";

    @Override
    public int hashCode()
    {
      int result = 17;
      result = 31*result + program.hashCode();
      result = 31*result + workout.hashCode();
      return result;
    }

    @Override
    public String toString() {
      return String.format("%s,%s", program, workout);
    }

    @Override
    public boolean equals(Object o)
    {
      if (o == this) return true;
      return this.program.equals(((EntryHolder)o).program) && this.workout.equals(((EntryHolder)o).workout);
    }

    public static ProgramContract.EntryHolder fromJson(String json){
      Gson gson = new Gson();
      ProgramContract.EntryHolder obj = gson.fromJson(json, ProgramContract.EntryHolder.class);
      return obj;
    }
  }
}