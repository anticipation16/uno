import exceptions.IllegalCardStringException;
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
    private final Pile drawPile;
    private final Pile discardPile;
    private GameStatus status;
    private final Map<Player, CardSet> playerCardSetMap = new HashMap<>();
    private final GameServer gameServer;


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
        infoBroadcast();
    }

    /**
     * Process move of the move [1stLetterOfColor]:Number or [1stLetterOfColor]:Speciality
     *
     * @param player the player who made the move
     * @param move   the move made by the player p
     * @throws IllegalMoveException when an illegal move is made
     */


    public void processMove(Player player, String move) throws IllegalMoveException {
        if (status.equals(GameStatus.WAITING_FOR_PLAYERS))
            throw new IllegalMoveException("Insufficient players!");
        if (players.get(currentPlayerIndex) != player)
            throw new IllegalMoveException("Not your turn!");

        try {
            Card playedCard = CardUtility.stringRepToCard(move);
            CardSet playerCardSet = playerCardSetMap.get(player);
            if (!playerCardSet.contains(playedCard)) {
                throw new IllegalMoveException("You do not have this card!");
            }

            Card drawPileTop = drawPile.peekTopCard();

            if(drawPileTop instanceof ColoredCard && playedCard instanceof ColoredCard){
                if(!((ColoredCard) drawPileTop).getColor().equals( ((ColoredCard) playedCard).getColor())){
                    throw new IllegalMoveException("Your card color does not match top card on pile!");
                }
            }

            playerCardSet.remove(playedCard);
            discardPile.addCard(drawPile.popTopCard());

        } catch (IllegalCardStringException e) {
            throw new IllegalMoveException("Illegal Move String Played: " + e.getMessage());
        }

        String info = " made the move " + move;
        gameServer.broadcastMessage1ToOthersMessage2ToPlayer(
                player.getName() + info,
                "You " + info,
                player);
        incrementCurrentPlayerIndex();
    }


    private void infoBroadcast() {
        Player currentPlayer = getCurrentPlayer();
        gameServer.broadcast("Top card on draw pile is " + drawPile.peekTopCard());
        gameServer.broadcastMessage1ToOthersMessage2ToPlayer(
                currentPlayer.getName() + "'s turn",
                "Your turn",
                currentPlayer
        );
    }

    private void incrementCurrentPlayerIndex() {
        currentPlayerIndex = (currentPlayerIndex + 1) % maxPlayers;
        infoBroadcast();
    }

    public void addPlayer(Player p) {
        if (players.size() < maxPlayers)
            players.add(p);
    }

    public List<Player> getPlayers() {
        return players;
    }


}
