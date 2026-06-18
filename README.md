# Terminal Chat Application

A multi-client chat application with SQLite database for message persistence.

## Features

- Multiple clients can connect simultaneously
- Real-time message broadcasting
- Message history stored in SQLite database
- New clients see last 50 messages when they join
- Server can broadcast messages to all clients

## Requirements

- Java 8 or higher
- SQLite JDBC Driver
- SLF4J (logging library)

## Setup

### 1. Download Required Libraries

**Option A: Use the download script (easiest)**
```bash
./download-driver.sh
```

**Option B: Download manually**

Download these JAR files:
- SQLite JDBC: https://github.com/xerial/sqlite-jdbc/releases/download/3.45.0.0/sqlite-jdbc-3.45.0.0.jar
- SLF4J API: https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar
- SLF4J Simple: https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar

Place all JAR files in the same directory as your Java files.

### 2. Compile

**Linux/Mac:**
```bash
./compile.sh
```

**Windows:**
```bash
compile.bat
```

### 3. Run

**Linux/Mac:**
```bash
# Start the server
./run-server.sh

# Start clients (in separate terminals)
./run-client.sh
```

**Windows:**
```bash
# Start the server
run-server.bat

# Start clients (in separate terminals)
run-client.bat
```

---

<details>
<summary>Advanced: Manual compile/run commands (if you don't want to use scripts)</summary>

**Compile (Linux/Mac):**
```bash
javac -cp ".:sqlite-jdbc-3.45.0.0.jar:slf4j-api-2.0.9.jar:slf4j-simple-2.0.9.jar" *.java
```

**Compile (Windows):**
```bash
javac -cp ".;sqlite-jdbc-3.45.0.0.jar;slf4j-api-2.0.9.jar;slf4j-simple-2.0.9.jar" *.java
```

**Run Server (Linux/Mac):**
```bash
java -cp ".:sqlite-jdbc-3.45.0.0.jar:slf4j-api-2.0.9.jar:slf4j-simple-2.0.9.jar" Server
```

**Run Client (Linux/Mac):**
```bash
java -cp ".:sqlite-jdbc-3.45.0.0.jar:slf4j-api-2.0.9.jar:slf4j-simple-2.0.9.jar" Client
```

**Run Server (Windows):**
```bash
java -cp ".;sqlite-jdbc-3.45.0.0.jar;slf4j-api-2.0.9.jar;slf4j-simple-2.0.9.jar" Server
```

**Run Client (Windows):**
```bash
java -cp ".;sqlite-jdbc-3.45.0.0.jar;slf4j-api-2.0.9.jar;slf4j-simple-2.0.9.jar" Client
```

</details>

## Usage

1. Start the server first
2. Connect multiple clients from different terminals
3. Type messages and press Enter to send
4. New clients will see the last 50 messages from history
5. Type `exit` to disconnect
6. Server admin can type messages to broadcast to all clients
7. Type `exit` in server console to shutdown

## Database

The application creates a `chat.db` SQLite database file with two tables:

- **users**: Stores connected usernames
- **messages**: Stores all chat messages with timestamps

The database persists across server restarts, so message history is always available.

## Files

### Java Source Files
- `Server.java` - Main server handling multiple clients
- `Client.java` - Client application
- `ClientHandler.java` - Handles individual client connections
- `MessageReader.java` - Thread for reading messages
- `DatabaseManager.java` - SQLite database operations

### Helper Scripts
- `download-driver.sh` - Downloads required JAR files
- `compile.sh` / `compile.bat` - Compiles all Java files
- `run-server.sh` / `run-server.bat` - Runs the server
- `run-client.sh` / `run-client.bat` - Runs the client

### Generated Files
- `chat.db` - SQLite database (created automatically)
- `*.class` - Compiled Java bytecode
