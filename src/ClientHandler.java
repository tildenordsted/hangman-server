import com.hangman.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    //client socket, and input- output streams variables
    private Server server;
    private Socket clientSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ArrayList<ClientHandler> clients;
    private String userName;
    private int points;
    private int lives;
    private String guess;
    private int gameRoomIndex;
    private Message message = null;


    //constructor
    public ClientHandler(Socket clientSocket, Server server, ArrayList<ClientHandler> clients) throws IOException{
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.server = server;
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

                    //compose greeting message object
                    Message greetingMessage = new Message("Greetings, " + this.getUserName() + ". Join or Create a Hangman Game", "greeting");

                    //send greeting message object
                    objectOutputStream.writeObject(greetingMessage);
                    objectOutputStream.flush();

                    //flush the object stream TODO find ud af hvad flush gør og hvor det skal være
                    //objectOutputStream.flush();
                    Message updateLobby = new Message(server.getGameRoomListAndUsersAsString(), "updatelobby");

                    objectOutputStream.writeObject(updateLobby);
                    objectOutputStream.flush();


                } //end if message object is of type user

                //if type of message is username
                if(typeOfMessage.equalsIgnoreCase("join")){

                    //get the message content (gameroom index), and parse to an int
                    int gameRoomIndex = Integer.parseInt(message.getMessage());


                    //only add player to room, if theres room (max 3 players)
                    if(server.getAGameRoom(gameRoomIndex).getClients().size() < 3){
                        //set this clients gameroom index
                        setGameRoomIndex(gameRoomIndex);

                        //get a room by index and add client to it
                        server.getAGameRoom(gameRoomIndex).addClientToRoom(this);


                        //TODO: Send some string data to client to show in lobby
                        Message updateLobby = new Message(server.getGameRoomListAndUsersAsString(), "updatelobby");

                        System.out.println(server.getGameRoomListAndUsersAsString());
                        //update lobby message to all clients
                        writeToAllClients(updateLobby);

                    }

                }

                //if type of message is newgame
                if(typeOfMessage.equalsIgnoreCase("newgame")){

                    int gameRoomIndex = server.createGameRoomSession(this);

                    //set the gameroom index for this client
                    this.setGameRoomIndex(gameRoomIndex);

                    //send message to update lobby for all players
                    Message updateLobby = new Message(server.getGameRoomListAndUsersAsString(), "updatelobby");

                    System.out.println(updateLobby.getMessage());
                    writeToAllClients(updateLobby);

                    //TODO This newly created gameroom should be put in a thread and started


                }

                //if type of message is guess
                if(typeOfMessage.equalsIgnoreCase("guess")){

                    String guess = message.getMessage();
                    this.setGuess(guess);

                    System.out.println(this.getGuess());



                }


            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
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

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void writeMessageToUser(Message message) throws IOException{
        this.objectOutputStream.writeObject(message);
        this.objectOutputStream.flush();

    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void writeToAllClients(Message message) throws IOException {
        for(ClientHandler client : clients){
            client.objectOutputStream.writeObject(message);
            client.objectOutputStream.flush();

        }
    }

    //set gameroom index number (when user create a gameroom)
    public void setGameRoomIndex(int gameRoomIndex){
        this.gameRoomIndex = gameRoomIndex;
    }

    //get gameroom index
    public int getGameRoomIndex(){
        return this.gameRoomIndex;
    }

}

