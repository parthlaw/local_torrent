package org.local_torrent.queues;

import java.util.concurrent.LinkedBlockingDeque;

public record Task(
    TaskType type, String message, LinkedBlockingDeque<TaskResponse> responseQueue) {}
