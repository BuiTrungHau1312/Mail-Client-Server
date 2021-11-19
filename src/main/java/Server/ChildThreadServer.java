package Server;

import java.io.*;
import java.net.Socket;

public class ChildThreadServer implements Runnable {
    private final Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public ChildThreadServer(Socket socket) {
        this.socket = socket;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                String data = bufferedReader.readLine();
                System.out.println(data);
                sendDataToClient("Server receive: "+data);
            } catch (IOException e) {
                e.printStackTrace();
                closeConnection();
            }
        }
    }

    private void sendDataToClient(String data) {
        try {
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }
}
