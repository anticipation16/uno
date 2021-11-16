import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {
    private Socket socket;
    private final String hostName;
    private final int port;

    public GameClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public void execute() throws IOException {
        socket = new Socket(hostName, port);
        readFromServer();
        writeToServer();
    }

    private void readFromServer() {
        Runnable r = () -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(r).start();
    }

    private void writeToServer() {
        Runnable r = () -> {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // for reading input on terminal
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    out.println(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(r).start();
    }

    public static void main(String[] args) throws IOException {
        String hostName = args[0];
        int port = Integer.parseInt(args[1]);
        GameClient gameClient = new GameClient(hostName, port);
        gameClient.execute();
    }
}
