import Server.ChildThreadServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main {
    private static final int PORT = 6003;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for client ...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.toString()+" connected!");
                executorService.execute(new ChildThreadServer(socket));//Nếu có client kết nối thì tạo 1 luồng chạy sv
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
