package com.strengthlog;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.strengthlog.db.DataBridge;
import com.strengthlog.db.sql.ExerciseContract;
import com.strengthlog.db.sql.LogContract;
import com.strengthlog.db.sql.ProgramContract;
import com.strengthlog.db.sql.ProgramExerciseContract;

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
    item.key = "1";
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
    item.key = "1";
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
    assertEquals(2, DataBridge.dataBridge.programs.size());

    item = getProgramItem();
    assertFalse(DataBridge.dataBridge.addProgram(item));
    assertEquals(2, DataBridge.dataBridge.programs.size());
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
    item.program = "hash";
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
}
