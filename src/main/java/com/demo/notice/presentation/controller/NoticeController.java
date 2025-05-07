package com.demo.notice.presentation.controller;

import com.demo.notice.application.command.CreateNoticeCommand;
import com.demo.notice.application.command.UpdateNoticeCommand;
import com.demo.notice.application.query.SearchNoticeQuery;
import com.demo.notice.application.usecase.CreateNoticeUseCase;
import com.demo.notice.application.usecase.DeleteNoticeUseCase;
import com.demo.notice.application.usecase.FindNoticeUseCase;
import com.demo.notice.application.usecase.UpdateNoticeUseCase;
import com.demo.notice.domain.model.Notice;
import com.demo.notice.global.util.response.Response;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

  private final CreateNoticeUseCase createNoticeUseCase;
  private final UpdateNoticeUseCase updateNoticeUseCase;
  private final FindNoticeUseCase findNoticeUseCase;
  private final DeleteNoticeUseCase deleteNoticeUseCase;

  @GetMapping(value = "")
  public Response<Page<Notice>> getList(@Valid SearchNoticeQuery query) {
    Page<Notice> page = findNoticeUseCase.list(query);
    return Response.success(page);
  }

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Response<Notice> create(@Valid @ModelAttribute CreateNoticeCommand dto) {
    return Response.success(createNoticeUseCase.create(dto));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Response<Notice> update(@PathVariable UUID id,
      @Valid @ModelAttribute UpdateNoticeCommand dto) {
    return Response.success(updateNoticeUseCase.update(id, dto));
  }

  @GetMapping(value = "/{id}")
  public Response<Notice> getDetail(@PathVariable UUID id) {
    return Response.success(findNoticeUseCase.detail(id));
  }

  @DeleteMapping(value = "/{id}")
  public Response<Void> delete(@PathVariable UUID id) {
    deleteNoticeUseCase.delete(id);
    return Response.success();
  }
}
