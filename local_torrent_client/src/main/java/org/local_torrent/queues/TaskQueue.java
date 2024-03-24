package org.local_torrent.queues;

import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue{
  LinkedBlockingQueue<Task> queue;
  public TaskQueue(){
    queue = new LinkedBlockingQueue<Task>();
  }
  public void addTask(Task task){
    try{
    queue.put(task);
    }catch(InterruptedException e){
      e.printStackTrace();
    }
  }
  public Task getTask() {
    try {
      return queue.take();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
}
}
