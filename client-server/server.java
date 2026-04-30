import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(7777);
        System.out.println("Server is running...");

        Socket s = ss.accept();
        System.out.println("Client connected!");

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        while (true) {

            String clientMsg = dis.readUTF();

            if (clientMsg.equalsIgnoreCase("Hi")) {
                dos.writeUTF("Hello");
            }

            else if (clientMsg.equalsIgnoreCase("Date")) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                dos.writeUTF("Server Date: " + date);
            }

            else if (clientMsg.equalsIgnoreCase("Time")) {
                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                dos.writeUTF("Server Time: " + time);
            }

            else if (clientMsg.equalsIgnoreCase("IP")) {
                String ip = InetAddress.getLocalHost().getHostAddress();
                dos.writeUTF("Server IP: " + ip);
            }

            else if (clientMsg.equalsIgnoreCase("Exit")) {
                dos.writeUTF("Connection Closed");
                break;
            }

            else {
                dos.writeUTF("Invalid Request");
            }
        }

        s.close();
        ss.close();
        System.out.println("Server stopped.");
    }
}
