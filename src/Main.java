//Impoter biblioteker
import java.io.*; //Den sørger for at systemet kan lave input og output gennem "data streams"
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        try {
            //Opretter et server socket objekt med vores port/"dørnøgle"
            ServerSocket serverSocket = new ServerSocket(6666);

            //Etablerer forbindelsen til klienten
            Socket connectToClient = serverSocket.accept();

            DataInputStream inputFromClient = new DataInputStream(connectToClient.getInputStream());
            DataOutputStream outFromServer = new DataOutputStream(connectToClient.getOutputStream());

            String stringInput = (String)inputFromClient.readUTF();
            System.out.println("Brugernavn: " + stringInput + " er forbundet til server");

            outFromServer.writeUTF("Du har oprettet forbindelse til server");

            serverSocket.close();


        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
