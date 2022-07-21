package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.application.port.GameMonitor;

public class Game {

    private Long id;
    private final Deck deck;

    private final Hand dealerHand;
    private final Hand playerHand;
    private GameMonitor gameMonitor;
    private boolean playerDone;

    public Game(Deck deck) {
        this(deck, new Hand(), new Hand(), false);
    }

    public Game(Deck deck, GameMonitor gameMonitor) {
        this(deck, new Hand(), new Hand(), false);
        this.gameMonitor = gameMonitor;
    }

    public Game(Deck deck, Hand playerHand, Hand dealerHand, boolean isPlayerDone) {
        this.deck = deck;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        playerDone = isPlayerDone;
        this.gameMonitor = game -> {};
    }


    private void dealRoundOfCards() {
        // why: players first because this is the rule of Blackjack
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public GameOutcome determineOutcome() {
        // PROTOCOL: throw exception if game isn't over
        if (playerHand.isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (dealerHand.isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand.hasBlackjack()) {
            return GameOutcome.PLAYER_WINS_BLACKJACK;
        } else if (playerHand.beats(dealerHand)) {
            return GameOutcome.PLAYER_BEATS_DEALER;
        } else if (playerHand.pushes(dealerHand)) {
            return GameOutcome.PLAYER_PUSHES;
        } else {
            return GameOutcome.PLAYER_LOSES;
        }
    }

    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (value of hand: <=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    // "Query" Rule: SNAPSHOT (point in time), does not allow
    // clients to change internal state (immutable/unmodifiable/copy)
    // 0 - Hand - is mutable and not snapshot --> *NO*
    // 1 - Deep Copy of Hand - deep clone() --> *NO* misleads clients to think they can change it
    // 2 - DTO - cards (first card), hand's value --> pure data, "JavaBean", only lives in Adapters
    // 3 - Interface - exposes just the cards & value --> only queries of Hand: ReadOnlyHand,
    //           be careful that the interface isn't a "view" on the Hand that can change (*NO*)
    // 4 - Hand Value Object ("HandView") - cards, hand's value --> Domain, often just data, domain-meaningful methods
    // 5 - Subclass that throws exception for command methods --> *NO* not nice to take away commands
    public Hand playerHand() {
        return playerHand;
    }

    public Hand dealerHand() {
        return dealerHand;
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
        updatePlayerDoneTo(playerHand.hasBlackjack());
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        updatePlayerDoneTo(playerHand.isBusted());
    }

    public void playerStands() {
        dealerTurn();
        updatePlayerDoneTo(true);
    }

    public Deck deck() {
        return deck;
    }

    private void updatePlayerDoneTo(boolean playerIsDone) {
        if (playerIsDone) {
            playerDone = true;
            gameMonitor.roundCompleted(this);
        }
    }

    /*
    gameService.currentPlayerStands() {
        // repo.findGame()
       game.playerStands();
       if (game.isPlayerDone()) {
         monitor....
       }
       repo.save(game)
     }
     */

    public boolean isPlayerDone() {
        return playerDone;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
