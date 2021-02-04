package mysql;

import java.io.File;

public class DebugHelper {

	//Private constructor to avoid instantiated	
	private DebugHelper() 
	{
		throw new AssertionError();
	}	
	
	//print current line
	public static void getCurrentLine()
	{	
		System.out.println("Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
	}
	
	//print current directory path
	public static void getDirPath()
	{
		System.out.println(new File("").getAbsolutePath());
	}
}
