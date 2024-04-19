package org.local_torrent.controllers;

import org.local_torrent.models.ConfigEntity;
import org.local_torrent.models.ConfigEntity.ConnectionStatus;
import org.local_torrent.models.response.BaseResponseEntity;
import org.local_torrent.queues.TaskResponse;
import org.local_torrent.queues.TaskStatus;
import org.local_torrent.socket.ClientManager;
import org.local_torrent.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

  @CrossOrigin(origins = "*")
  @PostMapping("")
  public BaseResponseEntity<ConfigEntity> setConfig(@RequestBody ConfigEntity data) {
    System.out.println("Address " + data.getIp()+":"+data.getPort());
    String initialIp = store.getServerIp();
    store.setServerConfig(data.getIp(), data.getPort());
    if (initialIp!=null && !initialIp.isEmpty()) {
      this.clientManager.disconnectFromServer(initialIp);
    }
    String responseId = this.clientManager.connectToServer(data.getIp(), data.getPort());
    return BaseResponseEntity.getResponseEntity(true, "Connection Request Initiated", responseId);
  }

  @CrossOrigin(origins = "*")
  @GetMapping("")
  public BaseResponseEntity<ConfigEntity> getConfig() {
    TaskResponse sTaskResponse = store.getServerConnectionResponse();
    ConnectionStatus connectionStatus = sTaskResponse.status()== TaskStatus.SUCCESS?ConnectionStatus.CONNECTED:ConnectionStatus.NOT_CONNECTED;
    ConfigEntity configEntity = new ConfigEntity(store.getServerIp(),store.getServerPort(),connectionStatus);
    return BaseResponseEntity.getResponseEntity(true, "Connection Status fetched", configEntity);
  }
}
