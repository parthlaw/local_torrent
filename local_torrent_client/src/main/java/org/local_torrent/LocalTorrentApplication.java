package org.local_torrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.local_torrent.socket.ClientManager;
import org.local_torrent.socket.SocketServer;
import org.local_torrent.store.Store;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@Controller
@EnableWebSocket
public class LocalTorrentApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        SpringApplication.run(LocalTorrentApplication.class, args);
    SocketServer socketServer = context.getBean(SocketServer.class);
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(socketServer::run);
  }

  @GetMapping("/hello")
  @ResponseBody
  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello %s !", name);
  }

  @Bean
  public Store store() {
    return new Store();
  }

  @Bean
  public ClientManager clientManager(Store store) {
    return new ClientManager(store);
  }
}
