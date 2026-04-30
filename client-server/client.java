import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 7777);

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command (Hi, Date, Time, IP, Exit): ");
            String msg = sc.nextLine();

            dos.writeUTF(msg);

            String response = dis.readUTF();
            System.out.println("Server: " + response);

            if (msg.equalsIgnoreCase("Exit")) {
                break;
            }
        }

        s.close();
        sc.close();
    }
}
