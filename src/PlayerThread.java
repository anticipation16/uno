import exceptions.IllegalMoveException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerThread extends Thread {
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
            gameServer.addPlayer(player, this);

            String clientMove;
            while ((clientMove = in.readLine()) != null) {
                try {
                    gameServer.processMove(this, clientMove);
                } catch (IllegalMoveException e) {
                    out.println(e.getMessage());
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
