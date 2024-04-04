package org.local_torrent.queues;

import java.util.concurrent.LinkedBlockingQueue;

public record Task(TaskType type, String message, LinkedBlockingQueue<TaskResponse> responseQueue) {}
