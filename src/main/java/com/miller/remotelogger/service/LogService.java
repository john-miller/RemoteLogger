package com.miller.remotelogger.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.miller.remotelogger.domain.LogEvent;

@Component
public interface LogService {
	
	public void addLogEventToQueue(LogEvent event);
	
	public LogEvent retrieveLogEvent(int logEventId);
	
	public List<LogEvent> retrieveLogEvents(String database, String tableName);
	
	public void deleteLogEvent(int logEventId);

}
