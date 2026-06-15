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

            String userInput;
            while (true) {
                System.out.print("Client: ");
                userInput = console.readLine();
                out.println(userInput);

                if (userInput.equals("exit")) {
                    break;
                }

                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                System.out.println("Server: " + response);

                if (response.equals("exit")) {
                    break;
                }
            }

            System.out.println("Connection closed.");

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
