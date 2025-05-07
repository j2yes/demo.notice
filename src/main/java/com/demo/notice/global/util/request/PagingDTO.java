package com.demo.notice.global.util.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class PagingDTO {

  @Min(value = 0)
  protected int pageNumber = 0;
  @Range(min = 1)
  protected int rowCount = 20;

  @JsonIgnore
  public Pageable getPageRequest() {
    return PageRequest.of(this.pageNumber, this.rowCount);
  }
}


