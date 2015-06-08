package com.xxu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Statement;
public class PostgreSql {
	
	private static Statement stmt = null;
	private static Connection c = null;
	private static int status = 1 ;
	private static final int OPEN_SUCCESS = 0;
	private static final int OPEN_FAILED = 1;
	//private static final String db_table_name = "customer";
	//private String return_string = "";
	//private String sql_create_table= "CREATE TABLE " + db_table_name + " " +
		//	"(ID INT PRIMARY KEY     NOT NULL," +
		//	" NAME           TEXT    NOT NULL, " +
		//	" AGE            INT     NOT NULL, " +
		//	" ADDRESS        CHAR(50), " +
		//	" SALARY         REAL)";
	private static String sql_insert = "INSERT INTO " + "customer" + " (ID,NAME,AGE,ADDRESS,SALARY) "
			+ "VALUES (4, 'Pasdfl', 32, 'California', 20000.00 );";
	//private static String sql_delete = "DELETE from " + db_table_name + " where ID=1;";
	//private static String sql_update = "UPDATE " + db_table_name + " set ACCOUNT_BALANCE = 27000.00 where ID=1;";
	
	public static void main(String args[]) {
	      db_open();
	      
	      db_exe_stmt(sql_insert);
	      //db_exe_stmt();
	     
	      //db_select();
	      db_close();
	   }
	
	public static void db_open( )
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
	
	public static void db_close()
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
	
	public static void db_exe_stmt(String sql)
	{	
		try {
			if( status != OPEN_SUCCESS )
				db_open();
			if( status == OPEN_SUCCESS )
			{
				if( stmt == null )
					stmt = c.createStatement();
		        stmt.executeUpdate(sql);
		        c.commit();
		        db_close();
			}else{
				System.out.println("Can not execute command\"" + sql + "\": previosu operation failed.");
			}
		} catch (Exception e) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
    }
	
	public static ResultSet db_select(String  query)
    {
		try {
			if(status != OPEN_SUCCESS)
				db_open();
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
	public static void db_create_table( )
    {
		try{
			if( status == OPEN_SUCCESS )
			{
				if( stmt == null )
					stmt = c.createStatement();
				String sql = "CREATE TABLE " + db_table_name + " " +
						"(ID INT PRIMARY KEY     NOT NULL," +
						" NAME           TEXT    NOT NULL, " +
						" AGE            INT     NOT NULL, " +
						" ADDRESS        CHAR(50), " +
						" SALARY         REAL)";
				stmt.executeUpdate(sql);
				System.out.println("Table created successfully");
			} else{
				System.out.println("Can not create table: previosu operation failed.");
			}
    	  
		} catch ( Exception e ) {
			status = OPEN_FAILED;
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
    }
	
	public static void db_insert()
    {
		try{
			if( status == OPEN_SUCCESS )
			{
				if( stmt == null )
					stmt = c.createStatement();
        
				String sql = "INSERT INTO " + db_table_name + " (ID,NAME,AGE,ADDRESS,SALARY) "
						+ "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
				stmt.executeUpdate(sql);
				System.out.println("Insert operation created successfully");
        
			} else {
				System.out.println("Can not insert: previosu operation failed.");
			}
		}catch ( Exception e ) {
			status = OPEN_FAILED;
	        System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	        System.exit(0);
	      }
    }
	
	public static void db_update()
    {
      try {
    	  if( status == OPEN_SUCCESS )
			{
				if( stmt == null )
					stmt = c.createStatement();
		        String sql = "UPDATE " + db_table_name + " set SALARY = 27000.00 where ID=1;";
		        stmt.executeUpdate(sql);
			}else{
				System.out.println("Can not select: previosu operation failed.");
			}
      } catch ( Exception e ) {
    	 status = OPEN_FAILED;
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
    }
	
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
