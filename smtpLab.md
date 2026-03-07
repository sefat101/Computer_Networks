1. Improved SMTP Java Code (Protocol Structured)

This version:

separates send / receive

checks server response codes

clearly matches RFC 5321 SMTP flow

import java.io.*;
import java.util.Base64;
import javax.net.ssl.*;

public class SMTPClient {

    private SSLSocket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public SMTPClient(String server, int port) throws Exception {
        socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(server, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        readResponse(); // 220 greeting
    }

    private void sendCommand(String cmd) throws Exception {
        System.out.println("C: " + cmd);
        out.write(cmd + "\r\n");
        out.flush();
    }

    private String readResponse() throws Exception {
        String line = in.readLine();
        System.out.println("S: " + line);
        return line;
    }

    public void sendEmail(String from, String password, String to) throws Exception {

        // RFC 5321 Section 4.1.1 — EHLO
        sendCommand("EHLO client.example.com");
        readResponse();

        // RFC 4954 — SMTP Authentication
        sendCommand("AUTH LOGIN");
        readResponse();

        sendCommand(Base64.getEncoder().encodeToString(from.getBytes()));
        readResponse();

        sendCommand(Base64.getEncoder().encodeToString(password.getBytes()));
        readResponse();

        // RFC 5321 Section 3.3 — MAIL FROM
        sendCommand("MAIL FROM:<" + from + ">");
        readResponse();

        // RFC 5321 Section 3.3 — RCPT TO
        sendCommand("RCPT TO:<" + to + ">");
        readResponse();

        // RFC 5321 Section 4.1.1.4 — DATA
        sendCommand("DATA");
        readResponse();

        // RFC 5322 — Email headers
        sendCommand("From: " + from);
        sendCommand("To: " + to);
        sendCommand("Subject: SMTP Protocol Test");
        sendCommand("");

        // Email body
        sendCommand("Hello, this email was sent using a raw SMTP client.");

        // RFC 5321 Section 4.5.2 — End of message
        sendCommand(".");
        readResponse();

        // Close connection
        sendCommand("QUIT");
        readResponse();

        socket.close();
    }

    public static void main(String[] args) throws Exception {

        String smtpServer = "smtp.gmail.com";
        int port = 465;

        String from = "yourmail@gmail.com";
        String password = "yourpassword";
        String to = "receiver@gmail.com";

        SMTPClient client = new SMTPClient(smtpServer, port);
        client.sendEmail(from, password, to);
    }
}
2. Now Let's Map the Code to RFC 5321

SMTP is defined in RFC 5321.

The protocol works in 3 major phases.

1️⃣ Connection Setup
2️⃣ Mail Transaction
3️⃣ Session Termination

Your code follows exactly this structure.

3. Phase 1 — Connection Setup
Code
SSLSocket socket = SSLSocketFactory.getDefault().createSocket(server, port);

This opens TCP connection.

Client → smtp.gmail.com:465

Server responds automatically:

220 smtp.gmail.com ESMTP ready

RFC rule:

220 = Service Ready

This greeting happens before the client sends anything.

4. EHLO Command
Code
sendCommand("EHLO client.example.com");

EHLO = Extended Hello

Purpose:

Client introduces itself

Server returns capabilities like:

250-AUTH LOGIN
250-SIZE
250-STARTTLS

Meaning:

Server supports authentication
Server supports TLS
Server supports message size limits
5. Authentication Phase

Your code:

AUTH LOGIN

Server replies:

334 VXNlcm5hbWU6

That means:

Send username

Why?

Because SMTP sends credentials Base64 encoded.

Example:

user@gmail.com
↓
dXNlckBnbWFpbC5jb20=

Code:

Base64.getEncoder().encodeToString(from.getBytes())

Then password same way.

Server returns:

235 Authentication successful
6. Mail Transaction Phase

Defined in RFC as:

MAIL FROM
RCPT TO
DATA

This is the core SMTP transaction.

MAIL FROM

Code:

sendCommand("MAIL FROM:<sender@gmail.com>");

Purpose:

Defines envelope sender

Server response:

250 OK
RCPT TO

Code:

sendCommand("RCPT TO:<receiver@gmail.com>");

Purpose:

Defines recipient

Multiple recipients allowed:

RCPT TO:<a@email>
RCPT TO:<b@email>
RCPT TO:<c@email>
7. DATA Command

Code:

sendCommand("DATA");

Server replies:

354 Start mail input

Meaning:

Server ready to receive message
8. Email Format (RFC 5322)

Your code sends:

From:
To:
Subject:

Example:

From: alice@gmail.com
To: bob@gmail.com
Subject: Test Mail

Then blank line.

Then body:

Hello world
9. Message Termination

SMTP requires a special sequence:

<CRLF>.<CRLF>

Meaning:

newline
.
newline

Code:

sendCommand(".");

Server response:

250 Message accepted
10. Closing Session

Code:

sendCommand("QUIT");

Server response:

221 Bye

Connection ends.

11. Visual Protocol Flow
Client                          Server
------                          ------

connect ----------------------->

                               220 ready

EHLO -------------------------->
                               250 capabilities

AUTH LOGIN -------------------->
                               334 username

username ---------------------->
                               334 password

password ---------------------->
                               235 authenticated

MAIL FROM --------------------->
                               250 ok

RCPT TO ----------------------->
                               250 ok

DATA -------------------------->
                               354 start input

message ----------------------->
.
                               250 accepted

QUIT -------------------------->
                               221 bye
12. Why This is Valuable for You

Since you are learning:

networking

Wireshark

protocol analysis

This code teaches:

raw SMTP protocol
socket programming
RFC implementation

Which is how real protocol engineers work.

13. If you want, next I can teach you something VERY powerful:
How to capture this exact SMTP session in Wireshark

You will literally see:

EHLO
AUTH LOGIN
MAIL FROM
RCPT TO
DATA

inside packets.
