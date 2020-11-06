//Impoter biblioteker
import java.io.*; //Den sørger for at systemet kan lave input og output gennem "data streams"
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

    private int port = 6666;
    private static ArrayList<ClientHandler> clients;

    private ArrayList<GameRoomSession> listOfGameRoomSessions;

    //Server constructor
    public Server(){
        clients = new ArrayList<>();
        listOfGameRoomSessions = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
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

        }).start(); //start thread for server socket*/
    }



    public int createGameRoomSession(ClientHandler client){

        //create a new game room, and add it to servers list of game rooms
        GameRoomSession gameRoomSession = new GameRoomSession();

        //a new gameroom session task is created, put in a thread
        new Thread(gameRoomSession).start();

        //add the client to new game room
        gameRoomSession.addClientToRoom(client);
        //add to servers list of game rooms
        listOfGameRoomSessions.add(gameRoomSession);

        //return the index number
        return listOfGameRoomSessions.size() -1;

    }

    //add client to room based on index (if user presses button join)
    public void addClientToGameRoom(int gameRoomIndex, ClientHandler client){
        this.listOfGameRoomSessions.get(gameRoomIndex).addClientToRoom(client);
    }

    public void getListOfGameRooms(){
        for(GameRoomSession gameRoom : listOfGameRoomSessions){
            System.out.println(gameRoom);
        }
    }

    public GameRoomSession getAGameRoom(int gameRoomIndex){
        return this.listOfGameRoomSessions.get(gameRoomIndex);
    }


    //method composes a string that denotes number of users for each room index e.g. 0:1:1:2:1 ...
    public String getGameRoomListAndUsersAsString(){

        StringBuilder str = new StringBuilder();

        for(int i = 0; i < listOfGameRoomSessions.size(); i++){
            str.append(i + ":");
            str.append(getAGameRoom(i).getClients().size() + ":");
        }

        //remove last ":"
        if( str.length() > 0 )
            str.deleteCharAt( str.length() - 1 );

        return str.toString();
    }

}
