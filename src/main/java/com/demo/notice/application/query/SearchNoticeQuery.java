package com.demo.notice.application.query;

import com.demo.notice.global.util.request.PagingDTO;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class SearchNoticeQuery extends PagingDTO {

  private String title;
  private String content;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate fromCreatedDate;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate toCreatedDate;
}
