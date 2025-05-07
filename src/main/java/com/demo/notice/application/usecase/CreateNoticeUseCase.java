package com.demo.notice.application.usecase;

import com.demo.notice.application.command.CreateNoticeCommand;
import com.demo.notice.domain.model.Notice;

public interface CreateNoticeUseCase {

  Notice create(CreateNoticeCommand command);
}
