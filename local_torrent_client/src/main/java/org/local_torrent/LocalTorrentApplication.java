package org.local_torrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.local_torrent.socket.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

// @SpringBootApplication
// @Controller
// @EnableWebSocket
public class LocalTorrentApplication{

	public static void main(String[] args) {
    SocketServer socketServer = new SocketServer();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(socketServer::run);
		// SpringApplication.run(LocalTorrentApplication.class, args);
	}

  // @GetMapping("/hello")
  // @ResponseBody
  // public String hello(@RequestParam(value="name",defaultValue = "World") String name){
  //   return String.format("Hello %s !", name);
  // }
}
