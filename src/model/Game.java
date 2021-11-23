package model;

import exceptions.IllegalCardStringException;
import exceptions.IllegalMoveException;
import interaction.MoveProcessor;
import server.GameServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static model.Color.*;
import static model.GameStatus.FINISHED;

/**
 * Models an UNO Game.
 */
public class Game {
    private final int maxPlayers;
    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex;
    private final Pile drawPile;
    private final Pile discardPile;
    private static final int INITIAL_CARDS_PER_USER = 7;
    private GameStatus status;
    private final GameServer gameServer;


    public Game(int maxPlayers, GameServer gameServer) {
        this.maxPlayers = maxPlayers;
        this.gameServer = gameServer;
        this.currentPlayerIndex = 0;

        var yellowCards = CardUtility.getAllCardsOfColorInUNODeck(YELLOW);
        var greenCards = CardUtility.getAllCardsOfColorInUNODeck(GREEN);
        var blueCards = CardUtility.getAllCardsOfColorInUNODeck(BLUE);
        var redCards = CardUtility.getAllCardsOfColorInUNODeck(RED);

        List<Card> allCards = Stream.of(yellowCards, greenCards, blueCards, redCards)
                .flatMap(Collection::stream).toList();

        drawPile = new Pile(allCards);
        discardPile = new Pile();

    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getMaxPlayers() {
        return maxPlayers;
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

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getNextPlayer() {
        return players.get((currentPlayerIndex + 1) % maxPlayers);
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void start() {

        drawPile.shuffle();

        for (var player : players) {
            var cardSet = new CardSet();
            for (int i = 0; i < INITIAL_CARDS_PER_USER; i++) cardSet.add(drawPile.popTopCard());
            player.setCardSet(cardSet);
            gameServer.messagePlayer("Your cards are:\n" + cardSet, player);
        }
        gameServer.broadcast("Let the game begin!");
        gameServer.broadcast("Players are : " + this.getPlayers());
        discardPile.addCard(drawPile.popTopCard());
        topDiscardedCardAndTurnBroadcast();
    }

    /**
     * Process move of the move [1stLetterOfColor]:Number or [1stLetterOfColor]:Speciality
     * Eg. A move of the form r9 indicates the player wants to play a red card numbered 9.
     * Also processed:
     * 1) view cards : to allow a player to view their card set,
     * 2) view cards -o : to allow a player to view number of cards with everyone else
     * 3) uno : to allow a player with 1 card to say UNO or
     * to allow a player with more than 1 cards make the above player draw 2 cards.
     *
     * @param player the player who made the move
     * @param move   the move made by the player p
     * @throws IllegalMoveException when an illegal move is made
     */
    public void processMove(Player player, String move)
            throws IllegalMoveException, IllegalCardStringException, IOException {
        move = move.toUpperCase();
        MoveProcessor.processMove(this, player, move);
        if (status.equals(FINISHED)) {
            gameServer.endConnections();
            return;
        }
        if (!move.contains("VIEW"))
            topDiscardedCardAndTurnBroadcast();
    }

    private void topDiscardedCardAndTurnBroadcast() {
        Player currentPlayer = getCurrentPlayer();
        gameServer.broadcast("\nTop card on discard pile is " + discardPile.peekTopCard());
        gameServer.broadcastMessage1ToOthersMessage2ToPlayer(
                currentPlayer.getName() + "'s turn",
                "\nYour turn",
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
