package org.local_torrent.socket;

import java.io.FileOutputStream;
import java.nio.channels.SocketChannel;

class ReceiveFileTask implements Runnable {
  private SocketChannel socketChannel;
  public ReceiveFileTask(SocketChannel socketChannel){
    this.socketChannel = socketChannel;
  }
  public void run(){
    try{
      FileOutputStream fileOutputStream = new FileOutputStream("file.txt");
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
