package lzl.io.demo.splitfiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SplitLisaFiles 
{
	//the path of the source files
	private static final String SOURCEPATH = ".\\src\\lzl\\io\\demo\\files\\source\\lisa\\";
	//the same prefix of the source files 
	private static final String SOURCENAME = "LISA";
	//the suffix of the source files
	private static final String[] SOURCEFILE = new String[]{"0.001", "0.501", "1.001", "1.501", "2.001", "2.501", "3.001",
		"3.501", "4.001", "4.501", "5.001", "5.501", "5.627", "5.850"};
	//the separator of an document 	 													
	private static final String ENDSTRING = "********************************************";
	//the path of the target files
	private static final String OUTPUTPATH = ".\\src\\lzl\\io\\demo\\files\\output\\lisa\\";
	//the same prefix of the target files
	private static final String OUTPUTNAME = "LISA";


	public static void main(String[] args)
	{	
		int count = 1;									//to record the number of documents
		int sourceFileCount = 0;
		long startTime = System.currentTimeMillis();
		long endTime = 0;

		File sourceFile = new File(SOURCEPATH + SOURCENAME + SOURCEFILE[0]);																		
		File outputFile = new File(OUTPUTPATH + OUTPUTNAME + Integer.toString(count));
		BufferedReader readIn = null;
		BufferedWriter readOut = null;

		try
		{

			/*
				readIn = new BufferedReader(
							new InputStreamReader(
								new FileInputStream(sourceFile)));
			 */
			readIn = new BufferedReader(
					new FileReader(sourceFile));
			/*
				readOut = new BufferedWriter(
							new OutputStreamWriter(
								new FileOutputStream(outputFile)));
			 */
			readOut = new BufferedWriter(
					new FileWriter(outputFile));

			String oneLineString = null;
			for(; sourceFileCount < SOURCEFILE.length; sourceFileCount++)
			{
				while(null != (oneLineString = readIn.readLine()))
				{
					if(!(ENDSTRING.equals(oneLineString)))
					{
						readOut.write(oneLineString);
						readOut.newLine();
					}
					else
					{
						count ++;
						readOut.close();
						outputFile = new File(OUTPUTPATH + OUTPUTNAME + Integer.toString(count));
						/*
							readOut = new BufferedWriter(
										new OutputStreamWriter(
											new FileOutputStream(outputFile)));
						 */
						readOut = new BufferedWriter(
								new FileWriter(outputFile));
					}
				}
				readIn.close();
				readOut.close();

				if(SOURCEFILE.length == sourceFileCount + 1)
					break;

				sourceFile = new File(SOURCEPATH + SOURCENAME + SOURCEFILE[sourceFileCount + 1]);
				/*
					readIn = new BufferedReader(
							new InputStreamReader(
								new FileInputStream(sourceFile)));
				 */
				readIn = new BufferedReader(
						new FileReader(sourceFile));
				outputFile = new File(OUTPUTPATH + OUTPUTNAME + Integer.toString(count));
				/*
					readOut = new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(outputFile)));
				 */
				readOut = new BufferedWriter(
						new FileWriter(outputFile));
			}
			endTime = System.currentTimeMillis();

			outputFile.deleteOnExit();
			count --;

			System.out.println("Create " + count + " files took " + (endTime - startTime) + " milliseconds");

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
