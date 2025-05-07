package com.demo.notice.global.util.uuid;

import java.util.UUID;

public class UuidUtil {

  private UuidUtil() {
  }

  public static String generateUuid() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
