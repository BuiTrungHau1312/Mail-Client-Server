import DTO.Account;
import DTO.Mail;
import DTO.RequestDTO;
import DTO.RequestType;
import org.json.JSONObject;
//
import java.io.*;
import java.net.InetAddress;
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
                System.out.println("Nhập username: ");
                String username = scanner.nextLine();
                System.out.println("Nhập password: ");
                String password = scanner.nextLine();
                account.setUsername(username).setPassword(password);
                Mail mail = new Mail(1, "hello", 0, 1);
                RequestDTO request = new RequestDTO().setRequestType(RequestType.LOGIN).setData(mail);
                sendDataToServer(request.toString());
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

//public class Client {
//    private InetAddress host;
//    private int port;
//
//    public Client(InetAddress host, int port) {
//        this.host = host;
//        this.port = port;
//    }
//
//    private void execute() throws IOException {
//        Scanner scan = new Scanner(System.in);
//        System.out.print("nhập tên của bạn: ");
//        String name = scan.nextLine();
//
//        Socket client = new Socket(host, port);
//        ReadClient read = new ReadClient(client);
//        read.start();
//        System.out.println("Hãy nhắn gì đó...");
//        WriteClient write = new WriteClient(client, name);
//        write.start();
//    }
//
//    public static void main(String[] args) throws IOException {
//        Client client = new Client(InetAddress.getLocalHost(), 5000);
//        client.execute();
//    }
//}
//
//class ReadClient extends Thread {
//    private Socket client;
//    public ReadClient(Socket client) {
//        this.client = client;
//    }
//
//    @Override
//    public void run() {
//        DataInputStream dis = null;
//        try {
//            dis = new DataInputStream(client.getInputStream());
//            while (true) {
//                String sms = dis.readUTF();
//                System.out.println(sms);
//            }
//        } catch (Exception e) {
//            try {
//                client.close();
//                dis.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//}
//
//class WriteClient extends Thread {
//    private Socket client;
//    private String name;
//
//    public WriteClient(Socket client, String name) {
//        this.client = client;
//        this.name = name;
//    }
//
//    @Override
//    public void run() {
//        DataOutputStream dos = null;
//        Scanner scan = null;
//        try {
//            dos = new DataOutputStream(client.getOutputStream());
//            scan = new Scanner(System.in);
//            while (true) {
//                String sms = scan.nextLine();
//                dos.writeUTF(name + ": " +sms);
//            }
//        } catch (Exception e) {
//            try {
//                client.close();
//                dos.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//}