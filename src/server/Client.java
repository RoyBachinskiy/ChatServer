package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable{
    Socket socket;
    ChatServer server;
    int id;

    Scanner in;
    PrintStream out;
    public Client(Socket socket, ChatServer server, int id){
        this.socket = socket;
        this.server = server;
        this.id = id;
        new Thread(this).start();
    }

    void receive(String message){
        out.println(message);
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            in = new Scanner(is);
            out = new PrintStream(os);

            out.println("Welcome to Chat Server! I'm chat " + this.id + ".");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sendAll("Chat " + this.id + ": " + input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
