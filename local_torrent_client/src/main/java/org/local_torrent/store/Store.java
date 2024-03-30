package org.local_torrent.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Store {
  private Properties config;
  private String propertiesFile = "/home/parth/.config/local_torrent.properties";
  private Boolean connectionStatus;

  public Store() {
    this.config = new Properties();
    try {
      FileInputStream fileInputStream = new FileInputStream(propertiesFile);
      this.config.load(fileInputStream);
      this.connectionStatus = false;
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

  public void setConnectionStatus(Boolean status) {
    this.connectionStatus = status;
  }

  public Boolean getConnectionStatus() {
    return this.connectionStatus;
  }
}
