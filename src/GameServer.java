import exceptions.IllegalMoveException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameServer {
    private int port;
    private final Set<PlayerThread> playerThreads = new HashSet<>();
    private final Map<Player, PlayerThread> playerPlayerThreadMap = new HashMap<>();
    private Game game;
    private final int maxPlayers;

    public GameServer(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

    public void execute() throws IOException {
        var serverSocket = new ServerSocket(port);
        game = new Game(maxPlayers, this);
        game.setStatus(GameStatus.WAITING_FOR_PLAYERS);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            PlayerThread playerThread = new PlayerThread(clientSocket, this);
            playerThreads.add(playerThread);
            playerThread.start();
        }
        //broadcast(, null);
    }


    public void messagePlayer(String message, Player player) {
        playerPlayerThreadMap.get(player).sendMessage(message);
    }

    public void broadcast(String message) {
        playerPlayerThreadMap.forEach((k, v) -> {
                v.sendMessage(message);
        });
    }

    public void broadcastMessage1ToOthersMessage2ToPlayer(String message1, String message2, Player excluded){
        playerPlayerThreadMap.forEach((k, v) -> {
            if (k != excluded)
                v.sendMessage(message1);
            else
                v.sendMessage(message2);
        });
    }

    public void processMove(PlayerThread playerThread, String move) throws IllegalMoveException {
        game.processMove(playerThread.getPlayer(), move);
    }

    public void addPlayer(Player player, PlayerThread playerThread) {
        playerPlayerThreadMap.put(player, playerThread);
        game.addPlayer(player);
        if (playerThreads.size() == maxPlayers) {
            game.setStatus(GameStatus.IN_PROGRESS);
            game.start();
        }
    }


    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        int maxPlayers = Integer.parseInt(args[1]);
        GameServer g = new GameServer(port, maxPlayers);
        g.execute();
    }
}
