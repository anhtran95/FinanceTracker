package mysql;

import java.io.File;

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
			System.out.println(e);
			System.out.println("DebugHelper: Error printing out absolute path for current directory.");
		}
	}
	
	
	/**
	 * print line and absolute path given file name
	 * 
	 * @param addr
	 * 			file name
	 */
	public static void printDebugLocation(final String addr)
	{
		DebugHelper.getCurrentLine();
		DebugHelper.getDirPath(addr);
		
	}
}
