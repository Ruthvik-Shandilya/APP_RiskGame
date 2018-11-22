package com.risk.services;

import com.risk.model.Card;
import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for Dice.
 * 
 * @author Neha Pal
 * @author Palash
 *
 */
public class StartUpPhaseTest {

	/** Object for StartUpPhase Class */
    private StartUpPhase startUpPhase;

    /** Object for Continent Class */
    private Continent continent;

    /** Object for Country Class */
    private Country country1;

    /** Object for Country CLass */
    private Country country2;

    /** Object for MapIO Class */
    private MapIO map;

    /** Object for Player Class */
    private Player player;

    /** The @fxPanel */
    @FXML
    private JFXPanel jfxPanel;

    /** The @textArea */
    @FXML
    private TextArea textArea;

    /** ArrayList to hold list of continents */
    private ArrayList<Continent> listOfContinents;

    /** ArrayList to hold list of Countries */
    private ArrayList<Country> listOfCountries;

    /** ArrayList to hold list of Players */
    private ArrayList<Player> listOfPlayers;

    /**
	 * Set up the initial objects for StartUpPhaseTest
	 */
    @Before
    public void initialize(){
        startUpPhase = new StartUpPhase();
        continent = new Continent("Asia",4);

        country1 = new Country("India");
        country1.setPartOfContinent(continent);

        country2 = new Country("Bangladesh");
        country2.setPartOfContinent(continent);

        country2.getAdjacentCountries().add(country2);
        country1.setAdjacentCountries(country2.getAdjacentCountries());
        country1.getAdjacentCountries().add(country1);
        country2.setAdjacentCountries(country1.getAdjacentCountries());

        map = new MapIO();
        player = new Player("Karan","TestPlayer");

        listOfContinents= new ArrayList<>();
        listOfContinents.add(continent);

        listOfCountries= new ArrayList<>();
        listOfCountries.add(country1);

        listOfPlayers= new ArrayList<>();
        player.setMyCountries(listOfCountries);
        player.setArmyCount(45);
        listOfPlayers.add(player);
        jfxPanel=new JFXPanel();
        textArea=new TextArea();
    }

    /**
     * Test for checking assignment of cards to a Country
     */
    @Test
    public void assignCardToCountryTest(){
        assertNotNull(startUpPhase.assignCardToCountry(map,textArea));
    }

    /**
     * Test for checking assignment of country to a player
     */
    @Test
    public void assignCountryToPlayerTest(){
        assertNotNull(startUpPhase.assignCountryToPlayer(map,listOfPlayers,textArea));
    }
}
