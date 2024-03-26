package org.local_torrent.socket;
public class JsonTaskMessages{
  public static record JsonConnectMessage(String ip, int port){}
}
