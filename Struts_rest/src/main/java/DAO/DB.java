package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

	private Connection connection;
	Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/storeManagement","root","sailu");
		return connection;
	}

}
