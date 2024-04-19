package org.local_torrent.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigEntity {
    private String ip;
    private int port;
    public static enum ConnectionStatus{
    CONNECTED,
    NOT_CONNECTED
    }
    private ConnectionStatus connectionStatus;
    @JsonCreator
    public ConfigEntity(@JsonProperty("ip") String ip, @JsonProperty("port") int port) {
        this.ip = ip;
        this.port = port;
    }
    public ConfigEntity(String ip, int port, ConnectionStatus connectionStatus) {
        this.ip = ip;
        this.port = port;
        this.connectionStatus = connectionStatus;
    }

    public static ConfigEntity getConfigEntity(String ip, int port) {
        return new ConfigEntity(ip, port);
    }

    public String getIp() {
        return ip;
    }
    public int getPort(){
      return port;
    }
    public ConnectionStatus getConnectionStatus(){
    return this.connectionStatus;
  }
}
