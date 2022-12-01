package org.distributed.connector;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

//import org.distributed.broker.ServerHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class ServerManager implements Runnable{
    //private int port;
    private ArrayList<ServerSocket> serverSocket;
    private ArrayList<Socket> sockets;
    //use ConcurrentHashMap instead? maybe boolean can be used to show whether server is available
    static HashMap<Integer, Boolean> servers = new HashMap<Integer, Boolean>();
    static ArrayList<Integer> portList = new ArrayList<>();
    
    public ServerManager(ArrayList<Integer> ports) {
        portList = ports;
        try {
            for(int i : portList)
            {
            	serverSocket.add(new ServerSocket(i));
            	servers.put(i,true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
            	for(ServerSocket j : serverSocket)
                {
            		Socket newSocket = j.accept();
            		sockets.add(newSocket);
                }
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            //new Thread(new ServerHandler(socket)).start();
//            for(Socket k : sockets)
//            {
//            	new Thread(new ServerHandler(k)).start();
//            }
        }
    }

}

//below to use as reference for message and error handling
//class WebClient extends Thread
//{
//	Socket client;
//	String method;
//	String resource;
//	String protocol;
//	public void run()
//	{
//		//System.out.println("In Client's run method");
//		try 
//		{
//			processRequest();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//	public void processRequest() throws Exception
//	{
//		//System.out.println("Socket in client"+client);
//		try 
//		{
//			InputStreamReader isr=new InputStreamReader(client.getInputStream());
//			BufferedReader br=new BufferedReader(isr);
//			StringBuilder request=new StringBuilder();
//			String line;
//			line=br.readLine();
//			//System.out.println("line = "+line);
//			HashMap<String,String> h=new HashMap<>();
//			while(line!=null && !line.isEmpty())
//			{
//				request.append(line+"\r\n");
//				line=br.readLine();
//				String arr[]=line.split(": ");
//				if(line=="\r\n"||line=="\n"||arr.length==1)
//					continue;
//				h.put(arr[0],arr[1]);
//			}
//			//System.out.println("Request="+request);
//			String requests[]=request.toString().split("\r\n");
//			String header[]=requests[0].split(" ");
//			if(header.length>1)
//			{
//				method=header[0];
//				resource=header[1];
//				protocol=header[2];
//			}
//			else 
//				return;
//			System.out.println("--"+method+"-"+resource+"-"+protocol+"--");
//			if(method.equals("GET"))
//				handleRequest();
//			client.close();
//
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//	}
//	public void handleRequest() throws Exception
//	{
//		//System.out.println("Currently handling HTML");
//		if(resource.equals("/"))
//		{
//			resource="index.html";
//		}
//		if(resource.startsWith("/"))
//		{
//			resource=resource.substring(1);
//		}
//		String path=WebServer.document_root+resource;
//		File reqFile=new File(resource);
//		Date date=new Date();
//		String response="";
//		OutputStream clientOutput=client.getOutputStream();
//		BufferedOutputStream bOutput=new BufferedOutputStream(clientOutput);
//		PrintStream clientOutputStream=new PrintStream(bOutput);
//		try {
//			if(reqFile.exists())
//			{
//				if(reqFile.canRead())
//				{
//					FileInputStream fileInput=new FileInputStream(reqFile);
//					clientOutputStream.print(("HTTP/1.0 200 OK\r\n"));
//					//System.out.println("File length : "+reqFile.length());
//					clientOutputStream.print(("Content-Length: "+reqFile.length()+"\r\n"));
//					String content="text/plain";
//			        if (resource.endsWith(".html") || resource.endsWith(".htm"))
//			         	content="text/html";
//			        else if (resource.endsWith(".jpg") || resource.endsWith(".jpeg"))
//			        	content="image/jpeg";
//				    else if(resource.endsWith(".svg"))
//				     	content="image/svg+xml";
//				    else if(resource.endsWith(".ico"))
//				     	content="image/vnd.microsoft.icon";
//				    else if (resource.endsWith(".gif"))
//				        content="image/gif";
//				    else if(resource.endsWith(".css"))
//				    	content="text/css";
//				     clientOutputStream.print(("Content-Type: "+content+"\r\n"));
//					clientOutputStream.print(("Date: "+date+"\r\n"));
//					clientOutputStream.print(("\r\n"));
//					byte byte_array[]=new byte[1000];
//			        int i;
//			        while ((i=fileInput.read(byte_array))>0)
//			        {
//			        	clientOutputStream.write(byte_array, 0, i);
//			        }
//				}
//				else 
//				{
//					
//					clientOutput.write(("HTTP/1.1 403 Forbidden\r\n").getBytes());
//					clientOutput.write(("\r\n").getBytes());
//					clientOutput.write(("ERROR : Page cannot be displayed").getBytes());
//
//				}
//				clientOutputStream.flush();
//			}
//			else 
//			{
//
//				clientOutput.write(("HTTP/1.1 404 Not Found\r\n").getBytes());
//				clientOutput.write(("\r\n").getBytes());
//				clientOutput.write(("ERROR : Page Not found").getBytes());
//			}
//			
//		}
//		catch(Exception e)
//		{
//				clientOutput.write(("HTTP/1.1 400 Bad Request\r\n").getBytes());
//				clientOutput.write(("\r\n").getBytes());
//				clientOutput.write(("ERROR : Bad Request").getBytes());
//			    e.printStackTrace();
//		}
//	}
//}
