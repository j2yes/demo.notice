package com.demo.notice.infrastructure.adapter.file;

import com.demo.notice.application.port.file.FileStoragePort;
import com.demo.notice.global.exception.FileException;
import static com.demo.notice.global.exception.FileException.FileExceptionExceptionCode.FILE_DELETE_FAILED;
import static com.demo.notice.global.exception.FileException.FileExceptionExceptionCode.FILE_LOAD_FAILED;
import static com.demo.notice.global.exception.FileException.FileExceptionExceptionCode.FILE_STORE_FAILED;
import com.demo.notice.global.util.uuid.UuidUtil;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class LocalFileStorageAdapter implements FileStoragePort {

//  @Value("${file.storage.path}")
//  private String rootPath;
  private final Path root = Paths.get(System.getProperty("user.dir")).resolve("uploads");

  @PostConstruct
  public void init() throws IOException {
    Files.createDirectories(root); // uploads 폴더 없으면 생성
  }

  @Override
  public String store(MultipartFile file) {
    try {
      String fileUniqueName = UuidUtil.generateUuid();
      Path filePath = Paths.get(root.toString(), File.separator, fileUniqueName);
      file.transferTo(filePath);
      return filePath.toString();
    } catch (IOException e) {
      throw new FileException(FILE_STORE_FAILED);
    }
  }

  @Override
  public Resource load(String fullPath) {
    try {
      Path filePath = Paths.get(fullPath);
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
    } catch (MalformedURLException e) {
      throw new FileException(FILE_LOAD_FAILED);
    }
    return null;
  }

  @Override
  public void delete(String fullPath) {
    Path filePath = Paths.get(fullPath);
    try {
      if (Files.exists(filePath)) {
        Files.delete(filePath);
      }
    } catch (IOException e) {
      throw new FileException(FILE_DELETE_FAILED);
    }
  }
}