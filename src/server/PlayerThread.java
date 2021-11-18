package server;

import exceptions.IllegalCardStringException;
import exceptions.IllegalMoveException;
import model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            gameServer.addPlayer(player, this);

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

    public void sendMessage(String message) {
        out.println(message);
    }

    public String askQuestion(String question) throws IOException {
        out.println(question);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return in.readLine();
    }
}
