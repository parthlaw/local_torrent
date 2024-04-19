package org.local_torrent.store;

enum PropertyEnum {
  BASEPATH("basePath"),
  SERVERIP("serverIp"),
  SERVERPORT("serverPort"),
  WEBPORT("webPort"),
  NODEPORT("nodePort"),
  SHAREDIR("shareDir"),
  DOWNLOADDIR("downloadDir");

  // Private field to store the associated string value
  private final String value;

  // Constructor to initialize the value for each enum constant
  PropertyEnum(String value) {
    this.value = value;
  }

  // Getter method to retrieve the string value
  public String getValue() {
    return value;
  }
}
