package com.risk.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Card.
 * 
 * @author Farhan Shaheen
 *
 */

public class CardTest {

    private Card card;

    private ArrayList<Card> listOfCards;


    @Before
    public void initialize() {

        card = new Card();
        listOfCards = new ArrayList<>();
    }

    @Test
    public void checkExchangeForDiffCards(){
        listOfCards.add(new Card(ICardType.INFANTRY));
        listOfCards.add(new Card(ICardType.ARTILLERY));
        listOfCards.add(new Card(ICardType.CAVALRY));

        assertEquals(true,card.checkTradePossible(listOfCards));
        }
    @Test
    public void checkExchangeForSameCards(){
        listOfCards.add(new Card(ICardType.CAVALRY));
        listOfCards.add(new Card(ICardType.CAVALRY));
        listOfCards.add(new Card(ICardType.CAVALRY));

        assertEquals(true,card.checkTradePossible(listOfCards));
        listOfCards.clear();

        listOfCards.add(new Card(ICardType.INFANTRY));
        listOfCards.add(new Card(ICardType.INFANTRY));
        listOfCards.add(new Card(ICardType.INFANTRY));
        assertEquals(true,card.checkTradePossible(listOfCards));
        listOfCards.clear();

        listOfCards.add(new Card(ICardType.ARTILLERY));
        listOfCards.add(new Card(ICardType.ARTILLERY));
        listOfCards.add(new Card(ICardType.ARTILLERY));
        assertEquals(true,card.checkTradePossible(listOfCards));
        listOfCards.clear();
        }
    }
