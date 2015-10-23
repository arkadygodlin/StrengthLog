package com.strengthlog.utils;

import android.util.Log;

/**
 * Created by agodlin on 7/1/2015.
 */
public class Logger
{
  public static final boolean IS_LOGGING = true;

  public static void d(String tag, String message)
  {
    if (IS_LOGGING)
    {
      Log.d(tag, message);
    }
  }

  public static void e(String tag, String message)
  {
    if (IS_LOGGING)
    {
      Log.e(tag, message);
    }
  }

  public static void i(String tag, String message)
  {
    if (IS_LOGGING)
    {
      Log.i(tag, message);
    }
  }

  public static void w(String tag, String message)
  {
    if (IS_LOGGING)
    {
      Log.w(tag, message);
    }
  }
}