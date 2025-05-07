package com.demo.notice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NoticeApplication {

  public static void main(String[] args) {
    SpringApplication.run(NoticeApplication.class, args);
  }

}
