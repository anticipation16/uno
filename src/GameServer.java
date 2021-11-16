import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class GameServer {
    private int port;
    private final HashSet<PlayerThread> playerThreads = new HashSet<>();
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
        for (PlayerThread p : playerThreads) {
            if (p.getPlayer() == player)
                p.sendMessage(message);
        }
    }

    public void broadcast(String message, PlayerThread excluded) {
        for (PlayerThread p : playerThreads) {
            if (p != excluded)
                p.sendMessage(message);
        }
    }

    public String processMove(PlayerThread playerThread, String move) {
        return game.processMove(playerThread.getPlayer(), move);

    }

    public void addPlayer(Player p) {
        game.addPlayer(p);
        if (playerThreads.size() == maxPlayers) {
            game.setStatus(GameStatus.IN_PROGRESS);
            game.start();
            broadcast("Let the game begin", null);
            broadcast("Players are : " + game.getPlayers(), null);
        }
    }


    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        int maxPlayers = Integer.parseInt(args[1]);
        GameServer g = new GameServer(port, maxPlayers);
        g.execute();
    }
}
