package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class CardEncoderTest {

    @ParameterizedTest
    @MethodSource("cardSupplier")
    void cardIsEncodedAsString(Suit suit, Rank rank, String expectedEncoding) throws Exception {
        Card card = new Card(rank, suit);

        String encodedCard = CardEncoder.encode(card);

        assertThat(encodedCard)
                .isEqualTo(expectedEncoding);
    }

    private static Stream<Arguments> cardSupplier() {
        return Stream.of(
                Arguments.of(Suit.HEARTS, Rank.ACE, "AH"),
                Arguments.of(Suit.HEARTS, Rank.TWO, "2H"),
                Arguments.of(Suit.HEARTS, Rank.THREE, "3H"),
                Arguments.of(Suit.HEARTS, Rank.FOUR, "4H"),
                Arguments.of(Suit.HEARTS, Rank.FIVE, "5H"),
                Arguments.of(Suit.HEARTS, Rank.SIX, "6H"),
                Arguments.of(Suit.HEARTS, Rank.SEVEN, "7H"),
                Arguments.of(Suit.HEARTS, Rank.EIGHT, "8H"),
                Arguments.of(Suit.HEARTS, Rank.NINE, "9H"),
                Arguments.of(Suit.HEARTS, Rank.TEN, "TH"),
                Arguments.of(Suit.HEARTS, Rank.JACK, "JH"),
                Arguments.of(Suit.HEARTS, Rank.QUEEN, "QH"),
                Arguments.of(Suit.HEARTS, Rank.KING, "KH"),
                Arguments.of(Suit.CLUBS, Rank.ACE, "AC"),
                Arguments.of(Suit.SPADES, Rank.SEVEN, "7S"),
                Arguments.of(Suit.DIAMONDS, Rank.KING, "KD")
        );
    }
}
