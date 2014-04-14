package trianlge23.derby.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyDemo {
	private Connection conn = null;
	private Statement stmt = null;

	public static void main(String[] args) {
		String ipAddress = "localhost";
		String portNumber = "1527";
		String dbPath = "Testdata";
		String dbName = "myDB";
		String dbURL = "jdbc:derby://" + ipAddress + ":" + portNumber + "/"
				+ dbPath + "/" + dbName + ";create=true;";
		String tableName = "basicinfo";
		String createTableQuery = "create table " + tableName
				+ "(id int not null, name varchar(12), cityname varchar(12))";
		String selectQuery = "select * from " + tableName;

		DerbyDemo dd = new DerbyDemo();
		dd.createConnection(dbURL);
		dd.createTable(createTableQuery);
		dd.insert(tableName, 0, "Trianlge23", "Beijing");
		dd.select(selectQuery);
		dd.shutdown(dbURL);
	}

	private void createConnection(String dbURL) {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	private void createTable(String createTableQuery) {
		try {
			stmt = conn.createStatement();
			stmt.execute(createTableQuery);
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
	}

	private void insert(String tableName, int id, String Name, String cityName) {
		try {
			stmt = conn.createStatement();
			stmt.execute("insert into " + tableName + " values (" + id + ",'"
					+ Name + "','" + cityName + "')");
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
	}

	private void select(String selectQuery) {
		try {
			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(selectQuery);
			ResultSetMetaData rsmd = results.getMetaData();
			int numberCols = rsmd.getColumnCount();
			for (int i = 1; i <= numberCols; i++) {
				System.out.print(rsmd.getColumnLabel(i) + "\t\t\t");
			}
			System.out
					.println("\n-------------------------------------------------------------");
			while (results.next()) {
				int id = results.getInt(1);
				String Name = results.getString(2);
				String cityName = results.getString(3);
				System.out.println(id + "\t\t\t" + Name + "\t\t\t" + cityName);
			}
			results.close();
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
	}

	private void shutdown(String dbURL) {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				DriverManager.getConnection(dbURL + "shutdown=true");
				conn.close();
			}
		} catch (SQLException sqlExcept) {
//			sqlExcept.printStackTrace();
		}
	}
}