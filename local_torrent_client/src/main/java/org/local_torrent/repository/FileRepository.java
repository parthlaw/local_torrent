
package org.local_torrent.repository;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.List;
import org.local_torrent.models.FileEntity;
import org.local_torrent.store.Store;
public class FileRepository{
  private String rootPath;
  private Store store;
  public FileRepository(String path, Store store) {
    this.store = store;
    this.rootPath = this.store.getBasePath();
  }
  public FileEntity[] listUnderDirectory(String directory){
    List<FileEntity> fileEntities = new ArrayList<>();
    try {
      Path dirPath = Paths.get(addRootPath(directory));
      DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath);

      for (Path entry : stream) {
        if(!Files.isRegularFile(entry) && !Files.isDirectory(entry)) continue;
          // Create a new FileEntity object for each entry and add it to the list
          fileEntities.add(createFileEntity(entry));
      }
      stream.close();
    } catch (Exception e) {
      // Handle the exception (e.g., directory not found, access denied, etc.)
      e.printStackTrace();
    }

    return fileEntities.toArray(new FileEntity[0]);
  }
  private String removeRootPath(String filePath){
    Path root = Paths.get(this.rootPath).normalize();
    Path file = Paths.get(filePath).normalize();
    if(file.startsWith(root)){
      return root.relativize(file).toString();
    }
    return "";
  }
  private String addRootPath(String filePath){
    Path file = Paths.get(filePath).normalize();
    Path root = Paths.get(this.rootPath).normalize();
    Path fullPath = root.resolve(file);
    return fullPath.toString();
  }
  private FileEntity createFileEntity(Path filePath){
    String name = filePath.getFileName().toString();

    // Extracting parent directory
    Path parentPath = filePath.getParent();
    String parent = (parentPath != null) ? parentPath.toString() : null;

    // Getting file size (in bytes)
    long size;
    String fileType = null;
    Boolean isDirectory = false;
    try {
        long fileSize = java.nio.file.Files.size(filePath);
        if(Files.isDirectory(filePath)){
        isDirectory = true;
      }else if(Files.isRegularFile(filePath)){
        isDirectory = false;
        fileType = Files.probeContentType(filePath);
      }else{
        throw new Exception("Invalid File");
      }
        size = fileSize;
    } catch (Exception e) {
        // Handle the exception (e.g., file not found, access denied, etc.)
        e.printStackTrace();
        size = 0;
    }
    return new FileEntity(name, removeRootPath(filePath.toString()), size, removeRootPath(parent),fileType,isDirectory);
  }
  public FileEntity getFile(String path){
    if(path == null || path.isBlank()|| path=="/"){
      path = this.rootPath;
    }else{
      path = this.addRootPath(path);
    }
    return this.createFileEntity(Paths.get(path));
  }
}
