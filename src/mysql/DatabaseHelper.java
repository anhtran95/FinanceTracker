package mysql;

import java.sql.*;


/* @author Anh Tran
 * 
 * Description:
 * Help connect to database
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
	
	public static Connection createConnection()
	{
		Connection connectDb = null;
		
		try
		{
			//connect to database
			Class.forName("com.mysql.cj.jdbc.Driver");
			connectDb = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "Baoanh123!");
			return connectDb;
		}
		//SQL error
		catch(SQLException sqlEx)
		{
			System.out.println(sqlEx);
			System.out.println(sqlEx.getErrorCode());
			DebugHelper.getCurrentLine();
			DebugHelper.getDirPath("DatabaseHelper.java");
		}
		//all other errors
		catch(Exception exp)
		{
			System.out.println(exp);
			DebugHelper.getCurrentLine();
			DebugHelper.getDirPath("DatabaseHelper.java");
		}
		
		return connectDb;
		
	}

}
