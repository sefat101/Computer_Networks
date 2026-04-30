import java.io.*;
import javax.net.ssl.*;
import java.util.Base64;

class Email {

    private static DataOutputStream dos;
    private static BufferedReader br;

    public static void main(String[] args) throws Exception {

        String user = "YOUR_MAIL";
        String pass = "your_app_password_here"; 

        String username = Base64.getEncoder().encodeToString(user.getBytes());
        String password = Base64.getEncoder().encodeToString(pass.getBytes());

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

        // Recipients (TO + CC + BCC)
        send("RCPT TO:<RECEIVER_MAIL>"); // TO
        readResponse();

        send("RCPT TO:<CC_MAIL>"); // CC
        readResponse();

        send("RCPT TO:<CC_MAIL>"); // CC
        readResponse();

        send("RCPT TO:<BCC_MAIL>"); // BCC
        readResponse();

        send("RCPT TO:<BCC_MAIL>"); // BCC
        readResponse();

        send("DATA");
        readResponse();

        // Headers
        send("From: " + user);
        send("To: YOUR_MAIL");
        send("Cc: BCC_MAIL,BCC_MAIL");
        send("Subject: Email test");
        send("MIME-Version: 1.0");
        send("Content-Type: text/plain; charset=UTF-8");
        send(""); // blank line before body

        // Body
        send("THIS IS A TEST EMAIL. THANK YOU.");

        send("."); // end of DATA
        readResponse();

        send("QUIT");
        readResponse();

        socket.close();
    }

    private static void send(String data) throws Exception {
        dos.writeBytes(data + "\r\n");
        dos.flush();
        System.out.println("CLIENT: " + data);
    }

    private static void readResponse() throws Exception {
        String line = br.readLine();
        System.out.println("SERVER: " + line);
    }

    private static void readMultiline() throws Exception {
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("SERVER: " + line);
            if (line.startsWith("250 ")) break;
        }
    }
}
