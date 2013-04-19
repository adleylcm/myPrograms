package lzl.tcp.demo.touppercase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
	public static final int PORT = 8088;
	
	public static void main(String[] args)
	{
		ServerSocket ss = null;
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = null;
		
		try
		{
			ss = new ServerSocket(PORT);
			System.out.println("ServerSocket Start:" + ss);
			socket = ss.accept();
			System.out.println(socket + " connection accept");
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//instead of BufferedReader, because it can flush automatic
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			
			while(null != (str = br.readLine()))
			{	
				System.out.println("the client say : " + str);

				pw.println(str.toUpperCase());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Close ...");
			try
			{
				br.close();
				pw.close();
				socket.close();
				ss.close();	
			}catch(Exception e2)
			{
				e2.printStackTrace();
			}
			
		}
	}
}
