package org.local_torrent.controllers;

import org.local_torrent.socket.ClientManager;
import org.local_torrent.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
class ConfigurationController {

  @Autowired private Store store;
  @Autowired ClientManager clientManager;

  @PostMapping("/config")
  public Boolean setConfig(@RequestBody String data) {
    String initialIp = store.getServerIp();
    store.setServerIp(data);
    if (!initialIp.isEmpty()) {
      this.clientManager.disconnectFromServer(initialIp);
      ;
    }
    this.clientManager.connectToServer(data, 8000);
    return true;
  }

  @GetMapping("/config")
  public String getConfig() {
    return store.getServerIp();
  }
}
