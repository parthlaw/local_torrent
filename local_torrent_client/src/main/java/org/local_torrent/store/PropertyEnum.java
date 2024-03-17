package org.local_torrent.store;

enum PropertyEnum {
  BASEPATH("basePath");

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
