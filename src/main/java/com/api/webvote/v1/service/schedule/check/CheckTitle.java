package com.api.webvote.v1.service.schedule.check;

import com.api.webvote.v1.controller.ScheduleController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component

public class CheckTitle implements ScheduleSystemChecker {
    final static Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    @Override
    public void check(Schedule schedule) {
        logger.debug("-> Verificando se o título da pauta é null ou vazio.");

        String title = schedule.getTitle();

        if (title == null || title.isEmpty())
            throw new BadRequestException("Título da pauta inválido");
    }
}
