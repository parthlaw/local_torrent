package org.local_torrent.socket;

import org.local_torrent.queues.TaskQueue;

import com.google.gson.Gson;

import org.local_torrent.queues.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ClientManager {
  TaskQueue queue;
  public ClientManager(TaskQueue queue){
    this.queue = queue;
  }
  public void run(){
    while(true){
      Task task = this.queue.getTask();
      if(task==null){
        continue;
      }
      switch (task.type()){
        case GET_FILE:
        break;
        case CONNECT:
          String jsonMessage = task.message();
          Gson gson = new Gson();
          JsonTaskMessages.JsonConnectMessage connectMessage = gson.fromJson(jsonMessage, JsonTaskMessages.JsonConnectMessage.class);
          this.handleConnect(connectMessage.ip(), connectMessage.port());
        break;
        default:
        break;
      }
    }
  }
  private void handleConnect(String ip, int port){
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    SocketClient socketClient = new SocketClient(ip, port);
    executorService.submit(socketClient::run);
  }
}
