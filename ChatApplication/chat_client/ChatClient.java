package chat_client;

import java.awt.*;


import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.net.*;
import java.util.*;


public class ChatClient extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 76890065777428060L;
	//arraylist for the lists of users
	ArrayList<String> users = new ArrayList();

    String username, address = "localhost";
    int port = 2222;
    
    private static Boolean isConnected = false;
    BufferedReader reader;
    PrintWriter writer;
	Socket clientSocket = null;

	private JPanel centralPanel, header, body, message;
	private JTextArea chatArea;
	
	private JLabel labelUsername,labelPassword;
	private JTextField textUsername,textPassword,textMessage;
	private JButton connect,disconnect,sendMessage;
	Container contentArea = getContentPane();
	
	//thread method for connecting a new user
	public void ListenThread() {
		Thread ChatClient = new Thread(new IncomingUser());
        ChatClient.start();
	}
	//thread 
	public void addUser(String string) {
        users.add(string);	
	}
	public void removeUser(String string) {
        chatArea.append(string + " is now offline.\n");
	}
	
	public void writeUsers() {
		String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token:tempList) 
        {
            //users.append(token + "\n");
        }
		
	}
	private void Disconnect() {
		try {
			 chatArea.append("Disconnected.\n");
	         clientSocket.close();
		} catch(Exception ex) {
	         chatArea.append("Failed to disconnect. \n");
	    }
	     isConnected = false;
	     textUsername.setEditable(true);	
	}
	private void sendDisconnect() {
		String bye = (username + ": :Disconnect");
	    try{
	    	writer.println(bye); 
	    	writer.flush(); 
	    } catch (Exception e) {
	        chatArea.append("Could not send Disconnect message.\n");
	    }
		
	}

	private ChatClient(){
		setTitle("Clash of Clans Chat Application");
		setSize(500,600);
		centralPanel = new JPanel();
		centralPanel.setSize(500,600);
		centralPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		header = new JPanel();
		header.setBackground(Color.YELLOW);
		
		header.setLayout(new GridBagLayout());
		GridBagConstraints d = new GridBagConstraints();
		d.fill = GridBagConstraints.HORIZONTAL;
		
		
		labelUsername = new JLabel("Id:");
		d.gridx = 0;
		d.weightx = 0.5;
		d.gridy = 0;
		header.add(labelUsername, d);
		
		textUsername = new JTextField();
		d.gridx = 1;		
		d.gridwidth = 4;
		d.gridy = 0;		
		header.add(textUsername,d);
		
		labelPassword = new JLabel("Password:");
		d.gridx = 5;
		d.gridwidth = 1;
		d.gridy = 0;
		header.add(labelPassword, d);
		
		textPassword = new JTextField();
		d.gridx = 6;		
		d.gridwidth = 4;
		d.gridy = 0;		
		header.add(textPassword,d);
		
		connect = new JButton("Join Game");
		d.gridx = 10;
		d.gridwidth = 1;
		d.gridy = 0;
		header.add(connect, d);
		
		disconnect = new JButton("Disconnect");
		d.gridx = 11;
		d.gridwidth = 1;
		d.gridy = 0;
		
		header.add(disconnect, d);
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		centralPanel.add(header, c);
		
		body = new JPanel();
		body.setBackground(Color.GREEN);
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		centralPanel.add(body, c);
		chatArea = new JTextArea();
		chatArea.setColumns(50);
		chatArea.setRows(20);
		chatArea.setEditable(false);
		body.add(chatArea);
		
		message = new JPanel();
		message.setBackground(Color.BLUE);
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 2;
		centralPanel.add(message, c);
		
		
		textMessage = new JTextField();
		textMessage.setPreferredSize(new Dimension(300,20));
		message.add(textMessage);
		
		sendMessage = new JButton("Send");
		connect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
                connect_ActionPerformed(arg0);

			}
	    });
		
		disconnect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				disconnect_ActionPerformed(arg0);
			}
	    });
		sendMessage.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				sendMessage_ActionPerformed(arg0);
			}
	    });
		
		message.add(sendMessage);
		contentArea.add(centralPanel);
		setContentPane(contentArea);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	public class IncomingUser implements Runnable
    {
		public void run() {
			
			String[] data;
	        String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
	        
	        try 
	        {
	            while ((stream = reader.readLine()) != null) 
	            {	
	                 data = stream.split(":");
	                 for (String token:data) 
	                 {
	                     System.out.println(token + "\n");
	                 }
	                 if (data[2].equals(chat)) 
	                 {
	                    chatArea.append(data[0] + ": " + data[1] + "\n");
	                    chatArea.setCaretPosition(chatArea.getDocument().getLength());
	                 } 
	                 else if (data[2].equals(connect))
	                 {
	                    chatArea.removeAll();
	                    addUser(data[0]);
	                 } 
	                 else if (data[2].equals(disconnect)) 
	                 {
	                     removeUser(data[0]);
	                 } 
	                 else if (data[2].equals(done)) 
	                 {
	                    writeUsers();
	                    users.clear();
	                 }
	            }
	       }catch(Exception ex) { }
	    }
    }


	private void disconnect_ActionPerformed(ActionEvent arg0) {
		sendDisconnect();
        Disconnect();
		
	}
	private void connect_ActionPerformed(ActionEvent arg0) {
		 if (isConnected == false) 
	        {
	            username = textUsername.getText();
	            textUsername.setEditable(false);
	            try 
	            {
	                clientSocket = new Socket(address, port);
	                InputStreamReader streamreader = new InputStreamReader(clientSocket.getInputStream());
	                reader = new BufferedReader(streamreader);
	                writer = new PrintWriter(clientSocket.getOutputStream());
	                writer.println(username + ":has connected.:Connect");
	                writer.flush();
	                isConnected = true; 
	            } 
	            catch (Exception ex) 
	            {
	                chatArea.append("Cannot Connect! Try Again. \n");
	                textUsername.setEditable(true);
	            }
	            ListenThread();
	            
	        } else if (isConnected == true) 
	        {
	            chatArea.append("You are already connected. \n");
	        }
	}
	private void sendMessage_ActionPerformed(ActionEvent arg0) {
		String nothing = "";
        if ((textMessage.getText()).equals(nothing)) {
            textMessage.setText("");
            textMessage.requestFocus();
        } else {
            try {
               writer.println(username + ":" + textMessage.getText() + ":" + "Chat");
               writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                chatArea.append("Message was not sent. \n");

            }
            textMessage.setText("");
            textMessage.requestFocus();
        }

        textMessage.setText("");
        textMessage.requestFocus();		
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new ChatClient().setVisible(true);
            }
        });
	}
}
