package com.api.webvote.v1.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.v1.controller.ScheduleController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Schedule;

public class CheckTitle {
	
	final static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

	public static void check (Schedule schedule) {
		logger.debug("-> Verificando se o título da pauta é null ou vazio.");
		String title = schedule.getTitle();
		
		if (title == null || title.isEmpty())
			throw new BadRequestException("Título da pauta inválido");
	}

}