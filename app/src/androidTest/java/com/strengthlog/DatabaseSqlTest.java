package com.strengthlog;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.strengthlog.db.DataBridge;
import com.strengthlog.db.sql.ExerciseContract;
import com.strengthlog.db.sql.LogContract;
import com.strengthlog.db.sql.ProgramContract;

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
    assertFalse(DataBridge.dataBridge.addLog(item));
    item.key="2";
    assertTrue(DataBridge.dataBridge.addLog(item));
    assertEquals(DataBridge.dataBridge.logs.size(), 2);
  }

  private ProgramContract.EntryHolder getProgramItem() {
    ProgramContract.EntryHolder item = new ProgramContract.EntryHolder();
    item.key = "1";
    item.program = "Texas";
    item.workout = "A";
    item.exercise = "Bench";
    return item;
  }

  public void testProgramAdd() {
    ProgramContract.EntryHolder item = getProgramItem();
    assertTrue(DataBridge.dataBridge.addProgram(item));
    assertFalse(DataBridge.dataBridge.addProgram(item));

    item = getProgramItem();
    item.program = "Linear";
    assertFalse(DataBridge.dataBridge.addProgram(item));
    item.key="2";
    assertTrue(DataBridge.dataBridge.addProgram(item));
    assertEquals(DataBridge.dataBridge.programs.size(), 2);
  }

  public void testExercise(){
    ExerciseContract.EntryHolder item = new ExerciseContract.EntryHolder();
    item.key = "1";
    item.exercise = "Bench";
    assertTrue(DataBridge.dataBridge.addExercise(item));
    assertFalse(DataBridge.dataBridge.addExercise(item));
    item = new ExerciseContract.EntryHolder();
    item.key = "1";
    item.exercise = "Press";
    assertFalse(DataBridge.dataBridge.addExercise(item));

    item.key="2";
    assertTrue(DataBridge.dataBridge.addExercise(item));
    assertEquals(DataBridge.dataBridge.exercises.size(), 2);
  }
}
