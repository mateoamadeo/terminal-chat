import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final List<ClientHandler> clients;
    private PrintWriter out;
    private String clientName;
    private volatile boolean running;

    public ClientHandler(Socket socket, List<ClientHandler> clients, int clientNumber) {
        this.socket = socket;
        this.clients = clients;
        this.clientName = "Client" + clientNumber;
        this.running = true;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
             )) {

            out = new PrintWriter(socket.getOutputStream(), true);

            broadcast(clientName + " joined the chat!", this);
            System.out.println(clientName + " connected from " + socket.getRemoteSocketAddress());

            String message;
            while (running && (message = in.readLine()) != null) {
                if (message.equals("exit")) {
                    break;
                }

                System.out.println(clientName + ": " + message);
                broadcast(clientName + ": " + message, this);
            }

        } catch (Exception e) {
            System.err.println("Error handling " + clientName + ": " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender && client.out != null) {
                    client.out.println(message);
                }
            }
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void cleanup() {
        running = false;
        synchronized (clients) {
            clients.remove(this);
        }
        broadcast(clientName + " left the chat.", this);
        System.out.println(clientName + " disconnected.");

        try {
            if (out != null) {
                out.close();
            }
            socket.close();
        } catch (Exception e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public void shutdown() {
        running = false;
        try {
            socket.close();
        } catch (Exception e) {
        }
    }
}
