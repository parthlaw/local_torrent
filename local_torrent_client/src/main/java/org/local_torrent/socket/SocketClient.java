package org.local_torrent.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import org.local_torrent.queues.Task;

public class SocketClient {
  String ip;
  int port;
  LinkedBlockingQueue<Task> requestQueue;

  public SocketClient(String ip, int port, LinkedBlockingQueue<Task> queue) {
    this.ip = ip;
    this.port = port;
    this.requestQueue = queue;
  }

  public void run() {
    try {
      InetSocketAddress hostAddress = new InetSocketAddress(this.ip, this.port);
      SocketChannel client = SocketChannel.open();
      client.configureBlocking(false);
      client.connect(hostAddress);
      Selector selector = Selector.open();
      client.register(selector, SelectionKey.OP_CONNECT);
      System.out.println("Client... started");
      while (true) {
        int readyChannels = selector.select();
        if (readyChannels == 0) {
          continue;
        }

        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        while (keyIterator.hasNext()) {
          SelectionKey key = keyIterator.next();
          keyIterator.remove();
          if (key.isConnectable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();

            if (socketChannel.isConnectionPending()) {
              socketChannel.finishConnect();
            }
            socketChannel.register(selector, SelectionKey.OP_READ);
            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
          }
          if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            int BUFFER_SIZE = 1024;
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            try {
              int bytesRead = socketChannel.read(buffer);
              if (bytesRead == -1) {
                System.out.println("Connection close");
                // Connection closed by client
                key.cancel();
                socketChannel.close();
                continue;
              }
              buffer.flip();
              byte[] receivedBytes = new byte[buffer.remaining()];
              buffer.get(receivedBytes);
              System.out.println(new String(receivedBytes, StandardCharsets.UTF_8));
              System.out.println("Received Message");
              System.out.println(buffer);

            } catch (Exception e) {
              e.printStackTrace();
              continue;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  // public SocketClient()
}
