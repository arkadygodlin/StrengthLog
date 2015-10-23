package com.strengthlog.utils;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agodlin on 7/1/2015.
 */
public class Utils
{

  public static boolean isExternalStorageWritable()
  {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state))
    {
      return true;
    }
    return false;
  }

  /* Checks if external storage is available to at least read */
  public static boolean isExternalStorageReadable()
  {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
    {
      return true;
    }
    return false;
  }

  public static File getAlbumStorageDir(String albumName)
  {
    // Get the directory for the user's public pictures directory.
    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
    if (!file.mkdirs())
    {
      Log.e("Error", "Directory not created");
    }
    return file;
  }

  public static void writeBytesToFile(Context context, byte[] dataByte, String fileName)
  {
    if (!isExternalStorageWritable() || !isExternalStorageReadable())
    {
      Log.d("Error", "not writable");
    }
    File path = getAlbumStorageDir(Environment.DIRECTORY_DCIM);
    File file = new File(path, fileName);

    writeBytesToFile(dataByte, file);

    makeFileAvailableToUser(context, file);
  }

  public static void writeBytesToFile(byte[] dataByte, File file)
  {
    try
    {
      OutputStream os = new FileOutputStream(file);
      os.write(dataByte);
      os.close();
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
      Log.i("sad", "******* File not found. Did you" + " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  // Tell the media scanner about the new file so that it is
  // immediately available to the user.
  public static void makeFileAvailableToUser(Context context, File file)
  {
    MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener()
      {
        public void onScanCompleted(String path, Uri uri)
        {
          Log.i("ExternalStorage", "Scanned " + path + ":");
          Log.i("ExternalStorage", "-> uri=" + uri);
        }
      });
  }

  public void saveProgramToFilePrivate(Context context, String program) throws IOException
  {

    String filename = program + ".txt";

    FileOutputStream fos;

    fos = context.openFileOutput(filename, Context.MODE_APPEND);


    XmlSerializer serializer = Xml.newSerializer();
    serializer.setOutput(fos, "UTF-8");
    serializer.startDocument(null, Boolean.valueOf(true));
    serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

    serializer.startTag(null, "Program");

    List<String> workouts = new ArrayList<>();

    for (String workout : workouts)
    {

      serializer.startTag(null, "exercise");

      serializer.text(workout);

      serializer.endTag(null, "exercise");
    }
    serializer.endDocument();

    serializer.flush();

    fos.close();

    FileInputStream fis = null;
    InputStreamReader isr = null;

    fis = context.openFileInput(filename);
    isr = new InputStreamReader(fis);
    char[] inputBuffer = new char[fis.available()];
    isr.read(inputBuffer);
    String data = new String(inputBuffer);
    isr.close();
    fis.close();

  }
}
