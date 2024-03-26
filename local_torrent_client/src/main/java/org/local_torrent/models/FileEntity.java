package org.local_torrent.models;

public record FileEntity(
    String name, String path, long size, String parent, String fileType, Boolean isDirectory) {}
