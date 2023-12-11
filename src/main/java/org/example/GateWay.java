package org.example;
import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintWriter;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.ArrayList;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

public class GateWay {
    static ArrayList<ClientHandler> users = new ArrayList<ClientHandler>();

    public static void main(String[] args) throws IOException {
        ServerSocket serversocket= new ServerSocket(5001);
        ExecutorService pool = Executors.newFixedThreadPool(4);
        while (true)
        {
            Socket socket = serversocket.accept();
            ClientHandler socket_service = new ClientHandler(socket);
            pool.execute(socket_service);
            GateWay.users.add(socket_service);
        }

    }

}

