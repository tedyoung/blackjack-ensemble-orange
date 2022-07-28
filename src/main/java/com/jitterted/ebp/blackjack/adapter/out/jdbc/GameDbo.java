package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Hand;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("games")
public class GameDbo {

    @Id
    private Long id;
    private String deck;
    private String dealerHand;
    private String playerHand;
    private boolean isPlayerDone;

    public static GameDbo from(Game game) {
        GameDbo gameDbo = new GameDbo();
        gameDbo.setId(game.getId());
        gameDbo.setDealerHand(Encoder.encode(game.dealerHand()));
        gameDbo.setPlayerHand(Encoder.encode(game.playerHand()));
        gameDbo.setPlayerDone(game.isPlayerDone());
        gameDbo.setDeck(Encoder.encode(game.deck()));
        return gameDbo;
    }

    public Game toDomain() {
        Deck decodedDeck = new Deck(CardDecoder.decodeCards(deck));
        Hand decodedPlayerHand = new Hand(CardDecoder.decodeCards(playerHand));
        Hand decodedDealerHand = new Hand(CardDecoder.decodeCards(dealerHand));
        return new Game(id, decodedDeck, decodedPlayerHand, decodedDealerHand, isPlayerDone);
    }

    public void setPlayerDone(boolean isPlayerDone) {
        this.isPlayerDone = isPlayerDone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(String dealerHand) {
        this.dealerHand = dealerHand;
    }

    public String getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(String playerHand) {
        this.playerHand = playerHand;
    }

    public boolean isPlayerDone() {
        return isPlayerDone;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public String getDeck() {
        return deck;
    }
}
