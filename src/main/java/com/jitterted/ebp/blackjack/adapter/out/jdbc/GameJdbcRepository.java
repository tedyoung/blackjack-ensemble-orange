package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import org.springframework.data.repository.CrudRepository;

public interface GameJdbcRepository extends CrudRepository<GameDbo, Long> {
}
