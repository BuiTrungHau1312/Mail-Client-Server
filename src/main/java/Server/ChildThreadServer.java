package Server;

import DAL.AuthDAL;
import DTO.Account;
import org.json.JSONObject;

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

                JSONObject res = new JSONObject(data);

//                switch (res.getString()) {
//
//                }
                String type = res.getString("type");
                switch (type) {
                    case "LOGIN":
                        JSONObject dataReceive = res.getJSONObject("data");
                        String username = dataReceive.getString("username");
                        String password = dataReceive.getString("password");
                        System.out.println(username + password);
                        AuthDAL auth = new AuthDAL();
                        Account account = auth.checkLogin(username, password);
                        if (account == null) {
                            sendDataToClient("tài khoản kh có trong Database");
                        } else {
                            sendDataToClient("tài khoản đã có trong Database");
                        }
                        break;
                    case "REGISTER":
                        sendDataToClient("register");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }

                System.out.println("RES: " + res.getString("type"));

                sendDataToClient("Server receive: "+data);
            } catch (IOException e) {
                e.printStackTrace();
                closeConnection();
                break;
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
