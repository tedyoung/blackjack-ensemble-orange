package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GameRepositoryJdbcAdapter implements GameRepository {

    private final GameJdbcRepository gameJdbcRepository;

    public GameRepositoryJdbcAdapter(GameJdbcRepository gameJdbcRepository) {
        this.gameJdbcRepository = gameJdbcRepository;
    }

    @Override
    public Game save(Game game) {
        GameDbo gameDbo = GameDbo.from(game);
        return gameJdbcRepository.save(gameDbo).toDomain();
    }

    @Override
    public Optional<Game> findById(long id) {
        Optional<GameDbo> gameById = gameJdbcRepository.findById(id);
        return gameById.map(GameDbo::toDomain);
    }
}
