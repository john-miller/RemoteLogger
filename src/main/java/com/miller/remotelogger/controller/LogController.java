package com.miller.remotelogger.controller;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miller.remotelogger.domain.LogEvent;
import com.miller.remotelogger.exceptions.LogEventNotFoundException;
import com.miller.remotelogger.exceptions.TableNotFoundException;

@RestController
@RequestMapping(value="v1/log")
public interface LogController {

	@PreAuthorize("hasRole('READER') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{logId}", method = RequestMethod.GET)
	public HttpEntity<LogEvent> getLogEventById(@PathVariable("logId") int logId) throws LogEventNotFoundException;

	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{logId}", method = RequestMethod.DELETE)
	public HttpEntity<LogEvent> deleteLogEventById(@PathVariable int logId) throws LogEventNotFoundException;

	@PreAuthorize("hasRole('EDITOR') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/", method = RequestMethod.POST)
	public HttpEntity<LogEvent> addLogEvent(@RequestBody LogEvent logEvent) throws TableNotFoundException, LogEventNotFoundException;
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({LogEventNotFoundException.class})
	public ResponseEntity<VndErrors> LogEventNotFoundExceptionHandler(LogEventNotFoundException ex);
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({TableNotFoundException.class})
	public ResponseEntity<VndErrors> TableNotFoundExceptionHandler(TableNotFoundException ex);
}