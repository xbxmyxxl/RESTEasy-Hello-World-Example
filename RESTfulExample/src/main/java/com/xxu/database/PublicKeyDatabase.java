package com.xxu.database;

import java.sql.ResultSet;

/**
 * @author xxu
 * 
 * 
 *         class for getting public key of usr by id from database. Note the key
 *         should be byte array, however,in order to debug and save we convert
 *         the key to hex array, and convert back.
 */
public class PublicKeyDatabase extends PostgreSql {
	private static final String db_table_name = "UsrKeyMap";

	/**
	 * @param ID
	 * @return public key of use
	 *
	 * 
	 *         as title says, return the public key of usr by id, which is used to
	 *         encode data from the server side
	 */
	public static String getPublicKeyById(String ID) {
		String query = "SELECT * FROM " + db_table_name + " where ID = " + ID
				+ " ;";
		
		/*getting the key from the databse*/
		ResultSet rs = PostgreSql.selectDatabase(query);
		String publicKeyHex = "";
		
		try {
			if (rs != null) {
				if (rs.next()) {
					publicKeyHex = rs.getString("publickey");
				}
				rs.close();
				return publicKeyHex.replaceAll("\\s+", "");

			} else {
				logger.warn("No item found which has id" + ID);
				return null;
			}
		} catch (Exception e) {
			logger.info(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}

}
