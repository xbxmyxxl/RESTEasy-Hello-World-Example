package com.xxu.database;

import java.sql.*;
import com.xxu.type.*;

public class ItemDatabase extends PostgreSql {
	private static final String db_table_name = "item";
	
	public static Item inquiryItemById( String ID)
    {
		String query = "SELECT * FROM " + db_table_name + " where ID = " + ID +" ;";
		System.out.println(query);
		ResultSet rs = PostgreSql.selectDatabase(query);
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
			}
			else
			{
				System.out.println("No item found which has id" + ID );
			}
		}
		catch (Exception e) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			
		}
		return null;
    }
	
	public static void main(String args[]) {
		Item a = ItemDatabase.inquiryItemById("1");
		a.print();
	 }

}
