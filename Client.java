import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final int DEFAULT_PORT = 5000;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        try (Socket socket = new Socket("localhost", port);
             PrintWriter out = new PrintWriter(
                 socket.getOutputStream(),
                 true
             );
             BufferedReader in = new BufferedReader(
                 new InputStreamReader(socket.getInputStream())
             );
             BufferedReader console = new BufferedReader(
                 new InputStreamReader(System.in)
             )) {

            System.out.println("Connected to server!");
            System.out.println("Type your messages (type 'exit' to quit):\n");

            MessageReader reader = new MessageReader(in, "Server: ");
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
            socket.close();

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
