package client;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class represents an UNO Game client that sends a request to the game server through its main method.
 */
public final class GameClient {
    private Socket socket;
    private final String hostName;
    private final int port;
    private volatile boolean isDisconnected;
    private final Scanner clientInputReader = new Scanner(System.in);


    public GameClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public void execute() throws IOException {
        socket = new Socket(hostName, port);
        isDisconnected = false;
        readFromServer();
        writeToServer();
    }

    private void readFromServer() {
        Runnable r = () -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputLine;
                while (!(in.readLine() == null) && !Objects.equals(inputLine = in.readLine(), "close")) {
                    System.out.println(inputLine);
                }
                //TODO: Remove
                isDisconnected = true;
                in.close();
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
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
                String inputLine;
                while (!isDisconnected) {
                    while(clientInputReader.hasNextLine()){
                        inputLine = clientInputReader.nextLine();
                        out.println(inputLine);
                    }
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        var t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    /**
     * @param args <br>
     *             {@code args[0]}: The IP address of the host (server) to which request is to be sent.<br/>
     *             {@code args[1]}: The port number of the server socket to request to.
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            String hostName = args[0];
            int port = Integer.parseInt(args[1]);
            GameClient gameClient = new GameClient(hostName, port);
            gameClient.execute();
        } else {
            System.out.println("Execute with 1st argument as server IP and second argument as server port");
        }

    }
}
