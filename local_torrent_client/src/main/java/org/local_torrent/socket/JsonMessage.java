package org.local_torrent.socket;
public class JsonMessage{
  public String type;
  public String message;
  public JsonMessage(String type, String message){
    this.message = message;
    this.type = type;
  }
}
