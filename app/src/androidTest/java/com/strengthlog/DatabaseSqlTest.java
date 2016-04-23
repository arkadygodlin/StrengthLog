package com.strengthlog;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.strengthlog.db.DataBridge;
import com.strengthlog.db.sql.ExerciseContract;
import com.strengthlog.db.sql.LogContract;
import com.strengthlog.db.sql.ProgramContract;
import com.strengthlog.db.sql.ProgramExerciseContract;
import com.strengthlog.db.sql.WeightContract;
import com.strengthlog.utils.Logger;
import com.strengthlog.utils.Utils;
import com.strengthlog.utils.VirtualStream;

import java.io.IOException;

/**
 * Created by agodlin on 4/11/2015.
 */
public class DatabaseSqlTest extends ApplicationTestCase<Application> {

  public DatabaseSqlTest() {
    super(Application.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    DataBridge.initDataBridge(getContext());
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    DataBridge.dataBridge.resetDataBase();
  }

  private LogContract.EntryHolder getLogItem() {
    LogContract.EntryHolder item = new LogContract.EntryHolder();
    item.key = 1;
    item.date = "22-10-2015";
    item.weight = 110;
    item.reps = 3;
    item.sets = 1;
    item.comment = "week 1";
    return item;
  }

  public void testLogAdd() {
    LogContract.EntryHolder item = getLogItem();
    assertTrue(DataBridge.dataBridge.addLog(item));
    assertFalse(DataBridge.dataBridge.addLog(item));

    item = getLogItem();
    item.date = "9-11-2015";
    assertTrue(DataBridge.dataBridge.addLog(item));
    assertEquals(2, DataBridge.dataBridge.logs.size());

    item = getLogItem();
    assertFalse(DataBridge.dataBridge.addLog(item));
    assertEquals(2, DataBridge.dataBridge.logs.size());
  }

  private ProgramContract.EntryHolder getProgramItem() {
    ProgramContract.EntryHolder item = new ProgramContract.EntryHolder();
    item.program = "Texas";
    item.workout = "A";
    return item;
  }

  public void testProgramAdd() {
    ProgramContract.EntryHolder item = getProgramItem();
    assertTrue(DataBridge.dataBridge.addProgram(item));
    assertFalse(DataBridge.dataBridge.addProgram(item));

    item = getProgramItem();
    item.program = "Linear";
    assertTrue(DataBridge.dataBridge.addProgram(item));
    assertEquals(3, DataBridge.dataBridge.programs.size()); //3 because of default program

    item = getProgramItem();
    assertFalse(DataBridge.dataBridge.addProgram(item));
    assertEquals(3, DataBridge.dataBridge.programs.size());
  }

  ExerciseContract.EntryHolder getExercise(){
    ExerciseContract.EntryHolder item = new ExerciseContract.EntryHolder();
    item.exercise = "Bench";
    return item;
  }

  public void testExercise(){
    ExerciseContract.EntryHolder item = getExercise();

    assertTrue(DataBridge.dataBridge.addExercise(item));
    assertFalse(DataBridge.dataBridge.addExercise(item));

    item = getExercise();
    item.exercise = "Press";
    assertTrue(DataBridge.dataBridge.addExercise(item));
    assertEquals(2, DataBridge.dataBridge.exercises.size());

    item = getExercise();
    assertFalse(DataBridge.dataBridge.addExercise(item));
    assertEquals(2, DataBridge.dataBridge.exercises.size());
  }

  ProgramExerciseContract.EntryHolder getProgramExercise(){
    ProgramExerciseContract.EntryHolder item = new ProgramExerciseContract.EntryHolder();
    item.key = 1;
    item.exercise = "Bench";
    return item;
  }

  public void testProgramExercise(){
    ProgramExerciseContract.EntryHolder item = getProgramExercise();

    assertTrue(DataBridge.dataBridge.addProgramExercise(item));
    assertFalse(DataBridge.dataBridge.addProgramExercise(item));

    item = getProgramExercise();
    item.exercise = "Press";
    assertTrue(DataBridge.dataBridge.addProgramExercise(item));
    assertEquals(2, DataBridge.dataBridge.programExercise.size());

    item = getProgramExercise();
    assertFalse(DataBridge.dataBridge.addProgramExercise(item));
    assertEquals(2, DataBridge.dataBridge.programExercise.size());
  }

  public WeightContract.EntryHolder getWeigth(){
    WeightContract.EntryHolder entry = new WeightContract.EntryHolder(1, 85.7, "9-11-2015", "8:20");
    return entry;
  }

  public void testWeight(){
    WeightContract.EntryHolder item = getWeigth();
    assertTrue(DataBridge.dataBridge.addWeight(item));
    assertEquals(1, DataBridge.dataBridge.weights.size());

    item = getWeigth();
    item.weight = 90;
    item.date = "11-11-2015";

    assertTrue(DataBridge.dataBridge.addWeight(item));
    assertEquals(2, DataBridge.dataBridge.weights.size());
  }

  public void testSaveDataToStream(){
    testExercise();
    testWeight();
    testProgramExercise();
    testLogAdd();
    testProgramAdd();

    try
    {
      if (!Utils.isExternalStorageWritable()){
        Logger.e("Test", "No write permissions");
      }

      VirtualStream.OVirtualStream stream = new VirtualStream.OVirtualStream();
      stream.open("test.txt");

      DataBridge.dataBridge.save(stream);

      stream.close();

      VirtualStream.IVirtualStream stream2 = new VirtualStream.IVirtualStream();
      stream2.open("test.txt");

      DataBridge.dataBridge.load(stream2);

      stream2.close();


    } catch (IOException e)
    {
      e.printStackTrace();
    } catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }
}
