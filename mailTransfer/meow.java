import java.io.*;
import javax.net.ssl.*;
import java.util.Base64;
import java.net.NetworkInterface;
import java.util.Enumeration;

class Email {

    private static DataOutputStream dos;
    private static BufferedReader br;

    public static void main(String[] args) throws Exception {

        String user = "s2212076113@ru.ac.bd";
        String pass = "your_app_password_here"; // जरूरी: put Gmail app password

        // Encode credentials
        String username = Base64.getEncoder().encodeToString(user.getBytes());
        String password = Base64.getEncoder().encodeToString(pass.getBytes());

        // Get MAC address
        String macAddress = getMacAddress();

        // Create SSL connection
        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault()
                .createSocket("smtp.gmail.com", 465);

        dos = new DataOutputStream(socket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        readResponse();

        send("EHLO localhost");
        readMultiline();

        send("AUTH LOGIN");
        readResponse();

        send(username);
        readResponse();

        send(password);
        readResponse();

        send("MAIL FROM:<" + user + ">");
        readResponse();

        // Recipients
        send("RCPT TO:<s2212176122@ru.ac.bd>"); // TO
        readResponse();

        send("RCPT TO:<s2212076115@ru.ac.bd>"); // CC
        readResponse();

        send("RCPT TO:<s2212576117@ru.ac.bd>"); // CC
        readResponse();

        send("RCPT TO:<s2212576141@ru.ac.bd>"); // BCC
        readResponse();

        send("RCPT TO:<s2112376130@ru.ac.bd>"); // BCC
        readResponse();

        send("DATA");
        readResponse();

        // Headers
        send("From: " + user);
        send("To: s2212176122@ru.ac.bd");
        send("Cc: s2212076115@ru.ac.bd, s2212576117@ru.ac.bd");
        send("Subject: Email with MAC Address");
        send("MIME-Version: 1.0");
        send("Content-Type: text/plain; charset=UTF-8");
        send("");

        // Body (with MAC)
        send("THIS IS A TEST EMAIL.");
        send("My MAC Address: " + macAddress);
        send("THANK YOU.");

        send(".");
        readResponse();

        send("QUIT");
        readResponse();

        socket.close();
    }

    // 🔹 Function to send data
    private static void send(String data) throws Exception {
        dos.writeBytes(data + "\r\n");
        dos.flush();
        System.out.println("CLIENT: " + data);
    }

    // 🔹 Read single line response
    private static void readResponse() throws Exception {
        String line = br.readLine();
        System.out.println("SERVER: " + line);
    }

    // 🔹 Read multiline response (EHLO)
    private static void readMultiline() throws Exception {
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("SERVER: " + line);
            if (line.startsWith("250 ")) break;
        }
    }

    // 🔹 Get MAC Address
    private static String getMacAddress() throws Exception {
        Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();

        while (networks.hasMoreElements()) {
            NetworkInterface network = networks.nextElement();
            byte[] mac = network.getHardwareAddress();

            if (mac != null && mac.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                return sb.toString();
            }
        }
        return "MAC NOT FOUND";
    }
}
