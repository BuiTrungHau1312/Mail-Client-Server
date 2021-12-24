package Server;

import Controller.UserController;
import DAL.AuthDAL;
import DTO.Account;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ChildThreadServer implements Runnable {
    private final Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private boolean isLogin = false;
    private Account account;
    public static HashMap<String, Object> listMessage = new HashMap<String, Object>();

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
        while (true) {
            JSONObject rq = getRes();
//            System.out.println(rq);
            switch (rq.getString("type")){
                case "LOGIN":
                    account = new UserController().DoLogin(rq.getString("username"),rq.getString("password"));
                    if (account == null){
                        sendDataToClient(new JSONObject().put("type", "LOGIN").put("status", "ERROR").put("message", "khong tim thay tai khoan").toString());
                    } else {
                        sendDataToClient(new JSONObject().put("type", "LOGIN").put("status", "SUCCESS").put("data", account.toString()).toString());
                        account = null;
                    }
                    break;
                case "SENDMAIL":
                    listMessage.put(rq.getString("receiver"), rq);
                    for (Map.Entry<String, Object> item: listMessage.entrySet()) {

                        String s = new JSONObject()
                                .put("receiver", item.getKey())
                                .put("data", item.getValue())
                                .toString();
                        String encodedPassword = Base64.getEncoder().encodeToString(s.getBytes());
                        sendDataToClient(new JSONObject().put("encode", encodedPassword).toString());
                    }
                    break;
                case "REGISTER":
                    AuthDAL auth = new AuthDAL();
                    if (auth.checkExist(rq.getString("username")) == null) {
                        auth.Register(rq.getString("username"), rq.getString("password"));
                        sendDataToClient(new JSONObject().put("type", "REGISTER").put("status", "SUCCESS").toString());
                    } else {
                        sendDataToClient(new JSONObject().put("type", "REGISTER").put("status", "ERROR").toString());
                    }
                    break;
                default:
            }
        }
    }

    private JSONObject getRes() {
        try {
            String data = bufferedReader.readLine();
            return new JSONObject(data);
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject().put("type", "ERROR").put("messsage", e.getMessage());
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
