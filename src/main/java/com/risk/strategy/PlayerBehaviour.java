package com.risk.strategy;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.services.MapIO;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public abstract class PlayerBehaviour extends Observable implements Serializable {

    abstract public TextArea getTerminalWindow();

    abstract public void reinforcementPhase(ObservableList<Country> countryList, Country country, TextArea terminalWindow,
                                            Player currentPlayer);


    abstract public void attackPhase(ListView<Country> attackingCountryList, ListView<Country> defendingCountryList,
                                     Player currentPlayer, TextArea terminalWindow);


    abstract public boolean fortificationPhase(ListView<Country> selectedCountry, ListView<Country> adjCountry,
                                               TextArea terminalWindow, Player playerPlaying);


    public List<Country> getDefendingCountryList(Country attackingCountry) {
        List<Country> defendingCountries = attackingCountry.getAdjacentCountries().stream()
                .filter(t -> (attackingCountry.getPlayer() != t.getPlayer())).collect(Collectors.toList());

        return defendingCountries;

    }

    abstract public boolean playerCanAttack(ListView<Country> countries, TextArea terminalWindow);


    public boolean isFortificationPhaseValid(MapIO mapIO, Player playerPlaying) {
        boolean isFortificationAvailable = false;
        outer:
        for (Continent continent : mapIO.getMapGraph().getContinents().values()) {
            for (Country Country : continent.getListOfCountries()) {
                if (Country.getPlayer().equals(playerPlaying)) {
                    if (Country.getNoOfArmies() > 1) {
                        for (Country adjCountry : Country.getAdjacentCountries()) {
                            if (adjCountry.getPlayer().equals(playerPlaying)) {
                                isFortificationAvailable = true;
                                break outer;
                            }
                        }
                    }
                }
            }
        }

        return isFortificationAvailable;
    }


}
