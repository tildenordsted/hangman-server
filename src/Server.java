//Impoter biblioteker
import java.io.*; //Den sørger for at systemet kan lave input og output gennem "data streams"
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

    private int port = 6666;
    private ArrayList<ClientHandler> clients;


    public Server(){
        clients = new ArrayList<>();
    }

    public static void main(String[] args) {
        Server server = new Server();

        server.startServer();
    }

    public void startServer(){
        new Thread(() ->{ //thread for server socket
            try{
                //Opretter et server socket objekt med vores port/"dørnøgle"
                ServerSocket serverSocket = new ServerSocket(port);

                while(true){

                    //create new client sockets
                    Socket socket = serverSocket.accept();

                    //create thread for client communication (takes in clienthandler object that implements runnable interface)
                    ClientHandler clientHandler = new ClientHandler(socket, this, clients);

                    //add to client arraylist
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();

                }

            }catch (IOException exception){
                System.out.println(exception);
            }

        }).start(); //start thread for server socket
    }
}
