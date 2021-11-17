import exceptions.IllegalCardStringException;
import exceptions.IllegalMoveException;
import model.*;

import java.io.IOException;
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
    private final GameServer gameServer;

    public GameServer getGameServer() {
        return gameServer;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public Pile getDrawPile() {
        return drawPile;
    }

    public Pile getDiscardPile() {
        return discardPile;
    }

    public GameStatus getStatus() {
        return status;
    }


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

    public Player getNextPlayer() {
        return players.get((currentPlayerIndex + 1) % maxPlayers);
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void start() {
        drawPile.shuffle();
        for (Player player : players) {
            CardSet cardSet = new CardSet();
            // give set of 7 cards to each player p
            for (int i = 0; i < 3; i++) {
                cardSet.add(drawPile.popTopCard());
            }
            player.setCardSet(cardSet);
            gameServer.messagePlayer("Your cards are:\n" + cardSet, player);
        }
        gameServer.broadcast("Let the game begin");
        gameServer.broadcast("Players are : " + this.getPlayers());
        // TODO - discard pile should be empty at first
        discardPile.addCard(drawPile.popTopCard());
        infoBroadcast();
    }

    /**
     * Process move of the move [1stLetterOfColor]:Number or [1stLetterOfColor]:Speciality
     *
     * @param player the player who made the move
     * @param move   the move made by the player p
     * @throws IllegalMoveException when an illegal move is made
     */


    public void processMove(Player player, String move)
            throws IllegalMoveException, IllegalCardStringException, IOException {
        move = move.toUpperCase();

        MoveProcessor.processMove(this, player, move);
        infoBroadcast();
    }


    private void infoBroadcast() {
        Player currentPlayer = getCurrentPlayer();
        gameServer.broadcast("Top card on discard pile is " + discardPile.peekTopCard());
        gameServer.broadcastMessage1ToOthersMessage2ToPlayer(
                currentPlayer.getName() + "'s turn",
                "Your turn",
                currentPlayer
        );
    }

    public void incrementCurrentPlayerIndex() {
        currentPlayerIndex = (currentPlayerIndex + 1) % maxPlayers;
    }

    public void addPlayer(Player p) {
        if (players.size() < maxPlayers)
            players.add(p);
    }

    public List<Player> getPlayers() {
        return players;
    }


}
