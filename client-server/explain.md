

---

# 🧠 1. What This Program Really Is

You are implementing a **Client–Server Architecture using TCP sockets in Java**.

* **Client (PC1)** → sends request
* **Server (PC2)** → processes and replies

This is based on the concept of:

👉 Transmission Control Protocol

**Key property of TCP:**

* Reliable
* Connection-oriented
* Ordered data delivery

---

# 🔌 2. Socket Programming Concept (Core Idea)

Think of a socket like a **pipe between two computers**:

```
Client  ======= (Socket Connection) =======  Server
```

* Client connects using **IP + Port**
* Server listens on a **Port**

---

# ⚙️ 3. Step-by-Step Execution Flow

## 🔹 Server Side Flow

1. Start server
2. Wait for client
3. Accept connection
4. Read request
5. Process request
6. Send response
7. Repeat

---

## 🔹 Client Side Flow

1. Connect to server
2. Take user input
3. Send request
4. Wait for reply
5. Print response
6. Repeat

---

# 🧩 4. Deep Code Explanation (Server)

---

## ✅ `ServerSocket ss = new ServerSocket(7777);`

* Opens **port 7777**
* Server starts listening

👉 Think: “I am ready, clients can connect to me”

---

## ✅ `Socket s = ss.accept();`

* **Blocking call** (VERY IMPORTANT)
* Server pauses until a client connects

👉 When client connects → returns a `Socket` object

---

## ✅ Streams Creation

```java
DataInputStream dis = new DataInputStream(s.getInputStream());
DataOutputStream dos = new DataOutputStream(s.getOutputStream());
```

### Why streams?

Because sockets only send **raw bytes**, so streams help convert:

* bytes → String (`readUTF`)
* String → bytes (`writeUTF`)

---

## 🔁 Main Loop

```java
while (true)
```

Keeps server alive for multiple requests
Without this → server handles only **one message**

---

## 📥 `String clientMsg = dis.readUTF();`

* Waits for client message
* Blocking again

👉 Server is always “listening”

---

## 🧠 Protocol Logic

### ✔ Case 1: Hi

```java
if (clientMsg.equalsIgnoreCase("Hi"))
    dos.writeUTF("Hello");
```

👉 Exact mapping from question

---

### ✔ Case 2: Date

```java
String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
```

* `new Date()` → current system date
* `SimpleDateFormat` → formats it nicely

---

### ✔ Case 3: Time

```java
HH:mm:ss
```

* 24-hour format

---

### ✔ Case 4: IP

```java
InetAddress.getLocalHost().getHostAddress();
```

👉 This matches your question instruction

It returns:

```
192.168.x.x
```

---

### ✔ Case 5: Exit

Breaks loop → closes connection

---

## ❌ Default Case

Handles invalid input:

```java
dos.writeUTF("Invalid Request");
```

---

## 🔚 Closing

```java
s.close();
ss.close();
```

Releases system resources

---

# 🧩 5. Deep Code Explanation (Client)

---

## ✅ `Socket s = new Socket("localhost", 7777);`

* Connects to server

👉 `"localhost"` = same machine
👉 In real lab:

```
"192.168.0.2"
```

---

## ✅ Streams (same as server)

Client also needs:

* input stream → to receive
* output stream → to send

---

## ✅ Scanner

```java
Scanner sc = new Scanner(System.in);
```

Takes user input dynamically

---

## 🔁 Loop

```java
while(true)
```

Allows continuous communication

---

## 📤 Sending Message

```java
dos.writeUTF(msg);
```

Encodes string → bytes → sends

---

## 📥 Receiving Response

```java
String response = dis.readUTF();
```

Waits for server reply

---

## 🛑 Exit Condition

```java
if (msg.equalsIgnoreCase("Exit"))
    break;
```

Stops client safely

---

# 🔄 6. Full Communication Example

### Step-by-step runtime:

```
Client: Hi
Server: Hello

Client: Date
Server: Server Date: 2026-04-30

Client: Time
Server: Server Time: 05:20:33

Client: IP
Server: Server IP: 192.168.0.2

Client: Exit
Server: Connection Closed
```

---

# ⚠️ 7. Important Concepts for Viva

---

## 🔹 1. Why TCP?

* Reliable
* No data loss
* Ordered packets

---

## 🔹 2. Why Port 7777?

* Arbitrary choice
* Must match on both sides

---

## 🔹 3. What is Blocking?

Functions like:

* `accept()`
* `readUTF()`

👉 Stop execution until data arrives

---

## 🔹 4. Why `while(true)`?

To support **multiple requests**
Otherwise → only one-time communication

---

## 🔹 5. Difference: ServerSocket vs Socket

| Class        | Purpose       |
| ------------ | ------------- |
| ServerSocket | Listens       |
| Socket       | Communication |

---

## 🔹 6. Why DataInputStream / OutputStream?

Because:

* Supports `readUTF()` / `writeUTF()`
* Easy string communication

---

# 🔥 8. Common Mistakes (VERY IMPORTANT)

* ❌ Not starting server first
* ❌ Port mismatch
* ❌ IP mismatch
* ❌ Forgetting loop
* ❌ Not handling Exit

---

