package org.local_torrent.exceptions;

public class KeyNotExistException extends Exception {
  public KeyNotExistException() {
    super();
  }

  public KeyNotExistException(String message) {
    super(message);
  }

  public KeyNotExistException(String message, Throwable cause) {
    super(message, cause);
  }

  public KeyNotExistException(Throwable cause) {
    super(cause);
  }
}
