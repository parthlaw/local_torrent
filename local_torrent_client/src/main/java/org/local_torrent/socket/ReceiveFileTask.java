package org.local_torrent.socket;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

class ReceiveFileTask implements Runnable {
  private SocketChannel socketChannel;
  private static final int BUFFER_SIZE = 1024;
  public ReceiveFileTask(SocketChannel socketChannel){
    this.socketChannel = socketChannel;
  }
  public void run(){
    try{
      FileOutputStream fileOutputStream = new FileOutputStream("file.txt");
      ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
      while(socketChannel.read(buffer)>0){
        buffer.flip();
        fileOutputStream.getChannel().write(buffer);
        buffer.clear();
      }
      fileOutputStream.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
