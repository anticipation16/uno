import model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.Color.*;

public class Game {
    private final int maxPlayers;
    private final List<Player> players = new ArrayList<>();
    private int current;
    private Pile drawPile;
    private Pile discardPile;
    private GameStatus status;
    private final Map<Player, CardSet> playerCardSetMap = new HashMap<>();
    private GameServer gameServer;

    public Game(int maxPlayers, GameServer gameServer) {
        this.maxPlayers = maxPlayers;
        current = 0;
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
            gameServer.messagePlayer("Your cards are: " + cardSet, player);
        }

    }


    public String processMove(Player p, String move) {
        if (status.equals(GameStatus.WAITING_FOR_PLAYERS))
            return "insufficient players";
        if (players.get(current) != p)
            return "Not your turn!";


        System.out.println(move);
        incrementCurrentPlayerIndex();
        return move;
    }

    private void incrementCurrentPlayerIndex() {
        current = (current + 1) % maxPlayers;
    }

    public void addPlayer(Player p) {
        if (players.size() < maxPlayers)
            players.add(p);
    }

    public List<Player> getPlayers() {
        return players;
    }


}
