package org.local_torrent.queues;
public enum TaskType {
    GET_FILE("GET_FILE");

    private final String text;

    private TaskType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
