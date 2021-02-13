package mysql;

import java.sql.*;


/* @author Anh Tran
 * 
 * Description:
 * Helper functions with connection to database
 * 
 */




public class DatabaseHelper 
{
	
	public static final int MYSQL_DUPLICATE_ERROR = 1062;
	
	//Private constructor to avoid instantiated	
	private DatabaseHelper() 
	{
		throw new AssertionError();
	}	
	
	
	//return a Connection to database
	public static Connection createConnection()
	{
		Connection connectDb = null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connectDb = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "Baoanh123!");
			System.out.println("Connect to database successful...");
			return connectDb;
		}
		catch(SQLException sqlEx)
		{
			System.out.println("Failed to connect to database...");
			System.out.println(sqlEx);
			System.out.println(sqlEx.getErrorCode());
			DebugHelper.getCurrentLine();
			DebugHelper.getDirPath("DatabaseHelper.java");
		}
		catch(Exception exp)
		{
			System.out.println("Failed to connect to database...");
			System.out.println(exp);
			DebugHelper.getCurrentLine();
			DebugHelper.getDirPath("DatabaseHelper.java");
		}
		
		return connectDb;
		
	}

}
