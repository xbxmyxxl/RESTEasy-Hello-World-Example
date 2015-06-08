package com.xxu.database;

import java.sql.*;
import com.xxu.type.*;

public class ItemDB extends PostgreSql {
	private static Statement stmt = null;
	private static Connection c = null;
	private static int status = 1 ;
	private static final int OPEN_SUCCESS = 0;
	private static final int OPEN_FAILED = 1;
	private static final String db_table_name = "item";
	
	public static Item inquiry( String ID)
    {
		String query = "SELECT * FROM " + db_table_name + " where ID = " + ID +" ;";
		ResultSet rs = PostgreSql.db_select(query);
		Item return_item = new Item();
		
		try{
			if(rs!=null)
			{
				while ( rs.next() ) {
					int id = rs.getInt("id");
					String  name = rs.getString("name");
					String  brand = rs.getString("brand");
					float price = rs.getFloat("price");
					return_item.setId(id);
					return_item.setName(name);
					return_item.setBrand(brand);
					return_item.setPrice(price);
					
				}
				rs.close();
				
				if(return_item!=null)
				{
					return return_item;
				}
				else 
				{
					System.out.println("No item found which has id" + ID );
					return null;
				}
			
			}
			else
			{
				System.out.println("No item found which has id" + ID );
				return null;
			}
		}
		catch (Exception e) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			return null;
		}
    }
	
	public static void main(String args[]) {
		Item a = ItemDB.inquiry("1");
		a.print();
	 }

}
