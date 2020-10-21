import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    //client socket, and input- output streams variables
    private Socket clientSocket;
    private DataInputStream dataInputFromClient;
    private DataOutputStream dataOutputToClient;



    //constructor
    public ClientHandler(Socket clientSocket) throws IOException{
        this.clientSocket = clientSocket;
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

                //server output
                System.out.println(userName + " connected to server");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

