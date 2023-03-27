package com.api.webvote.v1.service.check;

import com.api.webvote.v1.controller.ScheduleController;
import com.api.webvote.v1.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckTitle {
	
	final static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

	public static void check (String title) {
		logger.debug("-> Verificando se o título da pauta é null ou vazio.");

		if (title == null || title.isEmpty())
			throw new BadRequestException("Título da pauta inválido");
	}

}