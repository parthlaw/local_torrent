package org.local_torrent.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Store {
  private Properties config;
  private String propertiesFile = "/home/parth/.config/local_torrent.properties";

  public Store() {
    this.config = new Properties();
    try {
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
}
