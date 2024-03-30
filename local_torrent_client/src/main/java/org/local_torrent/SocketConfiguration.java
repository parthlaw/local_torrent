package org.local_torrent;

import org.local_torrent.socket.SocketServer;
import org.local_torrent.store.Store;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketConfiguration {
  @Bean
  public SocketServer socketServer(Store store) {
    return new SocketServer();
  }
}
