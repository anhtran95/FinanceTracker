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
	//debug
	private static final String file_name = "DatabaseHelper.java";
	
	//constant
	public static final int MYSQL_DUPLICATE_ERROR = 1062;
	private static final String database =  "userlogin";
	private static final String db_login_user = "root";
	private static final String db_login_pass = "Baoanh123!";
	
	
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
			connectDb = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, db_login_user, db_login_pass);
			System.out.println("Connect to database successful...");
			return connectDb;
		}
		catch(SQLException sqlEx)
		{
			System.out.println("Failed to connect to database...");
			System.out.println(sqlEx);
			System.out.println(sqlEx.getErrorCode());
			DebugHelper.getCurrentLineAndDir(file_name);
		}
		catch(Exception exp)
		{
			System.out.println("Failed to connect to database...");
			System.out.println(exp);
			DebugHelper.getCurrentLineAndDir(file_name);
		}
		
		return connectDb;
		
	}

}
