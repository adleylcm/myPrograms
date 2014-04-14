package lzl.io.demo.splitfiles;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DealWithIOException 
{
	public static void main(String[] args)
	{
		FileWriter fw = null;
		FileReader fr = null;
		try
		{
			fr = new FileReader("a.txt");
			fw = new FileWriter("b.txt");
			int temp;
			
			while(-1 != (temp = fr.read()))
			{
				fw.write(temp);
			}
			System.out.println("Copy completed!");
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(null != fr)
					fr.close();
			}catch(IOException e1)
			{
				e1.printStackTrace();
			}
			
			try
			{
				if(null != fw)
					fw.close();
			}
			catch(IOException e2)
			{
				e2.printStackTrace();
			}
		}
	}
}