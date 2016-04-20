package com.strengthlog.db.sql;

import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.strengthlog.entities.IEntryHolder;

/**
 * Created by agodlin on 12/27/2015.
 */
public class WeightContract
{
  private static final String TEXT_TYPE = " TEXT";
  private static final String INT_TYPE = " INTEGER";
  private static final String FLOAT_TYPE = " REAL";
  private static final String COMMA_SEP = ",";

  public static final String SQL_CREATE_ENTRIES =
    "CREATE TABLE " + Entry.TABLE_NAME +
      " (" +
      Entry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
      Entry.COLUMN_NAME_ENTRY_ID + INT_TYPE + COMMA_SEP +
      Entry.COLUMN_NAME_WEIGHT + FLOAT_TYPE + COMMA_SEP +
      Entry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
      Entry.COLUMN_NAME_TIME + TEXT_TYPE +
      " )";

  public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

  public static abstract class Entry implements BaseColumns
  {
    public static final String TABLE_NAME = "weigth";
    public static final String COLUMN_NAME_ENTRY_ID = "entryid";
    public static final String COLUMN_NAME_WEIGHT = "weight";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_TIME = "time";
  }

  public static class EntryHolder extends IEntryHolder {
    public int key = 0;
    public double weight = 0;
    public String date = null;
    public String time = null;
    public EntryHolder(){}

    public EntryHolder(int key, double weight, String date, String time) {
      this.weight = weight;
      this.key = key;
      this.date = date;
      this.time = time;
    }

    @Override
    public String toString() {
      return String.format("%.2f", weight);
    }

    @Override
    public boolean equals(Object o)
    {
      if (o == this) return true;
      return this.date.equals(((EntryHolder) o).date) && this.time.equals(((EntryHolder) o).time);
    }

    public static WeightContract.EntryHolder fromJson(String json){
      Gson gson = new Gson();
      WeightContract.EntryHolder obj = gson.fromJson(json, WeightContract.EntryHolder.class);
      return obj;
    }
  }
}
