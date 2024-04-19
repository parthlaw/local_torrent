package org.local_torrent.queues;

public record TaskResponse(TaskType type, TaskStatus status, String message) {}
;
