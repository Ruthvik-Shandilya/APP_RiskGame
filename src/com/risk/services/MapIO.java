package com.risk.services;

import com.risk.model.Continent;
import com.risk.model.Country;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class MapIO {

    private HashMap<String, Continent> continents;

    private HashMap<Country, ArrayList<Country>> adjacentCountries;

    private HashMap<Continent, HashSet<Country>> countriesInContinent;

    private String fileName;

    private ArrayList<String> mapTagData;

    private String newFileName;

    private boolean isNewFile = false;

    private static final String COMMA_DELIMITER = ",";


    public MapIO(MapValidate map) {
        this.continents = map.getContinentSetOfContinents();
        this.adjacentCountries = map.getAdjacentCountries();
        this.countriesInContinent = map.getCountriesInContinent();
        this.fileName = map.getFileName();
        this.mapTagData = map.getMapTagData();
    }

    public MapIO(HashMap<String, Continent> continents, HashMap<Country, ArrayList<Country>> adjacentCountries,
                 HashMap<Continent, HashSet<Country>> countriesInContinent, String fileName,
                 ArrayList<String> mapTagData, String newFileName, boolean isNewFile) {
        this.continents = continents;
        this.adjacentCountries = adjacentCountries;
        this.countriesInContinent = countriesInContinent;
        this.fileName = fileName;
        this.mapTagData = mapTagData;
        this.newFileName = newFileName;
        this.isNewFile = isNewFile;
    }

    public MapIO readFile() {
        return this;
    }


    public MapIO writeToFile(boolean isNewFile) {
        File file;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.isNewFile) {
            file = new File(new File("").getAbsolutePath() + "\\" + "maps\\" + this.fileName + ".map");
        } else {
            file = new File(new File("").getAbsolutePath() + "\\" + "maps\\" + this.newFileName + ".map");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {

            stringBuilder.append("[Map]");
            for (String line : mapTagData) {
                stringBuilder.append(line);
            }
            stringBuilder.append("\n");


            stringBuilder.append("[Continents]");
            for (Map.Entry<String, Continent> continentEntry : continents.entrySet()) {
                stringBuilder.append(continentEntry.getValue() + "=" + continentEntry.getValue().getControlValue());
            }
            stringBuilder.append("\n");

            stringBuilder.append("[Territories]");
            for (Map.Entry<Continent, HashSet<Country>> continentHashSetEntry : countriesInContinent.entrySet()) {
                Continent continent = continentHashSetEntry.getKey();
                for (Country country : continentHashSetEntry.getValue()) {
                    String line = country.getName() + COMMA_DELIMITER + country.getxValue() + COMMA_DELIMITER + country.getyValue() +
                            continent.getName() ;
                    for(Country adjacentCountry: adjacentCountries.get(country)){
                        line += COMMA_DELIMITER + adjacentCountry.getName();
                    }
                    stringBuilder.append(line);
                }
            }
            writer.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}