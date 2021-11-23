package server;

import exceptions.IllegalCardStringException;
import exceptions.IllegalMoveException;
import model.Game;
import model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static model.GameStatus.IN_PROGRESS;
import static model.GameStatus.WAITING_FOR_PLAYERS;

/**
 * This class represents an UNO GameServer listening for a limited number of socket connections for an UNO Game.
 * It starts the game as soon as the maximum number of Players have successfully connected to the server.
 */
public final class GameServer {
    private final int port;
    private final Map<Player, PlayerThread> playerPlayerThreadMap = new HashMap<>();
    private Game game;
    private final int maxPlayers;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Starts the server socket listening for connections each spawning off a {@code PlayerThread}.
     * Creates the {@code Game} object associated with the server.
     * Sets the game status to {@code WAITING_FOR_PLAYERS}
     */
    public void execute() {
        try (var serverSocket = new ServerSocket(port)) {
            System.out.printf("Server socket listening for connections on port %d.\n", port);
            game = new Game(maxPlayers, this);
            game.setStatus(WAITING_FOR_PLAYERS);
            for (int i = 0; i < maxPlayers; i++) {
                Socket clientSocket = serverSocket.accept();
                System.out.printf("Client %d connected\n", i + 1);
                PlayerThread playerThread = new PlayerThread(clientSocket, this);
                playerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the player to the {@code Game} object started by the server
     * Starts the game and sets its status to {@code model.GameStatus.IN_Progress} if all players have joined
     *
     * @param player       The player to be added
     * @param playerThread The playerThread associated with the player
     */
    public void processNewPlayer(Player player, PlayerThread playerThread) {
        playerPlayerThreadMap.put(player, playerThread);
        game.addPlayer(player);
        if (playerPlayerThreadMap.size() == maxPlayers) {
            game.setStatus(IN_PROGRESS);
            game.start();
        }
    }

    /**
     * Broadcasts message to terminate player threads.
     */
    public void endConnections() {
        broadcast("close");
    }

    /**
     * Sends a message to the player.
     *
     * @param message The message to be transmitted to the player.
     * @param player  THe player receiving the message.
     */
    public void messagePlayer(String message, Player player) {
        playerPlayerThreadMap.get(player).sendMessage(message);
    }

    /**
     * Asks a question to the player through the associated thread.
     *
     * @param question The question to be asked
     * @param player   The player to ask
     * @return The player's response
     * @throws IOException In case of error processing input
     */
    public String askPlayer(String question, Player player) throws IOException {
        return playerPlayerThreadMap.get(player).askQuestion(question);
    }

    /**
     * Sends a message to all the Players of the associated Game.
     *
     * @param message The message to broadcast
     */
    public void broadcast(String message) {
        playerPlayerThreadMap.forEach((k, v) -> v.sendMessage(message));
    }

    /**
     * Sends a message to all the Players of the associated Game except one Player.
     *
     * @param message  The message to transmit
     * @param excluded The excluded Player
     */
    public void broadcastToAllExcept(String message, Player excluded) {
        playerPlayerThreadMap.forEach((k, v) -> {
            if (k != excluded)
                v.sendMessage(message);
        });
    }

    /**
     * Sends a message to all the Players of the associated Game except one Player.
     *
     * @param message1 The message to transmit to all players except the {@code other} player
     * @param message2 The message to transmit to the {@code other} player
     * @param other    The Player receiving {@code message2}
     */
    public void broadcastMessage1ToOthersMessage2ToPlayer(String message1, String message2, Player other) {
        playerPlayerThreadMap.forEach((k, v) -> {
            if (k != other)
                v.sendMessage(message1);
            else
                v.sendMessage(message2);
        });
    }

    /**
     * @param playerThread The thread sending the move from the player
     * @param move         The player's move
     * @throws IllegalMoveException       If move is invalid according to game rules
     * @throws IllegalCardStringException If an invalid string is sent
     * @throws IOException                If there's error processing input/output
     */
    public void processMove(PlayerThread playerThread, String move) throws IllegalMoveException, IllegalCardStringException, IOException {
        game.processMove(playerThread.getPlayer(), move);
    }

    /**
     * Starts the server.
     *
     * @param args <br/>
     *             args[0] - The port to start the server socket on <br/>
     *             args[1] - The maximum number of players allowed in the game
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            int port = Integer.parseInt(args[0]);
            int maxPlayers = Integer.parseInt(args[1]);
            GameServer g = new GameServer(port, maxPlayers);
            g.execute();
        } else {
            System.out.println(
                    "Execute with 1st argument as port number for the server" +
                            " and 2nd argument as player limit for the game."
            );
        }

    }
}
