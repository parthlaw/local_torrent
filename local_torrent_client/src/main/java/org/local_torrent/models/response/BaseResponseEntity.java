package org.local_torrent.models.response;

public record BaseResponseEntity<T>(boolean status, String message, String responseId, T data) {
  public static <T> BaseResponseEntity<T> getResponseEntity(
      boolean status, String message, T data) {
    return new BaseResponseEntity<T>(status, message, "", data);
  }

  public static <T> BaseResponseEntity<T> getResponseEntity(
      boolean status, String message, String responseId) {
    return new BaseResponseEntity<T>(status, message, responseId, null);
  }
}
