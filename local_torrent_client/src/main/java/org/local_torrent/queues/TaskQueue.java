package org.local_torrent.queues;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import org.local_torrent.exceptions.KeyNotExistException;

public class TaskQueue {
  HashMap<String, LinkedBlockingQueue<Task>> topicQueues;
  LinkedBlockingQueue<Task> mainQueue;
  HashMap<String, LinkedBlockingQueue<Task>> responseQueues;

  public TaskQueue() {}

  private void checkKey(HashMap<String, LinkedBlockingQueue<Task>> hashMap, String key)
      throws KeyNotExistException {
    if (!hashMap.containsKey(key)) {
      throw new KeyNotExistException("Key " + key + " not exists");
    }
  }

  public void addTask(Task task, String topic) throws KeyNotExistException {
    try {
      checkKey(this.topicQueues, topic);
      topicQueues.get(topic).put(task);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public Task getTask(String topic) throws KeyNotExistException {
    try {
      checkKey(this.topicQueues, topic);
      return topicQueues.get(topic).take();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addTask(Task task) {
    try {
      mainQueue.put(task);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public Task getTask() {
    try {
      return mainQueue.take();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public LinkedBlockingQueue<Task> addTopic(String topic) {
    this.topicQueues.put(topic, new LinkedBlockingQueue<Task>());
    return this.topicQueues.get(topic);
  }

  public void removeTopic(String topic) throws KeyNotExistException {
    checkKey(this.topicQueues, topic);
    this.topicQueues.remove(topic);
  }

  public UUID getResponseQueueUUID() {
    UUID uuid = UUID.randomUUID();
    responseQueues.put(uuid.toString(), new LinkedBlockingQueue<Task>());
    return uuid;
  }

  public void removeResponseQueue(UUID uuid) throws KeyNotExistException {
    checkKey(this.responseQueues, uuid.toString());
    responseQueues.remove(uuid.toString());
  }
}
