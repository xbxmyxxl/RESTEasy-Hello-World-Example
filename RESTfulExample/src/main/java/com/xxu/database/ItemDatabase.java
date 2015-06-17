package com.xxu.database;

import java.sql.*;
import com.xxu.type.*;

/**
 * @author xxu
 *
 *         inherits from the PostgreSql, adding the the attributes for Item type
 */
public class ItemDatabase extends PostgreSql {
	/* item is the table name in database*/
	private static final String db_table_name = "item";

	/**
	 * @param ID, the primary key
	 * @return Item if found, null otherwise
	 * 
	 * query the database by id
	 */
	public static Item inquiryItemById(String ID) {
		String query = "SELECT * FROM " + db_table_name + " where ID = " + ID
				+ " ;";
		logger.info("executing query"+query);
		
		ResultSet rs = PostgreSql.selectDatabase(query);
		Item return_item = new Item();

		try {
			/*checking whether the required item exist at all*/
			if (rs != null) {
				
				/*if it exists, return the item as an instance of Item*/
				while (rs.next()) {
					
					/*get the required data*/
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String brand = rs.getString("brand");
					float price = rs.getFloat("price");
					
					/*using setter to fill in the fields for this item*/
					return_item.setId(id);
					return_item.setName(name);
					return_item.setBrand(brand);
					return_item.setPrice(price);

				}
				rs.close();

				if (return_item != null) {
					return return_item;
				}
			} else {
				logger.warn("No item found which has id" + ID);
			}
		} catch (Exception e) {
			logger.warn(e.getClass().getName() + ": " + e.getMessage());

		}
		return null;
	}


}
