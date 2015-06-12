package com.xxu.database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.xxu.type.Item;
import com.xxu.type.Token;

public class TokenDatabase extends PostgreSql {
	private static final String db_table_name = "token";

	public TokenDatabase() {
		// TODO Auto-generated constructor stub
	}

	public static Token inquiry(String ID) {
		String query = "SELECT * FROM " + db_table_name + " where ID = " + ID
				+ " ;";
		ResultSet rs = PostgreSql.db_select(query);
		Token return_item = new Token();

		try {
			if (rs != null) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String token = rs.getString("token");
					Timestamp timestamp = rs.getTimestamp("timestamp");
					return_item.setId(id);
					return_item.setToken(token);
					return_item.setTimeStamp(timestamp);
				}
				rs.close();

				if (return_item != null) {
					return return_item;
				}
			} else {
				System.out.println("No item found which has id" + ID);
				return null;
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return null;
	}

	public static boolean varifyToken(String clientToken) throws Exception {
		String query = "SELECT * FROM " + db_table_name + " where token = '"
				+ clientToken + "' ;";
		ResultSet rs = PostgreSql.db_select(query);
		Token return_item = new Token();
		if (rs != null) {
			rs.next();
			int id = rs.getInt("id");
			String token = rs.getString("token");
			Timestamp timestamp = rs.getTimestamp("timestamp");
			return_item.setId(id);
			return_item.setToken(token);
			return_item.setTimeStamp(timestamp);
		}
		System.out.println(return_item.isTokenExpired());
		return (rs != null && !return_item.isTokenExpired());
	}

	public static String getTokenById(String ID) {

		Token token = TokenDatabase.inquiry(ID);
		System.out.println(token);
		if (token.getTimeStamp() == null)
			return null;
		if (token.isTokenExpired())
			return null;
		return token.getToken().replaceAll("\\s+", "");
	}

	public static void insertTokenById(String ID, String token) {
		Token return_item = inquiry(ID);
		String query = "INSERT INTO " + db_table_name + " (ID,TOKEN) "
				+ "VALUES (" + ID + ",'" + token + "');";
		if (return_item.getId() == Integer.parseInt(ID)) {
			
			query = "UPDATE " + db_table_name + " set TOKEN = '" + token
					+ "' where ID=" + ID + ";";
			PostgreSql.db_exe_stmt(query);
			
			query = "UPDATE " + db_table_name + " set TIMESTAMP = " + "now()"
					+ " where ID=" + ID + ";";
			PostgreSql.db_exe_stmt(query);
		
		} else {
			query = "INSERT INTO " + db_table_name + " (ID,TOKEN) "
					+ "VALUES (" + ID + ",'" + token + "');";
			PostgreSql.db_exe_stmt(query);
		}
		System.out.println(query);
		PostgreSql.db_exe_stmt(query);

	}

	public void DeleteExpiredToken() {
		String sql_delete = "DELETE FROM token WHERE timestamp < current_timestamp - interval '10' second";
		super.db_exe_stmt(sql_delete);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TokenDatabase a = new TokenDatabase();
		try {
			TokenDatabase.varifyToken("49ald2mic1b2g1u1ikbj28708d");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

}
