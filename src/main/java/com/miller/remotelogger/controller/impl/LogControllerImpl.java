package com.miller.remotelogger.controller.impl;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.miller.remotelogger.controller.LogController;
import com.miller.remotelogger.domain.LogEvent;
import com.miller.remotelogger.exceptions.LogEventNotFoundException;
import com.miller.remotelogger.exceptions.TableNotFoundException;
import com.miller.remotelogger.service.LogService;

@RestController
public class LogControllerImpl implements LogController {

	@Autowired
	private LogService logService;
	
	@Override
	public HttpEntity<LogEvent> getLogEventById(int logId) throws LogEventNotFoundException
	{
		LogEvent logEvent = logService.retrieveLogEvent(logId);
		logEvent.add(linkTo(methodOn(LogController.class).getLogEventById(logId)).withSelfRel());
		return new ResponseEntity<LogEvent>(logEvent, HttpStatus.OK);
	}
	
	@Override
	public HttpEntity<LogEvent> deleteLogEventById(int logId) throws LogEventNotFoundException
	{		
		LogEvent logEvent = new LogEvent();
		logEvent.add(linkTo(methodOn(LogController.class).getLogEventById(logId)).withSelfRel());
		return new ResponseEntity<LogEvent>(logEvent, HttpStatus.OK);
	}
	
	@Override
	public HttpEntity<LogEvent> addLogEvent(LogEvent logEvent) throws TableNotFoundException, LogEventNotFoundException
    {
		logService.addLogEventToQueue(logEvent);
		return new ResponseEntity<LogEvent>(logEvent, HttpStatus.OK);
    }
	
	/**
	 * Handles LogEventNotFoundExceptions when thrown
	 */
	@Override
	public ResponseEntity<VndErrors> LogEventNotFoundExceptionHandler(LogEventNotFoundException ex)
	{
		MediaType vndErrorMediaType = MediaType.parseMediaType("application/vnd.error+json");
		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(vndErrorMediaType);
		return new ResponseEntity<VndErrors>(new VndErrors("LogEventNotFoundException", 
				ex.getMessage()), httpHeaders, HttpStatus.NOT_FOUND);
	}
	
	@Override
	public ResponseEntity<VndErrors> TableNotFoundExceptionHandler(TableNotFoundException ex)
	{
		MediaType vndErrorMediaType = MediaType.parseMediaType("application/vnd.error+json");
		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(vndErrorMediaType);
		return new ResponseEntity<VndErrors>(new VndErrors("LogEventNotFoundException", 
				ex.getMessage()), httpHeaders, HttpStatus.NOT_FOUND);
	}
	
}
