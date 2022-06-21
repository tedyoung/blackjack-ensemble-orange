package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Game;

public class GameDbo {

    private Long id;
    private String dealerHand;
    private String playerHand;

    public static GameDbo from(Game game) {
        GameDbo gameDbo = new GameDbo();
        gameDbo.setId(game.getId());
        gameDbo.setDealerHand(Encoder.encode(game.dealerHand()));
        gameDbo.setPlayerHand(Encoder.encode(game.playerHand()));
        return gameDbo;
    }

    private void setId(Long id) {
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
}
