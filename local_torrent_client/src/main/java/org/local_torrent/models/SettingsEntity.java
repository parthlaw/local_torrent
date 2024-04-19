package org.local_torrent.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingsEntity {
  private String webPort;
  private String nodePort;
  private String shareDir;
  private String downloadDir;
  @JsonCreator
  public SettingsEntity(@JsonProperty("webPort") String webPort, @JsonProperty("nodePort") String nodePort, @JsonProperty("shareDir") String shareDir, @JsonProperty("downloadDir") String downloadDir){
    this.webPort = webPort;
    this.nodePort = nodePort;
    this.shareDir = shareDir;
    this.downloadDir = downloadDir;
  }
  public String getWebPort(){
    return this.webPort;
  }
  public String getNodePort(){
    return this.nodePort;
  }
  public String getShareDir(){
    return this.shareDir;
  }
  public String getDownloadDir(){
    return this.downloadDir;
  }
}
