package org.local_torrent.socket;

import java.nio.channels.SelectionKey;

class SendFileTask implements Runnable {
  // private FileService fileService;
  private SelectionKey key;

  public SendFileTask(SelectionKey key) {
    // fileService = new FileService();
    this.key = key;
  }

  public void run() {
    try {
      StringBuilder responseBuffer = (StringBuilder) key.attachment();
      responseBuffer.append("ACK");
      System.out.println("Added response buffer, switching key");
      // key.interestOps(SelectionKey.OP_WRITE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
