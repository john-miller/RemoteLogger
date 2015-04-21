package com.miller.remotelogger.service.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miller.remotelogger.domain.LogEvent;
import com.miller.remotelogger.service.ConnectionPoolService;
import com.miller.remotelogger.service.LogService;

@Component
public class LogServiceImpl implements LogService {

	/* Holds the log events until they can be stored into a data store */
	private Queue<LogEvent> logEventQueue = new LinkedList<LogEvent>();
		
	@Autowired
	private ConnectionPoolService connectionPoolService;
	
	public LogServiceImpl()
	{
		/* Initialize the queue handler when the service is instantiated */
		initQueueHandler();
	}
	
	private void initQueueHandler()
	{
		final int timerRefreshRateCeiling = 1000;
		final Timer timer = new Timer(timerRefreshRateCeiling, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(logEventQueue.peek() != null)
					storeLogEvent(logEventQueue.poll());
				
				if(logEventQueue.size() == 0)
					timer.setDelay(timerRefreshRateCeiling);
				else
					timer.setDelay((int) ((double)timerRefreshRateCeiling/(double)logEventQueue.size()));
			}
		});
		timer.start();
	}
				
	/**
	 * Store all log events through this method
	 * @param logEvent
	 */
	private synchronized void storeLogEvent(LogEvent logEvent)
	{
		try 
		{
			System.out.println("Log event hostname: " + logEvent.getHostName());
			String url = connectionPoolService.generateConnectionURL(logEvent.getHostName(), logEvent.getDatabaseName(), logEvent.getDatabasePort());
			Connection conn = connectionPoolService.getConnectionFromList(url);
			String[] columns = logEvent.getValues().keySet().toArray(new String[]{});
			String getColumnsStatement = connectionPoolService.generateColumnInformationQuery(logEvent.getTableName());
			ResultSet columnsResultSet = conn.createStatement().executeQuery(getColumnsStatement);
			
			PreparedStatement preparedStatement = conn.prepareStatement(connectionPoolService.generateInsertStatement(logEvent.getTableName(), columns));
			for(int i = 0 ; i < columns.length; i++)
			{
				int columnType = columnsResultSet.getMetaData().getColumnType(i+1);
				if(columnType == Types.VARCHAR)
					preparedStatement.setString(i+1, logEvent.getValues().get(columns[i]).toString());
				else if(columnType == Types.DATE)
					preparedStatement.setDate(i+1, new Date(Long.parseLong(logEvent.getValues().get(columns[i]).toString())));
				else if(columnType == Types.TIME)
					preparedStatement.setTime(i+1, new Time(Long.parseLong(logEvent.getValues().get(columns[i]).toString())));
				else if(columnType == Types.TIME_WITH_TIMEZONE)
					preparedStatement.setTime(i+1, new Time(Long.parseLong(logEvent.getValues().get(columns[i]).toString())));
				else if(columnType == Types.TIMESTAMP)
					preparedStatement.setTimestamp(i+1, new Timestamp(Long.parseLong(logEvent.getValues().get(columns[i]).toString())));
				else if(columnType == Types.TIMESTAMP_WITH_TIMEZONE)
					preparedStatement.setTimestamp(i+1, new Timestamp(Long.parseLong(logEvent.getValues().get(columns[i]).toString())));
				else
					preparedStatement.setObject(i+1, logEvent.getValues().get(columns[i]));
			}
			
			preparedStatement.execute();
			columnsResultSet.close();
			preparedStatement.closeOnCompletion();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void addLogEventToQueue(LogEvent event) {
		logEventQueue.add(event);
	}

	@Override
	public LogEvent retrieveLogEvent(int logEventId) {
		// TODO Auto-generated method stub
		System.out.println("Retrieving log event");
		return new LogEvent("database001", "table001", new HashMap<String, String>());
	}

	@Override
	public List<LogEvent> retrieveLogEvents(String database, String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLogEvent(int logEventId) {
		// TODO Auto-generated method stub
		
	}

}
