package com.strengthlog.db;

import android.content.Context;

import com.strengthlog.db.sql.DbHelper;
import com.strengthlog.db.sql.ExerciseContract;
import com.strengthlog.db.sql.LogContract;
import com.strengthlog.db.sql.ProgramContract;
import com.strengthlog.db.sql.ProgramExerciseContract;
import com.strengthlog.db.sql.WeightContract;
import com.strengthlog.utils.VirtualStream;

import java.io.IOException;
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

  private static ProgramContract.EntryHolder defaultProgram(){
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

  public void save(VirtualStream.OVirtualStream stream) throws IOException
  {
    stream.writeArray(exercises);

    stream.writeArray(programs);

    stream.writeArray(programExercise);

    stream.writeArray(logs);

    stream.writeArray(weights);

  }

  public void load(VirtualStream.IVirtualStream stream) throws IOException, ClassNotFoundException
  {
    exercises = stream.readArray();

    programs = stream.readArray();

    programExercise = stream.readArray();

    logs = stream.readArray();

    weights = stream.readArray();

  }
}
