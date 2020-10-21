import java.io.*;
import java.net.Socket;

public class HandleAClient implements Runnable {

    //client socket
    private Socket socket;

    //constructor
    public HandleAClient(Socket socket){
        this.socket = socket;
    }


    @Override
    public void run() {

        try {
            DataInputStream dataInputFromClient = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputToClient = new DataOutputStream(socket.getOutputStream());

            //while client is connected
            while(true){

                //compose messages
                String userName = dataInputFromClient.readUTF();
                String welcomeMessage = "Welcome to the Hangman Server ";

                //output UTF encode bytes to client
                dataOutputToClient.writeUTF(welcomeMessage.concat(userName));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
