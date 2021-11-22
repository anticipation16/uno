package server;

import exceptions.IllegalCardStringException;
import exceptions.IllegalMoveException;
import model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The PlayerThread class extends {@code Thread} to associate the {@code model.Game} to a particular thread
 * and allow the {@code GameServer}'s simple socket to read from and write to the Player client.
 */
public final class PlayerThread extends Thread {
    private final Socket socket;
    private final GameServer gameServer;
    private PrintWriter out;
    private Player player;

    public PlayerThread(Socket s, GameServer g) {
        socket = s;
        gameServer = g;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("What's your name? ");
            String userName = in.readLine();
            player = new Player(userName);
            gameServer.processNewPlayer(player, this);

            String clientMove;
            while ((clientMove = in.readLine()) != null) {
                processClientMove(clientMove);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processClientMove(String clientMove) {
        try {
            gameServer.processMove(this, clientMove);
        } catch (IllegalMoveException | IllegalCardStringException | IOException e) {
            out.println(e.getMessage());
        }
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Sends the message to the client.
     * @param message The message to be sent to the game client
     */
    public void sendMessage(String message) {
        out.println(message);
    }

    /**
     * Sends the question to the client output stream.
     * @param question The question posed.
     * @return The response from the game client
     * @throws IOException
     */
    public String askQuestion(String question) throws IOException {
        out.println(question);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return in.readLine();
    }
}
