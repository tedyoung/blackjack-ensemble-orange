package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleGame;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;

public class Blackjack {

    // Assembling & Configuring objects (bootstrap or initialize)
    // Transient
    public static void main(String[] args) {
        Game game = new Game(new Deck()); // Entity-like object
        ConsoleGame consoleGame = new ConsoleGame(game); // in general: Entities aren't directly passed in to Adapters
        consoleGame.start();
    }

}
