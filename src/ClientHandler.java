import com.hangman.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    //client socket, and input- output streams variables
    private Socket clientSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ArrayList<ClientHandler> clients;
    private String userName;
    private int points;
    private Message message = null;


    //constructor
    public ClientHandler(Socket clientSocket, Server server, ArrayList<ClientHandler> clients) throws IOException{
        this.clientSocket = clientSocket;
        this.clients = clients;
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {

        try {
            while(true){ //while client is connected

                //read message object
                Message message = (Message) objectInputStream.readObject();
                //get type of message
                String typeOfMessage = message.getTypeOfMessage();

                //if type of message is username
                if(typeOfMessage.equalsIgnoreCase("username")){

                    //get the message content, and set username
                    String userName = message.getMessage();
                    setUserName(userName);

                    //compose message object
                    Message messageToClients =
                           new Message(this.getUserName()
                                        + " is connected to server and has "
                                        + this.getPoints() + " points",
                           "outputFromServer");

                    //write out
                    for(ClientHandler client : clients){
                       client.objectOutputStream.writeObject(messageToClients);
                    }
                    break;
                } //end if message object is of type user


             }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}

