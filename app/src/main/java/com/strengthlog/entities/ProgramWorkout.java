package com.strengthlog.entities;

/**
 * Created by agodlin on 11/18/2015.
 */
public class ProgramWorkout
{
  public String program;
  public String workout;

  public ProgramWorkout()
  {
  }

  public ProgramWorkout(String program, String workout)
  {

    this.program = program;
    this.workout = workout;
  }

  @Override
  public String toString() {
    return String.format("%s,%s", program, workout);
  }

  @Override
  public boolean equals(Object o)
  {
    if (o == this) return true;
    return this.program.equals(((ProgramWorkout)o).program) &&
            this.workout.equals(((ProgramWorkout)o).workout);
  }
}
