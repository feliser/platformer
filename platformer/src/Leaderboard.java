import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class Leaderboard 
{
	public static ArrayList<String> closeLeaderboardPositions;
	public static Socket clientSocket;
	public static int openSocket;
	public static BufferedWriter Output;
	public static BufferedReader Input;
	public static final String serverIP = "68.66.207.211";
	public static final int socketToConnectTo = 023123;
	public static boolean startingConnection = false;
	
	public static void checkForMessages()
	{
		if (Input != null)
		{
			String Line;
			try 
			{
				if ((Line = Input.readLine()) != null)
				{
					System.out.println(Line);
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void startConnection()
	{
		startingConnection = true;
		(new Thread() 
        {
			@Override
            public void run() 
            {
                try 
                {
                    clientSocket = new Socket(serverIP, socketToConnectTo);
                    BufferedWriter out = new BufferedWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream()));
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line = null;
                    out.write(Main.localUUID.toString());
                    out.newLine();
                    out.flush();
                    while ((line = in.readLine()) != null) 
                    {
                        openSocket = Integer.valueOf(line);
                        break;
                    }
                    Thread.sleep(50);
                    System.out.println(openSocket);
                    clientSocket = new Socket(serverIP, openSocket);
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    Output = out;
                    Input = in;
                    startingConnection = false;
                    while (true)
                    {
                    	if (clientSocket.isClosed() == false)
                    	{
                    		out.write("Keep Connection");
                        	out.newLine();
                        	out.flush();
                        	try 
                        	{
    							Thread.sleep(10);
    						} 
                        	catch (Exception e) 
                        	{
                        		endConnection();
    							e.printStackTrace();
    						}
                    	}
                    	else
                    	{
                    		break;
                    	}
                    }
                }
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            }
        }
        ).start();
	}
	
	public static void endConnection()
	{
		if (clientSocket.isClosed())
		{
			try 
			{
				Input.close();
				Output.close();
				clientSocket.close();
				openSocket = -1;
				Input = null;
				Output = null;
				clientSocket = null;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void getLeaderboard(UUID playerUUID, int Level)
	{
		boolean hasReceivedFinish = false;
		if (Output != null && startingConnection == false)
		{
			try 
			{
				Output.write(playerUUID.toString() + " " + Level);
				while (hasReceivedFinish == false)
				{
					String Line = null;
					while ((Line = Input.readLine()) != null) 
                    {
						if (Line != null)
						{
							if (!Line.contains("Finished"))
							{
								closeLeaderboardPositions.add(Line);
							}
							else
							{
								hasReceivedFinish = true;
							}
						}
					}
				}
			} 
			catch (Exception e) 
			{
				endConnection();
				e.printStackTrace();
			}
		}
		else if (startingConnection == false)
		{
			startConnection();
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() 
			        {
			            @Override
			            public void run() 
			            {
			            	getLeaderboard(playerUUID, Level);
			            }
			        }, 500);
		}
	}
}
