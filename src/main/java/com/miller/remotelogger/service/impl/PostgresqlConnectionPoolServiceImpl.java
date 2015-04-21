package com.miller.remotelogger.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.miller.remotelogger.service.ConnectionPoolService;

@Component
public class PostgresqlConnectionPoolServiceImpl implements ConnectionPoolService {

	@Value("${logging.database.username}")
	private String username;
	
	@Value("${logging.database.password}")
	private String password;
	
	@Value("${spring.datasource.driverClassName}")
	private String jdbcDriver;
	 
	private Map<String, Connection> databaseConnections = new HashMap<String, Connection>();
	
	@Override
	public Connection createNewConnection(String connectionURL) throws Exception {
		Class.forName(jdbcDriver);
		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);
		DriverManager.setLoginTimeout(2000);
		Connection conn = DriverManager.getConnection(connectionURL, props);
		return conn;
	}

	@Override
	public Connection getConnectionFromList(String connectionURL) throws Exception {
		
		for(int i = 0; i < databaseConnections.size(); i++)
			if(databaseConnections.keySet().toArray(new String[]{})[i].equals(connectionURL))
				return databaseConnections.get(databaseConnections.keySet().toArray(new String[]{})[i]);
		
		Connection newConnection = createNewConnection(connectionURL);
		databaseConnections.put(connectionURL, newConnection);
		return newConnection;
	}

	@Override
	public String generateInsertStatement(String tableName, String[] columns) {
		String columnsStatement = "(";
		String valuesStatement = "(";
		
		for(int i = 0; i < columns.length; i++)
			columnsStatement += columns[i] + (i != columns.length-1 ? ", " : ")") ;
		
		for(int i = 0; i < columns.length; i++)
			valuesStatement += "?" + (i != columns.length-1 ? ", " : ")") ;
	
		String insertStatement = "INSERT INTO " + tableName + " " + columnsStatement + " VALUES " + valuesStatement;
		return insertStatement;
	}

	@Override
	public String generateConnectionURL(String hostName, String databaseName, int databasePort) {
		return "jdbc:postgresql://" + hostName + ":" + databasePort + "/" + databaseName;
	}

	@Override
	public String generateColumnInformationQuery(String tableName) {
		return  "SELECT * FROM " + tableName + " LIMIT 0";
	}

}
