package com.xxu.database;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.xxu.type.Usr;

public class UsrDatabase extends PostgreSql {

	private static final String db_table_name = "usr";
	final static Logger logger = Logger.getLogger(UsrDatabase.class);

	public static Usr inquiry(String ID) {
		String query = "SELECT * FROM " + db_table_name + " where ID = " + ID
				+ " ;";
		ResultSet rs = PostgreSql.selectDatabase(query);
		Usr returnUsr = new Usr();

		try {
			if (rs != null) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String publicKey = rs.getString("public_key");
					String RandomString = rs.getString("randomstring");
					returnUsr.setId(id);
					returnUsr.setName(name);
					returnUsr.setRandomString(RandomString);
					returnUsr.setPublicKey(publicKey);

				}
				rs.close();

				if (returnUsr != null) {
					return returnUsr;
				}

			} else {
				logger.warn("No item found which has id" + ID);
			}
		} catch (Exception e) {
			logger.warn(e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}

	public void insertRandomString(int id, String randomStr) {
		try {
			String sql_update = "UPDATE " + db_table_name
					+ " set RANDOMSTRING = '" + randomStr + "' where ID=" + id
					+ ";";
			super.executeDatabaseStmt(sql_update);
		} catch (Exception e) {
			logger.warn(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

}
