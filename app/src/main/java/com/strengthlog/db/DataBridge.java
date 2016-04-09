package com.strengthlog.db;

import android.content.Context;

import com.strengthlog.db.sql.DbHelper;
import com.strengthlog.db.sql.ExerciseContract;
import com.strengthlog.db.sql.LogContract;
import com.strengthlog.db.sql.ProgramContract;
import com.strengthlog.db.sql.ProgramExerciseContract;
import com.strengthlog.db.sql.WeightContract;
import com.strengthlog.entities.IEntryHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agodlin on 3/21/2015.
 */


public class DataBridge {
  public static DataBridge dataBridge;
  Context context;
  DbHelper sqlDb;

  //TODO add wrappers
  //This are cache members.
  public List<ExerciseContract.EntryHolder> exercises = null;
  public List<ProgramContract.EntryHolder> programs = null;
  public List<ProgramExerciseContract.EntryHolder> programExercise = null;
  public List<LogContract.EntryHolder> logs = null;
  public List<WeightContract.EntryHolder> weights = null;

  /*
  should be called once
   */
  public static void initDataBridge(Context context){
    if (DataBridge.dataBridge == null){
      DataBridge dataBridge = new DataBridge(context);
      DataBridge.dataBridge = dataBridge;
      DataBridge.dataBridge.loadData();
      DataBridge.dataBridge.addDefaultProgram();

    }
  }

  private void addDefaultProgram(){
    ProgramContract.EntryHolder entryHolder = defaultProgram();
    addProgram(entryHolder);
  }

  static public ProgramContract.EntryHolder defaultProgram(){
    ProgramContract.EntryHolder entryHolder = new ProgramContract.EntryHolder();
    entryHolder.program = "";
    entryHolder.workout = "";
    return entryHolder;
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

    programs = loadProgram();

    logs = loadLogs();

    programExercise = loadProgramExercise();

    weights = loadWeigths();
  }

  public boolean addLog(LogContract.EntryHolder entryHolder){
    if (containsLog(entryHolder)){
      return false;
    }
    logs.add(entryHolder);
    return sqlDb.insertLog(entryHolder) > 0;
  }

  public boolean addProgram(ProgramContract.EntryHolder entryHolder){
    if (containsProgram(entryHolder)){
      return false;
    }
    programs.add(entryHolder);
    return sqlDb.insertProgram(entryHolder) > 0;
  }

  public boolean addExercise(ExerciseContract.EntryHolder entryHolder){
    if (containsExercise(entryHolder)){
      return false;
    }
    exercises.add(entryHolder);
    return sqlDb.insertExercise(entryHolder) > 0;
  }

  public boolean addProgramExercise(ProgramExerciseContract.EntryHolder entryHolder){
    if (containsProgramExercise(entryHolder)){
      return false;
    }
    programExercise.add(entryHolder);
    return sqlDb.insertProgramExercise(entryHolder) > 0;
  }

  public boolean containsProgram(ProgramContract.EntryHolder entryHolder){
    return programs.contains(entryHolder);
  }

  public boolean containsLog(LogContract.EntryHolder entryHolder){
    return logs.contains(entryHolder);
  }

  public boolean containsExercise(ExerciseContract.EntryHolder entryHolder){
    return exercises.contains(entryHolder);
  }

  public boolean containsProgramExercise(ProgramExerciseContract.EntryHolder entryHolder){
    return programExercise.contains(entryHolder);
  }

  public List<ProgramContract.EntryHolder> retrieveAllPrograms(){
    List<ProgramContract.EntryHolder> list = new ArrayList<>();
    for(ProgramContract.EntryHolder entryHolder : programs){
      list.add(entryHolder);
    }
    return list;
  }

  public List<String> retrieveAllExercise(){
    List<String> list = new ArrayList<>();
    for(ExerciseContract.EntryHolder entryHolder : exercises){
      list.add(entryHolder.exercise);
    }
    return list;
  }

  public List<String> retrieveExercises(ProgramContract.EntryHolder holder){
    if (holder.equals(defaultProgram())){
      return retrieveAllExercise();
    }
    List<String> list = new ArrayList<>();
    for(ProgramExerciseContract.EntryHolder entryHolder : programExercise){
      if (entryHolder.key == holder.hashCode())
        list.add(entryHolder.exercise);
    }
    return list;
  }

  private List<ExerciseContract.EntryHolder> loadExercises(){
    return sqlDb.getExerciseEntries();
  }

  private List<ProgramContract.EntryHolder> loadProgram(){
    return sqlDb.getProgramEntries();
  }

  private List<ProgramExerciseContract.EntryHolder> loadProgramExercise(){
    return sqlDb.getProgramExerciseEntries();
  }

  private List<LogContract.EntryHolder> loadLogs(){
    return sqlDb.getAllEntryLogs();
  }

  private List<WeightContract.EntryHolder> loadWeigths(){
    return sqlDb.getWeightEntries();
  }

  public boolean addWeight(WeightContract.EntryHolder entryHolder){
    weights.add(entryHolder);
    return sqlDb.insertWeight(entryHolder) > 0;
  }

  public List<String> saveHistory(){
    List<String> lines = new ArrayList<>();

    lines.add(String.valueOf(weights.size()));
    for(IEntryHolder entryHolder : weights){
      lines.add(entryHolder.toJson());
    }

    lines.add(String.valueOf(programs.size()));
    for(IEntryHolder entryHolder : programs){
      lines.add(entryHolder.toJson());
    }

    lines.add(String.valueOf(exercises.size()));
    for(IEntryHolder entryHolder : exercises){
      lines.add(entryHolder.toJson());
    }

    lines.add(String.valueOf(programExercise.size()));
    for(IEntryHolder entryHolder : programExercise){
      lines.add(entryHolder.toJson());
    }

    lines.add(String.valueOf(logs.size()));
    for(IEntryHolder entryHolder : logs){
      lines.add(entryHolder.toJson());
    }

    return lines;
  }

  public void loadHistory(List<String> lines){
    int index = 0;

    int size = Integer.getInteger(lines.get(index++));
    for(int i = 0 ; i < size ; i++){
      weights.add(WeightContract.EntryHolder.fromJson(lines.get(index++)));
    }

    size = Integer.getInteger(lines.get(index++));
    for(int i = 0 ; i < size ; i++){
      programs.add(ProgramContract.EntryHolder.fromJson(lines.get(index++)));
    }

    size = Integer.getInteger(lines.get(index++));
    for(int i = 0 ; i < size ; i++){
      exercises.add(ExerciseContract.EntryHolder.fromJson(lines.get(index++)));
    }

    size = Integer.getInteger(lines.get(index++));
    for(int i = 0 ; i < size ; i++){
      programExercise.add(ProgramExerciseContract.EntryHolder.fromJson(lines.get(index++)));
    }

    size = Integer.getInteger(lines.get(index++));
    for(int i = 0 ; i < size ; i++){
      logs.add(LogContract.EntryHolder.fromJson(lines.get(index++)));
    }
  }
}
