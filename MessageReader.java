import java.io.BufferedReader;

public class MessageReader extends Thread {
    private final BufferedReader in;
    private boolean running;

    public MessageReader(BufferedReader in) {
        this.in = in;
        this.running = true;
    }

    @Override
    public void run() {
        try {
            String message;
            while (running && (message = in.readLine()) != null) {
                System.out.println(message);
                System.out.print("> ");
                if (message.equals("exit")) {
                    running = false;
                    break;
                }
            }
        } catch (Exception e) {
            if (running) {
                System.err.println("Error reading message: " + e.getMessage());
            }
        } finally {
            running = false;
        }
    }

    public void stopReading() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
