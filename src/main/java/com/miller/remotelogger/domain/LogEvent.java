package com.miller.remotelogger.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Log event object that will hold the:
 * Database ID for database 
 * the table name and data mappings
 * for insertion into the database
 * @author Jonathan Miller
 */
public class LogEvent {
	
	public interface ResponseView {};
	
	private int logId;
	
	@JsonView(ResponseView.class)
	private String databaseName;
	
	@JsonView(ResponseView.class)
	private String tableName;
	
	@JsonView(ResponseView.class)
	private Map<String, ?> values;
	
	/* Default value is the local host */
	private String hostName = "localhost";
	
	/* Default Postgresql port */
	private int databasePort = 5432;
	
	public LogEvent()
	{
		
	}
	
	public LogEvent(String databaseId, String tableName, Map<String, ?> values)
	{
		setDatabaseId(databaseId);
		setTableName(tableName);
		setValues(values);
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, ?> getValues() {
		return values;
	}

	public void setValues(Map<String, ?> values) {
		this.values = values;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseName = databaseId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getDatabasePort() {
		return databasePort;
	}

	public void setDatabasePort(int databasePort) {
		this.databasePort = databasePort;
	}

}
