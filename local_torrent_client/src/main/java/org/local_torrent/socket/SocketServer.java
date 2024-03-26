package org.local_torrent.socket;

import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.local_torrent.queues.TaskQueue;

public class SocketServer {
  ExecutorService pool;
  TaskQueue taskQueue;
  protected static final int MAX_T = 5;

  public SocketServer(TaskQueue taskQueue) {
    this.pool = Executors.newFixedThreadPool(MAX_T);
    this.taskQueue = taskQueue;
  }

  public void run() {
    try {
      Selector selector = Selector.open();
      ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.configureBlocking(false);
      InetSocketAddress inetSocketAddress = new InetSocketAddress(8000);
      serverSocketChannel.bind(inetSocketAddress);
      System.out.println("Socket Server started on port 8000");
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
      while (true) {
        int readyCount = selector.select();
        if (readyCount == 0) {
          continue;
        }
        Set<SelectionKey> readyKeysSet = selector.selectedKeys();
        Iterator iterator = readyKeysSet.iterator();
        while (iterator.hasNext()) {
          SelectionKey key = (SelectionKey) iterator.next();
          iterator.remove();
          if (key.isAcceptable()) {
            System.out.println("Accepting connection");
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            SelectionKey clientKey =
                client.register(selector, SelectionKey.OP_READ); // Register for both read and write
            clientKey.attach(new StringBuilder());
            continue;
          }
          if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            int BUFFER_SIZE = 1024;
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            try {
              int bytesRead = client.read(buffer);
              if (bytesRead == -1) {
                System.out.println("Connection close");
                // Connection closed by client
                key.cancel();
                client.close();
                continue;
              }
              buffer.flip();
              byte[] receivedBytes = new byte[buffer.remaining()];
              buffer.get(receivedBytes);
              System.out.println(receivedBytes.length);
              key.interestOpsOr(SelectionKey.OP_WRITE);
              readMessageHandler(receivedBytes, key);
              System.out.println("Received Message");
              System.out.println(buffer);
            } catch (SocketException e) {
              e.printStackTrace();
              key.cancel();
              client.close();
              continue;
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            StringBuilder responseBuffer = (StringBuilder) key.attachment();
            String response = responseBuffer.toString();
            if (response.length() > 0) {
              ByteBuffer responseByteBuffer = ByteBuffer.wrap(response.getBytes());
              System.out.println("Trigger");
              System.out.println(response);
              client.write(responseByteBuffer);
              responseBuffer.setLength(0);
            }
            // handle writes
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void readMessageHandler(byte[] serializedBytes, SelectionKey key) {
    try {
      System.out.println("HERE");
      System.out.println(serializedBytes);
      JsonMessage message = deserializeMessage(serializedBytes);
      System.out.println("Message GET");
      System.out.println(message.message);
      switch (message.type) {
        case "send_file":
          Runnable sendFileTask = new SendFileTask(key);
          this.pool.execute(sendFileTask);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public JsonMessage deserializeMessage(byte[] serializedBytes)
      throws UnsupportedEncodingException {
    String jsonString = new String(serializedBytes, StandardCharsets.UTF_8);
    System.out.println("JSON STRING: " + jsonString.length());
    System.out.print(jsonString);
    Gson gson = new Gson();
    JsonMessage message = gson.fromJson(jsonString, JsonMessage.class);
    return message;
  }
}
