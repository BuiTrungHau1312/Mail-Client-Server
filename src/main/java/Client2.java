//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class Client2 {
//    private InetAddress host;
//    private int port;
//
//    public Client2(InetAddress host, int port) {
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
//        Client2 client2 = new Client2(InetAddress.getLocalHost(), 5000);
//        client2.execute();
//    }
//}
//
//class ReadClient2 extends Thread {
//    private Socket client;
//    public ReadClient2(Socket client) {
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
//class WriteClient2 extends Thread {
//    private Socket client;
//    private String name;
//
//    public WriteClient2(Socket client, String name) {
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