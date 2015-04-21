package com.miller.remotelogger.service;

import java.sql.Connection;

import org.springframework.stereotype.Component;

@Component
public interface ConnectionPoolService {

	public Connection createNewConnection(String connectionURL) throws Exception;
	
	public Connection getConnectionFromList(String connectionURL) throws Exception;
	
	public String generateInsertStatement(String tableName, String[] columns);
	
	public String generateConnectionURL(String hostName, String databaseName, int databasePort);
	
	public String generateColumnInformationQuery(String tableName);
	
}
