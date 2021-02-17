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
	 * Get current line and absolute path of current file
	 * 
	 * @param file
	 */
	public static void getCurrentLineAndDir(final String file)
	{	
		System.out.println("Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		getDirPath(file);
	}
	
	
	/**
	 * print absolute path
	 * 
	 * @param file
	 */
	public static void getDirPath(final String file)
	{
		try
		{
			File new_file= new File(file);
			String absolutePath = new_file.getAbsolutePath();
			System.out.println(absolutePath);
		}
		catch(Exception e)
		{
			System.out.println("Failed to print absolute path...");
			System.out.println(e);
		}
	}
}
