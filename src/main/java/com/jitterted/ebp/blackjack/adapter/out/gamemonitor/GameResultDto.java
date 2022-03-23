package com.jitterted.ebp.blackjack.adapter.out.gamemonitor;

import com.jitterted.ebp.blackjack.domain.Game;

public class GameResultDto {
    private final String playerName;
    private final String outcome;
    private final String playerHandValue;
    private final String dealerHandValue;

    public static GameResultDto from(Game game) {
        return new GameResultDto("Ted",
                                 game.determineOutcome().display(),
                                 String.valueOf(game.playerHand().value()),
                                 String.valueOf(game.dealerHand().value()));
    }

    public GameResultDto(String playerName, String outcome, String playerHandValue, String dealerHandValue) {
        this.playerName = playerName;
        this.outcome = outcome;
        this.playerHandValue = playerHandValue;
        this.dealerHandValue = dealerHandValue;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getOutcome() {
        return outcome;
    }

    public String getPlayerHandValue() {
        return playerHandValue;
    }

    public String getDealerHandValue() {
        return dealerHandValue;
    }
}
