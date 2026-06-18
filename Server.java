import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int DEFAULT_PORT = 5000;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            System.out.println("Waiting for client...");

            try (Socket client = server.accept();
                 BufferedReader in = new BufferedReader(
                     new InputStreamReader(client.getInputStream())
                 );
                 PrintWriter out = new PrintWriter(
                     client.getOutputStream(),
                     true
                 );
                 BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in)
                 )) {

                System.out.println("Client connected!");
                System.out.println("Type your messages (type 'exit' to quit):\n");

                MessageReader reader = new MessageReader(in, "Client: ");
                reader.start();

                String userInput;
                while (reader.isRunning()) {
                    System.out.print("> ");
                    userInput = console.readLine();
                    if (userInput == null) {
                        break;
                    }
                    out.println(userInput);
                    if (userInput.equals("exit")) {
                        reader.stopReading();
                        break;
                    }
                }

                reader.join(1000);
                System.out.println("Connection closed.");
            }

        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}