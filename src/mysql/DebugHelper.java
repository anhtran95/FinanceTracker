package mysql;

import java.io.File;


/* @author Anh Tran
 * 
 * Description:
 * Help with debug throughout
 */


public class DebugHelper {

	//Private constructor to avoid instantiated	
	private DebugHelper() 
	{
		throw new AssertionError();
	}	
	
	
	/**
	 * print current line
	 */
	public static void getCurrentLine()
	{	
		System.out.println("Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	}
	
	
	/**
	 * print absolute path for given file name
	 * 
	 * @param addr
	 * 			file name
	 */
	public static void getDirPath(final String addr)
	{
		try
		{
			File file= new File(addr);
			String absolutePath = file.getAbsolutePath();
			System.out.println(absolutePath);
		}
		catch(Exception e)
		{
			System.out.println("Failed to print absolute path...");
			System.out.println(e);
		}
	}
}
