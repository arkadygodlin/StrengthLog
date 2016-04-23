package com.strengthlog.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by agodlin on 4/20/2016.
 */
public class VirtualStream
{

  public static class OVirtualStream {
    FileOutputStream fop = null;
    ObjectOutput s = null;
    public void open(String fileName) throws IOException
    {
      File path = Utils.getAlbumStorageDir(Environment.DIRECTORY_DCIM);
      File file = new File(path, fileName);
      fop = new FileOutputStream(file);

      s = new ObjectOutputStream(fop);
    }

    public <T> void writeArray(Collection<T> collection) throws IOException
    {
      s.writeObject(collection.size());
      for(T data : collection)
        s.writeObject(data);
    }

    public <T> void write(T data) throws IOException
    {
      s.writeObject(data);
    }

    public void close() throws IOException
    {
      s.flush();
      s.close();
      fop.flush();
      fop.close();
    }
  }

  public static class IVirtualStream {
    FileInputStream fop = null;
    ObjectInput s = null;
    public void open(String fileName) throws IOException
    {
      File path = Utils.getAlbumStorageDir(Environment.DIRECTORY_DCIM);
      File file = new File(path, fileName);

      fop = new FileInputStream(file);

      s = new ObjectInputStream(fop);
    }

    public <T> List<T> readArray() throws IOException, ClassNotFoundException
    {
      List<T>  collection = new ArrayList<>();

      int size = read();
      for(int i = 0 ; i < size ; i++){
        T entry = read();
        collection.add(entry);
      }
      return collection;
    }

    public <T> T read() throws IOException, ClassNotFoundException
    {
      return (T)s.readObject();
    }

    public void close() throws IOException
    {
      s.close();
      fop.close();
    }
  }
}

