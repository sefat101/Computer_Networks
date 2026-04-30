

# ✅ **1 (b) – Checking IP Address**

## 🔹 **Ubuntu Commands**

### 1.

```bash
ifconfig
```

### 🔍 What it does:

* Displays **network interface configuration**
* Shows:

  * IP address
  * MAC address
  * Netmask
  * RX/TX packets

### 📌 Example Output:

```bash
eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>
    inet 192.168.0.1  netmask 255.255.255.0
    ether 08:00:27:ab:cd:ef
```

### 🧠 Meaning:

* `eth0` → interface name
* `inet 192.168.0.1` → your IP
* `netmask` → subnet
* `ether` → MAC address

---

### 2.

```bash
ip addr
```

### 🔍 What it does:

* Modern replacement for `ifconfig`
* Shows **detailed network info**

### 📌 Example Output:

```bash
2: eth0: <BROADCAST,MULTICAST,UP>
    inet 192.168.0.1/24
```

### 🧠 Meaning:

* `/24` → subnet mask (255.255.255.0)
* `UP` → interface is active

---

## 🎯 **Main Purpose of 1(b):**

👉 To **verify IP configuration**

* Check if IP is assigned correctly
* Identify interface name
* Ensure network is active

---

# ✅ **1 (c) – Manual IP Configuration (Ubuntu)**

---

## 🔹 Command 1:

```bash
sudo ip addr add 192.168.0.1/24 dev eth0
```

### 🔍 What it does:

* Assigns a **static IP address** to interface

### 🧠 Breakdown:

* `sudo` → admin privilege required
* `ip addr add` → add IP
* `192.168.0.1/24` → IP + subnet
* `dev eth0` → target interface

### 📌 Response:

* Usually **no output if successful**
* If error:

  ```bash
  RTNETLINK answers: File exists
  ```

  → means IP already assigned

---

## 🔹 Command 2:

```bash
sudo ip link set eth0 up
```

### 🔍 What it does:

* Turns ON the network interface

### 🧠 Meaning:

* Like enabling network adapter
* Without this → no communication possible

### 📌 Response:

* No output (success)

---

## 🔹 Command 3:

```bash
ip addr show eth0
```

### 🔍 What it does:

* Displays current IP configuration

### 📌 Output:

```bash
inet 192.168.0.1/24
```

### 🧠 Meaning:

* Confirms IP is assigned

---

## 🔹 Command 4:

```bash
ping 192.168.0.2
```

### 🔍 What it does:

* Sends ICMP packets to test connectivity

### 📌 Successful Output:

```bash
64 bytes from 192.168.0.2: icmp_seq=1 ttl=64 time=0.3 ms
```

### 📌 Failure Output:

```bash
Destination Host Unreachable
```

---

## 🔹 Firewall Commands

### 1.

```bash
sudo ufw disable
```

### 🔍 What it does:

* Turns OFF firewall

### 📌 Output:

```bash
Firewall stopped and disabled
```

---

### 2.

```bash
sudo ufw allow icmp
```

### 🔍 What it does:

* Allows ping requests

---

### 3.

```bash
sudo ufw allow 7777
```

### 🔍 What it does:

* Opens port 7777 for server communication

---

## 🎯 **Main Purpose of 1(c):**

👉 To **establish network communication between two PCs**

* Assign static IPs
* Activate interfaces
* Test connectivity using ping
* Resolve firewall issues

---







SUMMARY : 

1. (b) ipconfig -> windows
	ifconfig -> ubuntu 
	
	
(c) In Linux ->

	i. 1st pc -> 
		$sudo ip addr add 192.168.0.1/24 dev [ethernet]
		$sudo ip link set [ethernet] up 
		
		check ip ->
			$ip addr show [ethernet]
	ii.2nd pc ->
		$sudo ip addr add 192.168.0.2/24 dev [ethernet]
		$sudo ip link set [ethernet] up 
	iii. ping 192.168.0.2 ->from pc 1
	
    In windows -> 
    
    	i.1st pc -> 
    		$netsh interface ip set address name="Ethernet" static 192.168.0.1 255.255.255.0
    	ii. 2nd pc ->
    		$netsh interface ip set address name+"Ethernet" static 192.168.0.2 255.255.255.0
    		
    	(*) if "Ethernet" name doesnt work ->
    		$netsh interface show interface 
    		
	(*)if fails ->
		i.turn off firewall ->
		    in windows -> netsh advfirewall set allprofiles state off
		    in ubuntu -> sudo ufw disable 
		    		 sudo ufw allow proto icmp -> if ping fails 
		    		 sudo ufw allow 5000 -> To allow java server port (example port 5000)

