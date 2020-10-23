import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    //client socket, and input- output streams variables
    private Socket clientSocket;
    private DataInputStream dataInputFromClient;
    private DataOutputStream dataOutputToClient;
    private ArrayList<ClientHandler> clients;
    private String userName;
    private int points;


    //constructor
    public ClientHandler(Socket clientSocket, Server server, ArrayList<ClientHandler> clients) throws IOException{
        this.clientSocket = clientSocket;
        this.clients = clients;
        dataInputFromClient = new DataInputStream(clientSocket.getInputStream());
        dataOutputToClient = new DataOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public void run() {

        try {
            while(true){ //while client is connected

                //compose messages
                String userName = dataInputFromClient.readUTF();
                String welcomeMessage = "Welcome to the Hangman Server ";

                //output UTF encode bytes to client
                dataOutputToClient.writeUTF(welcomeMessage.concat(userName));

                //set username for this client
                setUserName(userName);

                //write out
                for(ClientHandler client : clients){
                    System.out.println(client.getUserName() + " is connected to server and has " + client.getPoints() + " points");

                    //virker ikke, clients modtager ikke besked
                    dataOutputToClient.writeUTF("Test");
                }

             }

        } catch (IOException e) {
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

