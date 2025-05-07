package com.demo.notice.application.port.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {

  String store(MultipartFile file);

  Resource load(String fullPath);

  void delete(String fullPath);
}
