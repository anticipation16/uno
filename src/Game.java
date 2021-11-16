import exceptions.IllegalMoveException;
import model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.Color.*;

public class Game {
    private final int maxPlayers;
    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex;
    private Pile drawPile;
    private Pile discardPile;
    private GameStatus status;
    private final Map<Player, CardSet> playerCardSetMap = new HashMap<>();
    private GameServer gameServer;


    public Game(int maxPlayers, GameServer gameServer) {
        this.maxPlayers = maxPlayers;
        currentPlayerIndex = 0;
        var yellowCards = CardUtility.getAllCardsOfColor(YELLOW);
        var greenCards = CardUtility.getAllCardsOfColor(GREEN);
        var blueCards = CardUtility.getAllCardsOfColor(BLUE);
        var redCards = CardUtility.getAllCardsOfColor(RED);

        List<Card> allCards = Stream.of(yellowCards, greenCards, blueCards, redCards)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        drawPile = new Pile(allCards);
        discardPile = new Pile();
        this.gameServer = gameServer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void start() {
        drawPile.shuffle();
        for (Player player : players) {
            CardSet cardSet = new CardSet();
            // give set of 7 cards to each player p
            for (int i = 0; i < 7; i++) {
                cardSet.add(drawPile.popTopCard());
            }
            playerCardSetMap.put(player, cardSet);
            gameServer.messagePlayer("Your cards are:\n" + cardSet, player);
        }
        gameServer.broadcast("Let the game begin");
        gameServer.broadcast("Players are : " + this.getPlayers());
        turnBroadcast();
    }


    public void processMove(Player p, String move) throws IllegalMoveException {
        if (status.equals(GameStatus.WAITING_FOR_PLAYERS))
            throw new IllegalMoveException("Insufficient players!");
        if (players.get(currentPlayerIndex) != p)
            throw new IllegalMoveException("Not your turn!");
        String info = " made the move " + move;
        gameServer.broadcastMessage1ToOthersMessage2ToPlayer(
                p.getName() + info,
                "You " + info,
                p);
        incrementCurrentPlayerIndex();
    }


    private void turnBroadcast() {
        Player currentPlayer = getCurrentPlayer();
        gameServer.broadcastMessage1ToOthersMessage2ToPlayer(
                currentPlayer.getName() + "'s turn",
                "Your turn",
                currentPlayer
        );
    }

    private void incrementCurrentPlayerIndex() {
        currentPlayerIndex = (currentPlayerIndex + 1) % maxPlayers;
        turnBroadcast();
    }

    public void addPlayer(Player p) {
        if (players.size() < maxPlayers)
            players.add(p);
    }

    public List<Player> getPlayers() {
        return players;
    }


}
