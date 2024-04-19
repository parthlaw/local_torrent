package org.local_torrent.controllers.entities;

public record ResponseEntity<T>(boolean status, String message, T data) {
  public static <T> ResponseEntity<T> getResponse(boolean status, String message, T data) {
    return new ResponseEntity<T>(status, message, data);
  }
}
;
