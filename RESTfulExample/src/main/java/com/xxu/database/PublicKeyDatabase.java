package com.xxu.database;

import java.sql.ResultSet;

public class PublicKeyDatabase extends PostgreSql {
	private static final String db_table_name = "UsrKeyMap";


	public static String getPublicKeyById(String ID) {
		String query = "SELECT * FROM " + db_table_name + " where ID = " + ID
				+ " ;";
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
				System.out.println("No item found which has id" + ID);
				return null;
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}

	public static void main(String[] args) {
		 String  public_key_test = "30820122300D06092A864886F70D01010105000382010F003082010A02820101009E421B1618BA"
				+ "641A6D19BC51107DA739A038F2D4106D1FD3853070F8956EEC74A2181AB9D6CBF8A45999CB60F0406EC72A5ED146DFB9CAA197501E0B6D43"
				+ "47308F3E2A3191553FEC62FBFFD3A4F63A0787C85966240497820BF2F73AD14F6AD2EA1763099104C7C85E295F45897EAEF3BA536708C6"
				+ "7B18CD53AC3B31727F315C63C100FB5E6DAC197389F29C4F95AFD1F49D285FE2AD31A9E1D30CD5CA8321E1E82C0995EB4816997922FB8"
				+ "7C69EAA9EC66C8B46813F722091F3448FB9971C7AFC310896594EBBE656A7BC180A2C15238915FD7A780A4B15A057C3243FFD6460FDB"
				+ "DC394B23FC95B0611BE4FDEE3532A515F6485216D3CD0D2A77FB1986342930203010001";
		System.out.println(PublicKeyDatabase.getPublicKeyById("1").equals(public_key_test));
	}
}
