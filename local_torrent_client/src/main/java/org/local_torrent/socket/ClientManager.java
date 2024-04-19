package org.local_torrent.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import org.local_torrent.exceptions.KeyNotExistException;
import org.local_torrent.queues.Task;
import org.local_torrent.queues.TaskQueue;
import org.local_torrent.queues.TaskResponse;
import org.local_torrent.queues.TaskType;
import org.local_torrent.store.Store;

public class ClientManager {
  TaskQueue queue;
  Store store;

  public ClientManager(Store store) {
    TaskQueue taskQueue = new TaskQueue();
    this.queue = taskQueue;
    this.store = store;
    if (store.getServerIp() != null && !store.getServerIp().isEmpty() && store.getServerPort()!=0) {
      LinkedBlockingQueue<Task> netServerQueue = this.queue.addTopic(store.getServerIp());
      try {
        String uuid = this.queue.getNewResponseQueueUUID();
        LinkedBlockingDeque<TaskResponse> queue = this.queue.getResponseQueue(uuid);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        SocketClient serverSocketClient =
            new SocketClient(store.getServerIp(), store.getServerPort(), netServerQueue);
        executorService.submit(() -> serverSocketClient.run(queue));
        executorService.submit(() -> updateConnectionStatus(queue, store));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void updateConnectionStatus(
      LinkedBlockingDeque<TaskResponse> serverResponseQueue, Store store) {
    System.out.println("Starting Update Task");
    while (true) {
      TaskResponse response = serverResponseQueue.pollFirst();
      if(response != null){
        store.setServerConnectionResponse(response);
      }
    }
  }

  public String connectToServer(String ip, int port) {
    String uuid = this.queue.getNewResponseQueueUUID();
    LinkedBlockingDeque<TaskResponse> responseQueue = this.queue.getResponseQueue(uuid);
    String serverIp = store.getServerIp();
    if(ip==serverIp){
      ExecutorService executorService = Executors.newFixedThreadPool(2);
      LinkedBlockingQueue<Task> queue = this.queue.addTopic(ip);
      SocketClient socketClient = new SocketClient(ip, port, queue);
      executorService.submit(() -> socketClient.run(responseQueue));
      executorService.submit(() -> updateConnectionStatus(responseQueue, store));
      return null;
    }
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    LinkedBlockingQueue<Task> queue = this.queue.addTopic(ip);
    SocketClient socketClient = new SocketClient(ip, port, queue);
    executorService.submit(() -> socketClient.run(responseQueue));
    return uuid;
  }

  public void disconnectFromServer(String ip) {
    try {
      String uuid = this.queue.getNewResponseQueueUUID();
      LinkedBlockingDeque<TaskResponse> queue = this.queue.getResponseQueue(uuid);
      this.queue.addTask(new Task(TaskType.DISCONNECT, ip, queue), ip);
    } catch (KeyNotExistException e) {
      e.printStackTrace();
    }
  }

  public void getConnectedClients() {}

  public void getFiles() {
    try {
      String uuid = this.queue.getNewResponseQueueUUID();
      LinkedBlockingDeque<TaskResponse> queue = this.queue.getResponseQueue(uuid);
      this.queue.addTask(new Task(TaskType.GET_FILE, "", queue), "");
    } catch (KeyNotExistException e) {
      e.printStackTrace();
    }
  }

  public TaskResponse getResponseFromResponseQueue(String queueId) {
    LinkedBlockingDeque<TaskResponse> queue = this.queue.getResponseQueue(queueId);
    return queue.pollFirst();
  }
}
