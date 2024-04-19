package org.local_torrent.controllers;

import org.local_torrent.store.Store;
import org.local_torrent.models.SettingsEntity;
import org.local_torrent.models.response.BaseResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
public class NodeSettingsController {
  @Autowired private Store store;

  @CrossOrigin(origins = "*")
  @PostMapping("")
  public BaseResponseEntity<SettingsEntity> setSettings(@RequestBody SettingsEntity data){
    store.setNodeSettings(data.getWebPort(), data.getNodePort(), data.getShareDir(), data.getDownloadDir());
    return BaseResponseEntity.getResponseEntity(true, "Settings saved", data);
  }

  @CrossOrigin(origins = "*")
  @GetMapping("")
  public BaseResponseEntity<SettingsEntity> getSettings(){
    return BaseResponseEntity.getResponseEntity(true, "Settings fetched", store.getNodeSettings());
  }
}
