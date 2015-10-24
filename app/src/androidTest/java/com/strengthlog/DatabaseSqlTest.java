package com.strengthlog;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.strengthlog.db.sql.DbHelper;
import com.strengthlog.db.sql.LogContract;

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
    DbHelper db = new DbHelper(getContext());
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    DbHelper db = new DbHelper(getContext());
    db.deleteDataBase(getContext());
  }

  private LogContract.EntryHolder getLogItem() {
    LogContract.EntryHolder item = new LogContract.EntryHolder();
    item.program = "Texas";
    item.workout = "A";
    item.exercise = "bench";
    item.date = "22-10-2015";
    item.weight = 110;
    item.reps = 3;
    item.sets = 1;
    item.comment = "week 1";
    return item;
  }

  public void testLogAdd() {
    DbHelper db = new DbHelper(getContext());

    LogContract.EntryHolder item = getLogItem();
    assertTrue(db.insertLog(item) > 0);
    assertTrue(db.getAllEntryLogs().size() > 0);
  }

}
