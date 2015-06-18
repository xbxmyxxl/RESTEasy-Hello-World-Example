package com.xxu.database;

import java.sql.*;

import org.apache.log4j.Logger;

/**
 * @author xxu
 *
 *         the jdbc driver for all databsse operation, open , delete, select,
 *         run teh script
 */
public class PostgreSql {
	final static Logger logger = Logger.getLogger(PostgreSql.class);

	/**
	 * statement and connection for a database
	 */
	private static Statement stmt = null;
	private static Connection c = null;

	/**
	 * define the constant for the status of the database status is checked
	 * before any query is executed
	 */
	private static final int OPEN_SUCCESS = 0;
	private static final int OPEN_FAILED = 1;
	private static int status = OPEN_FAILED;

	/**
	 * open the database, need to set up the server beforehand. If this step
	 * failed, status will be set to OPEN_FAILED, otherwise OPEN_SUCCESS
	 */
	public static void openDatabase() {
		try {
			Class.forName("org.postgresql.Driver");

			/* change the name and password here */
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/testdata", "postgres",
					" ");

			logger.info("Opened database successfully");
			c.setAutoCommit(false);
			status = OPEN_SUCCESS;

		} catch (Exception e) {
			status = OPEN_FAILED;
			logger.warn(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * close the database if it has been successfully open
	 */
	public static void closeDatabase() {
		try {
			if (status == OPEN_SUCCESS) {
				if (stmt != null)
					stmt.close();
				if (c != null)
					c.close();
				logger.info("Closed database successfully");
			} else {
				logger.warn("Can not close: previous operation failed.");
			}
		} catch (Exception e) {
			status = OPEN_FAILED;
			logger.warn(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * @param sql
	 *            : command
	 * 
	 *            execute the command, sql passed to the function
	 */
	public static void executeDatabaseStmt(String sql) {
		try {
			if (status != OPEN_SUCCESS)
				openDatabase();
			if (status == OPEN_SUCCESS) {
				if (stmt == null)
					stmt = c.createStatement();
				stmt.executeUpdate(sql);
				c.commit();
			} else {
				logger.warn("Can not execute command\"" + sql
						+ "\": previosu operation failed.");
			}
		} catch (Exception e) {
			logger.warn(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * @param query
	 * @return result set
	 * 
	 *         query the database for sth, usually by primary key, return as a result set, need to call
	 *         next() to further handling with the return data
	 */
	public static ResultSet selectDatabase(String query) {
		try {
			if (status != OPEN_SUCCESS)
				openDatabase();
			if (status == OPEN_SUCCESS) {
				if (stmt == null)
					stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				return rs;

			} else {
				logger.warn("Can not select: previosu operation failed.");
				return null;
			}
		} catch (Exception e) {
			status = OPEN_FAILED;
			logger.warn(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return null;
		}
	}
}
