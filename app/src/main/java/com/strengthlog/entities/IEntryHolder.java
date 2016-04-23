package com.strengthlog.entities;

import com.google.gson.Gson;

/**
 * Created by agodlin on 4/9/2016.
 */
public class IEntryHolder implements java.io.Serializable
{
  public String toJson(){
    Gson gson = new Gson();
    String json = gson.toJson(this);
    return json;
  }
}
