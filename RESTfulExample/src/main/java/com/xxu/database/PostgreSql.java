package com.xxu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Statement;
public class PostgreSql implements Database {
	
	private static Statement stmt = null;
	private static Connection c = null;
	
	private static final int OPEN_SUCCESS = 0;
	private static final int OPEN_FAILED = 1;
	private static int status = OPEN_FAILED ;
	//private static String sql_delete = "DELETE from " + db_table_name + " where ID=1;";
	//private static String sql_update = "UPDATE " + db_table_name + " set ACCOUNT_BALANCE = 27000.00 where ID=1;";
	
	public static void main(String args[]) {
	      openDatabase();
	      
	     //executeDatabaseStmt(PostgreSql.sql_insert);
	      //db_exe_stmt();
	     
	      //db_select();
	      closeDatabase();
	   }
	
	public static void openDatabase( )
    {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/testdata",
							"postgres", " ");
			System.out.println("Opened database successfully");
			c.setAutoCommit(false);
			status = OPEN_SUCCESS;

		} catch ( Exception e ) {
			status = OPEN_FAILED;
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
    }
	
	public static void closeDatabase()
    {
		try {
			if( status == OPEN_SUCCESS )
			{
				if ( stmt != null )
					stmt.close();
				if( c!= null )
					c.close();
				System.out.println("Closed database successfully");
			}
			else {
				System.out.println("Can not close: previous operation failed.");
			}
		} catch ( Exception e ) {
	    	status = OPEN_FAILED;
	        System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	        System.exit(0);
	    }
    }
	
	public static void executeDatabaseStmt(String sql)
	{	
		try {
			if( status != OPEN_SUCCESS )
				openDatabase();
			if( status == OPEN_SUCCESS )
			{
				if( stmt == null )
					stmt = c.createStatement();
		        stmt.executeUpdate(sql);
		        c.commit();
			}else{
				System.out.println("Can not execute command\"" + sql + "\": previosu operation failed.");
			}
		} catch (Exception e) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
    }
	
	public static ResultSet selectDatabase(String  query)
    {
		try {
			if(status != OPEN_SUCCESS)
				openDatabase();
			if( status == OPEN_SUCCESS )
			{
				if( stmt == null )
					stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery( query );
				return rs;
				
			}else{
				System.out.println("Can not select: previosu operation failed.");
				return null;
			}
      } catch ( Exception e ) {
    	 status = OPEN_FAILED;  
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
         return null;
      }
    }
}
	/*
	public static void db_delete()
    {
      try {
    	  if( status == OPEN_SUCCESS )
			{
				if( stmt == null )
					stmt = c.createStatement();
		        String sql = "DELETE from " + db_table_name + " where ID=1;";
		        stmt.executeUpdate(sql);
		        c.commit();
			}else{
				System.out.println("Can not select: previosu operation failed.");
			}
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        System.exit(0);
      }
    }
}*/
