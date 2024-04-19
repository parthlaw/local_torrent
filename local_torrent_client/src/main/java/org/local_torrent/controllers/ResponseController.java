package org.local_torrent.controllers;

import org.local_torrent.models.response.BaseResponseEntity;
import org.local_torrent.queues.TaskResponse;
import org.local_torrent.socket.ClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
class ResponseController {

  @Autowired private ClientManager clientManager;

  @GetMapping("/{id}")
  public BaseResponseEntity<TaskResponse> getResponseById(@PathVariable String id) {
    return BaseResponseEntity.getResponseEntity(
        true, "Response fetched", clientManager.getResponseFromResponseQueue(id));
  }
}
