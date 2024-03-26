package org.local_torrent.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.local_torrent.repository.FileRepository;
import org.local_torrent.store.Store;

public class FileService {
  FileRepository fileRepository;
  String path;
  Store store;

  public FileService() {
    this.store = new Store();
    this.fileRepository = new FileRepository(path, store);
  }

  public void receiveFile(ObjectInputStream objectInputStream, String path) {
    try (FileOutputStream fos = new FileOutputStream(path)) {
      byte[] buffer = new byte[1024];
      int byteRead;

      SecretKey secretKey = new SecretKeySpec("Key".getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);

      CipherInputStream cipherInputStream = new CipherInputStream(objectInputStream, cipher);
      GZIPInputStream gzipInputStream = new GZIPInputStream(cipherInputStream);

      while ((byteRead = gzipInputStream.read(buffer)) != -1) {
        fos.write(buffer, 0, byteRead);
      }

      gzipInputStream.close();
      System.out.println("File received");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendFile(ObjectOutputStream objectOutputStream, String filePath) {
    try (FileInputStream fis = new FileInputStream(filePath)) {
      byte[] buffer = new byte[1024];
      int bytesRead;

      SecretKey secretKey = new SecretKeySpec("Key".getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);

      CipherOutputStream cipherOutputStream = new CipherOutputStream(objectOutputStream, cipher);
      GZIPOutputStream gzipOutputStream = new GZIPOutputStream(cipherOutputStream);

      while ((bytesRead = fis.read(buffer)) != -1) {
        gzipOutputStream.write(buffer, 0, bytesRead);
      }

      gzipOutputStream.close();

      System.out.println("File sent: " + filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  // public void listFiles
}
