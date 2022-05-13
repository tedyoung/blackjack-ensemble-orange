package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.application.GameService;
import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private final GameService gameService;

    @Autowired
    public BlackjackController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start-game")
    public String startGame() {
        Game game = gameService.startGame();
        gameService.initialDeal(game.getId());
        return redirectBasedOnGameState(game.getId());
    }

    @GetMapping("/game/{gameId}")
    public String gameView(Model model, @PathVariable Long gameId) {
        populateGameViewIn(model, gameId);
        return "blackjack";
    }

    @PostMapping("/hit/{gameId}")
    public String hitCommand(@PathVariable Long gameId) {
        gameService.playerHits(gameId);
        return redirectBasedOnGameState(gameId);
    }

    private String redirectBasedOnGameState(Long gameId) {
        if (gameService.isPlayerDone(gameId)) {
            return "redirect:/done/" + gameId;
        }
        return "redirect:/game/" + gameId;
    }

    @GetMapping("/done/{gameId}")
    public String doneView(Model model, @PathVariable Long gameId) {
        populateGameViewIn(model, gameId);
        model.addAttribute("outcome", gameService.gameOutcome(gameId).display());
        return "done";
    }

    @PostMapping("/stand/{gameId}")
    public String standCommand(@PathVariable Long gameId) {
        gameService.playerStands(gameId);
        return redirectBasedOnGameState(gameId);
    }

    private void populateGameViewIn(Model model, Long gameId) {
        GameView gameView = GameView.of(gameService.gameFor(gameId));
        model.addAttribute("gameView", gameView);
        model.addAttribute("gameId", gameId);
    }

}
