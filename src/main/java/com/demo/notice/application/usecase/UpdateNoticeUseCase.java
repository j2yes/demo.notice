package com.demo.notice.application.usecase;

import com.demo.notice.application.command.UpdateNoticeCommand;
import com.demo.notice.domain.model.Notice;
import java.util.UUID;

public interface UpdateNoticeUseCase {

  Notice update(UUID id, UpdateNoticeCommand command);
}
