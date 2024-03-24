package org.local_torrent;
import org.local_torrent.queues.TaskQueue;
import org.local_torrent.socket.SocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketConfiguration{
  @Bean
  public SocketServer socketServer(TaskQueue taskQueue){
    return new SocketServer(taskQueue);
  }
}
