package org.local_torrent.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.local_torrent.models.SettingsEntity;
import org.local_torrent.queues.TaskResponse;
import org.local_torrent.queues.TaskStatus;
import org.local_torrent.queues.TaskType;

public class Store {
  private Properties config;
  private String propertiesFile = "/home/parth/.config/local_torrent.properties";
  private TaskResponse serverConnectionResponse;

  public Store() {
    this.config = new Properties();
    try {
      this.serverConnectionResponse =
          new TaskResponse(TaskType.CONNECT, TaskStatus.NOT_STARTED, "Set the value for server ip");
      FileInputStream fileInputStream = new FileInputStream(propertiesFile);
      this.config.load(fileInputStream);
    } catch (Exception e) {
      System.out.println("Properties file not found");
      setPropertyValue(PropertyEnum.BASEPATH.getValue(), "/home/local_torrent/");
    }
  }

  private void setPropertyValue(String key, String value) {
    this.config.setProperty(key, value);
    try {
      File file = new File(this.propertiesFile);
      if (file.createNewFile()) {
        System.out.println("File created: " + file.getName());
      } else {
        System.out.println("File already exists");
      }
      FileOutputStream fileOutputStream = new FileOutputStream(this.propertiesFile);
      this.config.store(fileOutputStream, "setting initial properties");
    } catch (IOException ex) {
      System.out.println("error here");
      ex.printStackTrace();
    }
  }

  public void changeBasePath(String path) {
    setPropertyValue(PropertyEnum.BASEPATH.getValue(), path);
  }

  public String getBasePath() {
    return this.config.getProperty(PropertyEnum.BASEPATH.getValue());
  }

  public String getServerIp() {
    return this.config.getProperty(PropertyEnum.SERVERIP.getValue());
  }

  public void setServerIp(String value) {
    this.setPropertyValue(PropertyEnum.SERVERIP.getValue(), value);
  }
  public void setServerConfig(String ip, int port){
    this.setPropertyValue(PropertyEnum.SERVERIP.getValue(), ip);
    this.setPropertyValue(PropertyEnum.SERVERPORT.getValue(), String.valueOf(port));
  }
  public int getServerPort(){
    try{
      int port = Integer.parseInt(this.config.getProperty(PropertyEnum.SERVERPORT.getValue()));
      return port;
    }catch(NumberFormatException e){
      return 0;
    }
  }

  public void setServerConnectionResponse(TaskResponse connectionStatus) {
    this.serverConnectionResponse = connectionStatus;
  }

  public TaskResponse getServerConnectionResponse() {
    return this.serverConnectionResponse;
  }
  public void setNodeSettings(String webPort, String nodePort, String shareDir, String downloadDir){
    this.setPropertyValue(PropertyEnum.WEBPORT.getValue(), webPort);
    this.setPropertyValue(PropertyEnum.NODEPORT.getValue(), nodePort);
    this.setPropertyValue(PropertyEnum.SHAREDIR.getValue(), shareDir);
    this.setPropertyValue(PropertyEnum.DOWNLOADDIR.getValue(), downloadDir);
  }
  public SettingsEntity getNodeSettings(){
    return new SettingsEntity(
      this.config.getProperty(PropertyEnum.WEBPORT.getValue()),
      this.config.getProperty(PropertyEnum.NODEPORT.getValue()),
      this.config.getProperty(PropertyEnum.SHAREDIR.getValue()),
      this.config.getProperty(PropertyEnum.DOWNLOADDIR.getValue())
    );
  }
}
