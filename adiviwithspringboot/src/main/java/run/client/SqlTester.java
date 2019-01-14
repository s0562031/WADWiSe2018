package run.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlTester {
	
	private String db_url = "jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0562031__advidb";
	private String username = "_s0562031__advidb_generic";
	private String password = "s0562031";
	private Connection dbcon = null;
	private Statement stmt = null;
	
	public static void main(String[] args) throws SQLException {
		
		SqlTester sqltest = new SqlTester();
		ResultSet result = sqltest.find();
		ResultSetMetaData meda= result.getMetaData();
		sqltest.printTable(meda, result);
	

	}
	
	public ResultSet find() {
		
		ResultSet rs = null;
		
		try {
			dbcon = DriverManager.getConnection(db_url, username, password);
			stmt = dbcon.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users");
		}
		catch (SQLException e) {
			System.out.println(e);
			return null;
		} 
		
		return rs;
		
	}
	
	private void printTable(ResultSetMetaData meda, ResultSet rs) throws SQLException {
		
		int num_columns = meda.getColumnCount();
		System.out.println(num_columns + " Zeilen in der Tabelle.");
		
		while (rs.next()){
           for (int i=1; i<=num_columns;i++){
        	   String columnname = meda.getColumnName(i);
        	   System.out.println("|---------------------------|");
               System.out.printf ("|%s%n", columnname);
               System.out.printf("|%-1s%n",rs.getString(i));
           }
           System.out.println("##############################");
       }
	}

}
