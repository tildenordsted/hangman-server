import com.hangman.message.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRoomSession implements Runnable{

    Boolean isRunning = true;
    Boolean showScore = true;

    //private ArrayList<ClientHandler> clients = new ArrayList<>();

    private List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<ClientHandler>());


    //constructor
    public GameRoomSession(){}

    //Getter and setters

    //add client to this room
    public void addClientToRoom(ClientHandler client){
        this.clients.add(client);
    }

    //get clients
    public List<ClientHandler> getClients(){
        return this.clients;
    }

    public void writeToAllInRoom(Message message) throws IOException {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.writeMessageToUser(message);
            }
        }
    }


    //get clients info in this room
    public String getClientListNamesAndLives() {
        StringBuilder strUsernamesAndLives = new StringBuilder();

        for(ClientHandler client : clients){
            //System.out.println(client.getUserName());
            strUsernamesAndLives.append(client.getUserName() + " " + client.getLives() + " lives" + "\n");
        }
        return strUsernamesAndLives.toString();
    }


    @Override
    public void run() {







    } //end run


}
