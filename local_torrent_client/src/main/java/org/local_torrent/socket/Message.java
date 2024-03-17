package org.local_torrent.socket;

import java.io.Serializable;

record Message(MessageType type, String message) implements Serializable {
      private static final long serialVersionUID = 1L;
  }
