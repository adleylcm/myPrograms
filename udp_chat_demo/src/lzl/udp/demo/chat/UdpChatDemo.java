package lzl.udp.demo.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Send implements Runnable
{
	private DatagramSocket ds;
	private static final int SENDPORT = 8088;
	
	public Send(DatagramSocket ds)
	{
		this.ds = ds;
	}
	
	public void run()
	{
		try
		{
			//read the input data
			BufferedReader br = new BufferedReader(
									new InputStreamReader(
											System.in));
			String readLine = null;
			//read input always, but break out of the loop when read "quit" 
			while(null != (readLine = br.readLine()))
			{
				if("quit".equalsIgnoreCase(readLine))
					break;
				
				byte[] buf = readLine.getBytes();
				
				//encapsulated the data into a DatagramPacket
				DatagramPacket dp = new DatagramPacket(buf, 
													   buf.length, 
													   InetAddress.getByName("127.0.0.1"),
													   SENDPORT);
				//send the packet
				ds.send(dp);
			}
			
			ds.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

class Receive implements Runnable
{
	private DatagramSocket ds;
	private static final int SIZE = 1024;
	
	public Receive(DatagramSocket ds)
	{
		this.ds = ds;
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				byte[] buf = new byte[SIZE];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				//receive the packet
				ds.receive(dp);
				
				String dataIP = dp.getAddress().getHostAddress();
				String data = new String(dp.getData(), 0, dp.getLength());
				
				System.out.println(dataIP + " : " + data);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

public class UdpChatDemo
{
	public static void main(String[] args) throws Exception
	{
		//private final int LISTENPORT = 8088;
		final int LISTENPORT = 8088;
		DatagramSocket sendSocket = new DatagramSocket();
		DatagramSocket receiveSocket = new DatagramSocket(LISTENPORT);
		
		new Thread(new Send(sendSocket)).start();
		new Thread(new Receive(receiveSocket)).start();
	}
}
