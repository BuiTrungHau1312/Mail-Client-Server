import DTO.Account;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private static Socket mainSocket;
    private static Account account = new Account();


    public static void main(String[] args) throws IOException {
        mainSocket = new Socket(HOST,PORT);
        bufferedReader = new BufferedReader(new InputStreamReader(mainSocket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(mainSocket.getOutputStream()));

        Scanner scanner = new Scanner(System.in);
        Thread mainThead = new Thread(() -> {
            while (true){
                String username = scanner.nextLine();
                String password = scanner.nextLine();
                account.setUsername(username).setPassword(password);
                sendDataToServer(new JSONObject(account).toString());
                receiveDataFromServer();
            }
        });
        Thread mailListener = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendDataToServer(new JSONObject(account).put("checkmail","ádasd").toString());
                receiveDataFromServer();
            }
        });

        mainThead.start();
        mailListener.start();

    }

    private static void receiveDataFromServer() {
        try {
            String data = bufferedReader.readLine();
            System.out.println("Client receive: "+ data);
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    private static void sendDataToServer(String data) {
        try {
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    private static void closeConnection() {
        try {
            bufferedWriter.close();
            bufferedReader.close();
            mainSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
