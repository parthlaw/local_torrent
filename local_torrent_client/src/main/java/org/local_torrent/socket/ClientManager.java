package org.local_torrent.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import org.local_torrent.exceptions.KeyNotExistException;
import org.local_torrent.queues.Task;
import org.local_torrent.queues.TaskQueue;
import org.local_torrent.queues.TaskType;
import org.local_torrent.store.Store;

public class ClientManager {
  TaskQueue queue;
  Store store;

  public ClientManager(Store store) {
    TaskQueue taskQueue = new TaskQueue();
    this.queue = taskQueue;
    this.store = store;
    LinkedBlockingQueue<Task> netServerQueue = this.queue.addTopic("server");
    if (!store.getServerIp().isEmpty()) {
      try {
        netServerQueue.put(new Task(TaskType.CONNECT, store.getServerIp()));
      } catch (InterruptedException e) {
        store.setConnectionStatus(false);
        e.printStackTrace();
      }
    }
  }

  public void connectToServer(String ip, int port) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    LinkedBlockingQueue<Task> queue = this.queue.addTopic(ip);
    SocketClient socketClient = new SocketClient(ip, port, queue);
    executorService.submit(socketClient::run);
  }

  public void disconnectFromServer(String ip) {
    try {
      this.queue.addTask(new Task(TaskType.DISCONNECT, ip), ip);
    } catch (KeyNotExistException e) {
      e.printStackTrace();
    }
  }

  public void getConnectedClients() {}

  public void getFiles() {}
}
