

# 🔹 1. `main()` Function

```java
public static void main(String[] args) throws Exception
```

### Purpose:

* Entry point of the program

### Key Points:

* `String[] args` → command-line arguments
* `throws Exception` → avoids writing try-catch everywhere

---

# 🔹 2. Base64 Encoding Functions

## ✅ `Base64.getEncoder()`

```java
Base64.getEncoder()
```

* Returns a Base64 encoder object

---

## ✅ `encodeToString()`

```java
encodeToString(user.getBytes())
```

### What happens:

1. `user.getBytes()` → converts string → byte array
2. `encodeToString()` → converts bytes → Base64 string

### Why needed:

SMTP `AUTH LOGIN` requires credentials in Base64

---

## ✅ `getBytes()`

```java
user.getBytes()
```

* Converts string → raw bytes
* Required before encoding

---

# 🔹 3. SSL Socket Functions

## ✅ `SSLSocketFactory.getDefault()`

```java
SSLSocketFactory.getDefault()
```

* Returns default SSL socket factory
* Used to create secure sockets

---

## ✅ `createSocket(host, port)`

```java
.createSocket("smtp.gmail.com", 465)
```

### Purpose:

* Connects to SMTP server

### Parameters:

* `host` → server address
* `port` → 465 (SSL)

---

## ✅ `getOutputStream()`

```java
socket.getOutputStream()
```

* Returns output stream to send data

---

## ✅ `getInputStream()`

```java
socket.getInputStream()
```

* Returns input stream to receive data

---

## ✅ `close()`

```java
socket.close()
```

* Closes connection safely

---

# 🔹 4. I/O Stream Functions

## ✅ `DataOutputStream`

```java
new DataOutputStream(socket.getOutputStream())
```

* Used to send data to server

---

## ✅ `BufferedReader`

```java
new BufferedReader(new InputStreamReader(socket.getInputStream()))
```

### Why layered:

1. `InputStreamReader` → bytes → characters
2. `BufferedReader` → efficient reading (line by line)

---

## ✅ `InputStreamReader`

```java
new InputStreamReader(socket.getInputStream())
```

* Converts byte stream → character stream

---

# 🔹 5. Writing Data

## ✅ `writeBytes()`

```java
dos.writeBytes(data + "\r\n");
```

### Purpose:

* Sends text to server

### Important:

* `\r\n` = required line ending in SMTP

---

## ✅ `flush()`

```java
dos.flush();
```

* Forces data to be sent immediately
* Without this → data may stay in buffer

---

# 🔹 6. Reading Data

## ✅ `readLine()`

```java
br.readLine()
```

### Purpose:

* Reads one line from server

### Example:

```text
250 OK
```

---

# 🔹 7. Custom Function: `send()`

```java
private static void send(String data)
```

### Internally uses:

* `writeBytes()`
* `flush()`

### Purpose:

* Sends SMTP command
* Prints client message

---

# 🔹 8. Custom Function: `readResponse()`

```java
private static void readResponse()
```

### Internally uses:

* `readLine()`

### Purpose:

* Reads single server response

---

# 🔹 9. Custom Function: `readMultiline()`

```java
private static void readMultiline()
```

### Internally uses:

* `readLine()`
* `startsWith()`

---

## ✅ `startsWith()`

```java
line.startsWith("250 ")
```

### Purpose:

* Checks if response is last line

### Why:

SMTP multi-line responses look like:

```text
250-First line
250-Second line
250 Last line
```

👉 Only last line has space after code

---

# 🔹 10. String Concatenation

```java
"MAIL FROM:<" + user + ">"
```

* Combines strings dynamically

---

# 🔹 11. `System.out.println()`

```java
System.out.println("CLIENT: " + data);
```

### Purpose:

* Prints output to console
* Helps debugging

---

# 🔹 12. SMTP Commands (Sent via `send()`)

These are not Java functions but **protocol commands**:

| Command      | Purpose          |
| ------------ | ---------------- |
| `EHLO`       | Identify client  |
| `AUTH LOGIN` | Start login      |
| `MAIL FROM`  | Sender           |
| `RCPT TO`    | Recipient        |
| `DATA`       | Start email      |
| `.`          | End email        |
| `QUIT`       | Close connection |

---

# 🧠 How Everything Connects

### Flow of function usage:

1. `main()` starts
2. Encode → `getBytes()` + `encodeToString()`
3. Connect → `createSocket()`
4. Setup streams → `getInputStream()` / `getOutputStream()`
5. Send commands → `send()` → `writeBytes()`
6. Read replies → `readResponse()` → `readLine()`
7. End → `close()`

---
