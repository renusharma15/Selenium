package com.myaccess.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnector {

	private String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private String baseUrl = "jdbc:oracle:thin:@";
	private Connection connection = null;

	public Connection connectViaJDBC(String host, String port, String serviceName, String userName, String password) throws Exception {
		try {
			Class.forName(JDBC_DRIVER);
			String url = baseUrl + host + ":" + port + "/" + serviceName;
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("Database connection success.");
			return connection;
		} catch (Exception e) {
			ExecutionLog.logExceptionMessage(e);
			System.out.println(e);
			throw e;
		}
	}

	public ResultSet executeQuery(String query) throws Exception {
		try {
			//Below statement used to move the result set cursor to any position.
			//Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			Statement st = connection.createStatement();
			ResultSet result = st.executeQuery(query);
			//System.out.println("executed Query");
			return result;
		} catch (Exception e) {
			ExecutionLog.logExceptionMessage(e);
			throw e;
		}
	}

	public void closeConnection() throws Exception {
		connection.close();
		System.out.println("Database Connection closed.");
	}
}
