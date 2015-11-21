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
            "CREATE TABLE " + Entry.TABLE_NAME +
            " (" +
            Entry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            Entry.COLUMN_NAME_ENTRY_ID + INT_TYPE + COMMA_SEP +
            Entry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
            Entry.COLUMN_NAME_WEIGHT + FLOAT_TYPE + COMMA_SEP +
            Entry.COLUMN_NAME_REPS + INT_TYPE + COMMA_SEP +
            Entry.COLUMN_NAME_SETS + INT_TYPE + COMMA_SEP +
            Entry.COLUMN_NAME_COMMENT + TEXT_TYPE +
            " )";

  public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

  public static abstract class Entry implements BaseColumns {
    public static final String TABLE_NAME = "log";
    public static final String COLUMN_NAME_ENTRY_ID = "entryid";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_WEIGHT = "weight";
    public static final String COLUMN_NAME_REPS = "reps";
    public static final String COLUMN_NAME_SETS = "sets";
    public static final String COLUMN_NAME_COMMENT = "comment";
  }

  public static class EntryHolder {
    public int key = 0;
    public String date = "";
    public float weight = 0;
    public int reps = 0;
    public int sets = 0;
    public String comment = "";
    public EntryHolder(){}

    public EntryHolder(int sets, String date, float weight,
                       int reps, int key, String comment) {
      this.sets = sets;
      this.date = date;
      this.weight = weight;
      this.reps = reps;
      this.key = key;
      this.comment = comment;
    }

    @Override
    public String toString() {
      return String.format("%s,%.2f,%d,%d,%s", date, weight, reps, sets, comment);
    }

    @Override
    public boolean equals(Object o)
    {
      if (o == this) return true;
      return this.date.equals(((EntryHolder)o).date);
    }

  }
}