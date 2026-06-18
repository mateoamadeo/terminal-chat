import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int DEFAULT_PORT = 5000;
    private static final List<ClientHandler> clients = new ArrayList<>();
    private static volatile boolean running = true;
    private static int clientCounter = 0;
    private static DatabaseManager db;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        // Initialize database
        db = new DatabaseManager();
        int msgCount = db.getMessageCount();
        System.out.println("Loaded " + msgCount + " messages from history.\n");

        // Thread for accepting client connections
        Thread acceptThread = new Thread(() -> {
            try (ServerSocket server = new ServerSocket(port)) {
                System.out.println("Server started on port " + port);
                System.out.println("Waiting for clients to connect...");
                System.out.println("Type 'exit' to shutdown server.\n");

                while (running) {
                    try {
                        Socket clientSocket = server.accept();
                        ClientHandler handler = new ClientHandler(clientSocket, clients, ++clientCounter, db);

                        synchronized (clients) {
                            clients.add(handler);
                        }

                        handler.start();
                    } catch (Exception e) {
                        if (running) {
                            System.err.println("Error accepting client: " + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Server error: " + e.getMessage());
                e.printStackTrace();
            }
        });

        acceptThread.start();

        try (BufferedReader console = new BufferedReader(
                new InputStreamReader(System.in)
             )) {

            String input;
            while (running && (input = console.readLine()) != null) {
                if (input.equals("exit")) {
                    System.out.println("Shutting down server...");
                    running = false;
                    break;
                }

                if (!input.trim().isEmpty()) {
                    broadcastFromServer("Server", input);
                }
            }

            synchronized (clients) {
                for (ClientHandler client : clients) {
                    client.shutdown();
                }
                clients.clear();
            }

            acceptThread.join(2000);
            db.close();
            System.out.println("Server stopped.");

        } catch (Exception e) {
            System.err.println("Console error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void broadcastFromServer(String username, String message) {
        String fullMessage = username + ": " + message;
        System.out.println(fullMessage);

        // Save to database
        db.saveMessage(username, message);

        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendMessage(fullMessage);
            }
        }
    }
}