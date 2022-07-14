CREATE TABLE games
(
    id SERIAL NOT NULL PRIMARY KEY,
    deck TEXT NOT NULL,
    dealer_hand TEXT NOT NULL,
    player_hand TEXT NOT NULL,
    is_player_done BOOLEAN NOT NULL
);