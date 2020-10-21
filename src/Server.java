//Impoter biblioteker
import java.io.*; //Den sørger for at systemet kan lave input og output gennem "data streams"
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

    private static final int port = 6666;
    //list to hold clients
    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {


        new Thread(() ->{ //thread for server socket
            try{
                //Opretter et server socket objekt med vores port/"dørnøgle"
                ServerSocket serverSocket = new ServerSocket(port);

                while(true){

                    //create new client sockets
                    Socket socket = serverSocket.accept();

                    //create thread for client communication (takes in clienhandler object that implements runnable)
                    ClientHandler clientHandler = new ClientHandler(socket);
                    new Thread(clientHandler).start();

                    //add to client arraylist
                    clients.add(clientHandler);

                }

            }catch (IOException exception){
                System.out.println(exception);
            }

        }).start(); //start thread for server socket

    }
}
