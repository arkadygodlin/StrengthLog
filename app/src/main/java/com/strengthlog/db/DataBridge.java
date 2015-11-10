package com.strengthlog.db;

import android.content.Context;

import com.strengthlog.db.sql.DbHelper;
import com.strengthlog.db.sql.ExerciseContract;
import com.strengthlog.db.sql.LogContract;
import com.strengthlog.db.sql.ProgramContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agodlin on 3/21/2015.
 */


public class DataBridge {
  public static DataBridge dataBridge;
  Context context;
  DbHelper sqlDb;

  //TODO add wrappers
  public List<ExerciseContract.EntryHolder> exercises = null;
  public Map<String, ProgramContract.EntryHolder> programs = null;
  public Map<String, LogContract.EntryHolder> logs = null;

  /*
  should be called once
   */
  public static void initDataBridge(Context context){
    if (DataBridge.dataBridge == null){
      DataBridge dataBridge = new DataBridge(context);
      DataBridge.dataBridge = dataBridge;
      DataBridge.dataBridge.loadData();
    }
  }

  private DataBridge(Context context){
    this.context = context;
    sqlDb = new DbHelper(context);
  }

  public void resetDataBase(){
    sqlDb.deleteDataBase(context);
    DataBridge.dataBridge = null;
    initDataBridge(context);
  }

  public void loadData(){
    exercises = loadExercises();

    List<ProgramContract.EntryHolder> programsList = loadProgram();
    programs = new HashMap<>();
    for(ProgramContract.EntryHolder entry : programsList){
      programs.put(entry.key, entry);
    }

    List<LogContract.EntryHolder> logsList = loadLogs();
    logs = new HashMap<>();
    for(LogContract.EntryHolder entry : logsList){
      logs.put(entry.key, entry);
    }
  }

  public boolean addLog(LogContract.EntryHolder entryHolder){
    if (containsLog(entryHolder)){
      return false;
    }
    logs.put(entryHolder.key, entryHolder);
    return sqlDb.insertLog(entryHolder) > 0;
  }

  public boolean addProgram(ProgramContract.EntryHolder entryHolder){
    if (containsProgram(entryHolder)){
      return false;
    }
    programs.put(entryHolder.key, entryHolder);
    return sqlDb.insertProgram(entryHolder) > 0;
  }

  public boolean addExercise(ExerciseContract.EntryHolder entryHolder){
    if (containsExercise(entryHolder)){
      return false;
    }
    exercises.add(entryHolder);
    return sqlDb.insertExercise(entryHolder) > 0;
  }

  public boolean containsProgram(ProgramContract.EntryHolder entryHolder){
    return programs.containsKey(entryHolder.key);
  }

  public boolean containsLog(LogContract.EntryHolder entryHolder){
    return logs.containsKey(entryHolder.key);
  }

  public boolean containsExercise(ExerciseContract.EntryHolder entryHolder){
    return exercises.contains(entryHolder);
  }

  private List<ExerciseContract.EntryHolder> loadExercises(){
    return sqlDb.getExerciseEntries();
  }

  private List<ProgramContract.EntryHolder> loadProgram(){
    return sqlDb.getProgramEntries();
  }

  private List<LogContract.EntryHolder> loadLogs(){
    return sqlDb.getAllEntryLogs();
  }
}
