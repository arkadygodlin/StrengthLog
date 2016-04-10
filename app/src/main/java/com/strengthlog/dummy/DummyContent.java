package com.strengthlog.dummy;

import android.content.Context;

import com.strengthlog.R;
import com.strengthlog.db.DataBridge;
import com.strengthlog.db.sql.ExerciseContract;
import com.strengthlog.db.sql.LogContract;
import com.strengthlog.db.sql.ProgramContract;
import com.strengthlog.db.sql.ProgramExerciseContract;
import com.strengthlog.db.sql.WeightContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent
{

  /**
   * An array of sample (dummy) items.
   */
  public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

  /**
   * A map of sample (dummy) items, by ID.
   */
  public static Map<Integer, DummyItem> ITEM_MAP = new HashMap<Integer, DummyItem>();


  public static void initItems(Context context, String type){
    ITEM_MAP = new HashMap<Integer, DummyItem>();
    ITEMS = new ArrayList<DummyItem>();

    if (type.equals(context.getString(R.string.title_section2))){
      List<LogContract.EntryHolder> entryHolders = new ArrayList<>(DataBridge.dataBridge.logs);
      int i = 0;
      for(LogContract.EntryHolder entryHolder : entryHolders){
        addItem(new DummyItem(i, entryHolder.toString()));
      }
    }
    else if (type.equals(context.getString(R.string.title_section3))){
      List<ProgramContract.EntryHolder> entryHolders = new ArrayList<>(DataBridge.dataBridge.programs);
      int i = 0;
      for(ProgramContract.EntryHolder entryHolder : entryHolders){
        addItem(new DummyItem(i, entryHolder.toString()));
      }
    }
    else if (type.equals(context.getString(R.string.title_section6))){
      List<ExerciseContract.EntryHolder> entryHolders = new ArrayList<>(DataBridge.dataBridge.exercises);
      int i = 0;
      for(ExerciseContract.EntryHolder entryHolder : entryHolders){
        addItem(new DummyItem(i, entryHolder.toString()));
      }
    }
    else if (type.equals(context.getString(R.string.title_section7))){
      List<ProgramExerciseContract.EntryHolder> entryHolders = new ArrayList<>(DataBridge.dataBridge.programExercise);
      int i = 0;
      for(ProgramExerciseContract.EntryHolder entryHolder : entryHolders){
        addItem(new DummyItem(i, entryHolder.toString()));
      }
    }
    else if (type.equals(context.getString(R.string.title_section8))){
      List<WeightContract.EntryHolder> entryHolders = new ArrayList<>(DataBridge.dataBridge.weights);
      int i = 0;
      for(WeightContract.EntryHolder entryHolder : entryHolders){
        addItem(new DummyItem(i, entryHolder.toString()));
      }
    }
  }

//  static
//  {
//    // Add 3 sample items.
//    addItem(new DummyItem("1", "Item 1"));
//    addItem(new DummyItem("2", "Item 2"));
//    addItem(new DummyItem("3", "Item 3"));
//  }

  private static void addItem(DummyItem item)
  {
    ITEMS.add(item);
    ITEM_MAP.put(item.id, item);
  }

  /**
   * A dummy item representing a piece of content.
   */
  public static class DummyItem
  {
    public int id;
    public String content;

    public DummyItem(int id, String content)
    {
      this.id = id;
      this.content = content;
    }

    @Override
    public String toString()
    {
      return content;
    }
  }
}
