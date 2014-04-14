package lzl.tcp.demo.touppercase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client 
{
	public static int PORT = 8088;
	
	public static void main(String[] args)
	{
		Socket socket = null;
		BufferedReader cbr = null;
		BufferedReader sbr = null;
		BufferedWriter sbw = null;
		String str = null;
		
		try
		{
			socket = new Socket("localhost",PORT);
			System.out.println("Socket = " + socket);
			
			cbr = new BufferedReader(new InputStreamReader(System.in));
			sbr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sbw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			System.out.println("Please input : ");
			
			while(null != (str = cbr.readLine()))
			{	
				if("quit".equalsIgnoreCase(str))
					break;
				//to write the message to the socket's output stream and add a new line
				sbw.write(str);
				sbw.newLine();
				sbw.flush();
				
				System.out.println("Server send back message: " + sbr.readLine());
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				System.out.println("Close ...");
				//socket.shutdownOutput();
				cbr.close();
				sbr.close();
				sbw.close();
				socket.close();
				
			}catch(Exception e2)
			{
				e2.printStackTrace();
			}
		}
	}
}
