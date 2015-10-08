package chat_server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.*;
import javax.swing.*;

public class ChatServer extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList clientOutputStreams;
	ArrayList<String> users;
	
	private JPanel centralPanel, body, footer;
	private JTextArea updateArea;
	private JTextField textMessage;
	
	private JButton startServer;

	Container contentArea = getContentPane();

	public ChatServer(){
		setTitle("Clash of Clans Server");
		setSize(500,600);
		centralPanel = new JPanel();
		centralPanel.setSize(500,600);
		centralPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		body = new JPanel();
		body.setBackground(Color.GREEN);
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		centralPanel.add(body, c);
		updateArea = new JTextArea();
		updateArea.setColumns(50);
		updateArea.setRows(20);
		updateArea.setEditable(false);
		body.add(updateArea);
		
		footer = new JPanel();
		footer.setBackground(Color.BLUE);
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		centralPanel.add(footer, c);
		textMessage = new JTextField();
		textMessage.setPreferredSize(new Dimension(300,20));
		footer.add(textMessage);
		startServer = new JButton("Start");
		startServer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				startServer_ActionPerformed(arg0);
			}
	    });
		
		footer.add(startServer);
		contentArea.add(centralPanel);
		setContentPane(contentArea);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	private void startServer_ActionPerformed(ActionEvent arg0) {
		Thread starter = new Thread(new ServerStart());
        starter.start();
        updateArea.append("Server started...\n");
	}
	public static void main(String args[]){
		java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() {
                new ChatServer().setVisible(true);
            }
        });
	}
	public class ServerStart implements Runnable {
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  

            try {
                ServerSocket serverSock = new ServerSocket(2222);
                while (true) 
                {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);

				Thread listener = new Thread(new manageClient(clientSock, writer));
				listener.start();
				updateArea.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                updateArea.append("Error making a connection. \n");
            }
        }
    }
	public class manageClient implements Runnable	{
	       BufferedReader reader;
	       Socket sock;
	       PrintWriter client;

	       public manageClient(Socket clientSocket, PrintWriter user) 
	       {
	            client = user;
	            try 
	            {
	                sock = clientSocket;
	                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
	                reader = new BufferedReader(isReader);
	            }
	            catch (Exception ex) 
	            {
	                updateArea.append("Unexpected error... \n");
	            }

	       }

	       public void run() 
	       {
	            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
	            String[] data;
	            try 
	            {
	                while ((message = reader.readLine()) != null) 
	                {
	                    updateArea.append("Received: " + message + "\n");
	                    data = message.split(":");
	                    
	                    for (String token:data) 
	                    {
	                        updateArea.append(token + "\n");

	                    }

	                    if (data[2].equals(connect)) 
	                    {
	                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
	                        addUser(data[0]);
	                    } 
	                    else if (data[2].equals(disconnect)) 
	                    {
	                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
	                        removeUser(data[0]);
	                    } 
	                    else if (data[2].equals(chat)) 
	                    {
	                        tellEveryone(message);
	                    } 
	                    else 
	                    {
	                        updateArea.append("No Conditions were met. \n");
	                    }
	                } 
	             } 
	             catch (Exception ex) 
	             {
	                updateArea.append("Connection lost. \n");
	                ex.printStackTrace();
	                clientOutputStreams.remove(client);
	             } 
		} 
	    }
	public void addUser(String string) {
		String message, add = ": :Connect", done = "Server: :Done", name = string;
        updateArea.append("Before " + name + " added. \n");
        users.add(name);
        updateArea.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
	}
	public void tellEveryone(String message) {
		Iterator it = clientOutputStreams.iterator();
		while (it.hasNext()){
            try {
            	PrintWriter writer = (PrintWriter) it.next();
            	writer.println(message);
            	updateArea.append("Sending: " + message + "\n");
                writer.flush();
                updateArea.setCaretPosition(updateArea.getDocument().getLength());

            } catch (Exception ex) {
            	updateArea.append("Error. \n");
            }
        } 
	}
	public void removeUser(String string) {
		String message, add = ": :Connect", done = "Server: :Done", name = string;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
	}
}
