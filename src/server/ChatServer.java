package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatServer {

    public static int indexClient = 1;
    ArrayList<Client> clients = new ArrayList<>();
    static ServerSocket serverSocket;

    ChatServer() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    void sendAll(String message){
        for (Client client: clients) {
            client.receive(message);
        }
    }
    public void run(){
        while (true) {
            System.out.println("Waiting...");
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                clients.add(new Client(socket, this, indexClient));
                indexClient++;
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}
