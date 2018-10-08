package com.risk.services;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.risk.model.Country;
import com.risk.model.Continent;

public class MapValidate {


    private HashMap<String, Continent> continentSetOfContinents;

	private HashMap<String, Continent> continentSetOfTerritories;

    private HashMap<String, Country> countrySet;

    private HashMap<Country, ArrayList<Country>> adjacentCountries;

    private HashMap<Continent, HashSet<Country>> countriesInContinent;

    private String fileName;

    private ArrayList<String> mapTagData;

    public MapValidate() {
        this.continentSetOfContinents = new HashMap<>();
        this.continentSetOfTerritories = new HashMap<>();
        this.adjacentCountries = new HashMap<>();
        this.countriesInContinent = new HashMap<>();
        this.mapTagData = new ArrayList<>();
        this.countrySet = new HashMap<>();
    }
    
    public HashMap<String, Continent> getContinentSetOfTerritories() {
		return continentSetOfTerritories;
	}

	public void setContinentSetOfTerritories(HashMap<String, Continent> continentSetOfTerritories) {
		this.continentSetOfTerritories = continentSetOfTerritories;
	}
	
    public HashMap<String, Continent> getContinentSetOfContinents() {
        return continentSetOfContinents;
    }

    public HashMap<Country, ArrayList<Country>> getAdjacentCountries() {
        return adjacentCountries;
    }

    public HashMap<Continent, HashSet<Country>> getCountriesInContinent() {
        return countriesInContinent;
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<String> getMapTagData() {
        return mapTagData;
    }

    public boolean validateMapFile(String mapFile) {
        this.fileName = mapFile;

        if (mapFile != null) {
            try (BufferedReader read = new BufferedReader(new FileReader(mapFile))) {
                String inputText = new String(Files.readAllBytes(Paths.get(mapFile)), StandardCharsets.UTF_8);
                if (!checkAllTags(inputText)) {
                    System.out.println("Missing tags or wrong tags.");
                    return false;
                }

                for (String line; (line = read.readLine()) != null; ) {
                    if (line.trim().equals("[Map]")) {
                        while (!((line = read.readLine()).equals("[Continents]"))) {
                            line = read.readLine();
                            if (!line.trim().isEmpty() && !(line.contains("="))) {
                                System.out.print("Invalid map configuration");
                                return false;
                            }
                            mapTagData.add(line);
                        }
                    }
                    if (line.equals("[Continents]")) {
                        while (!((line = read.readLine()).equals("[Territories]"))) {
                            Pattern pattern = Pattern.compile("[a-z, A-Z]+=[0-9]+");
                            if (!line.trim().isEmpty()) {
                                Matcher match = pattern.matcher(line.trim());
                                if (!match.matches()) {
                                    if (line.trim().equals("[Territories]")) {
                                        break;
                                    }
                                    System.out.print("Invalid continent configuration");
                                    return false;
                                }

                                if (continentSetOfContinents.containsKey(line.split("=")[0])) {
                                    System.out.println("Continent is already defined.");
                                    return false;
                                }
                                Continent continent = new Continent(line.split("=")[0], Integer.parseInt(line.split("=")[1]));
                                continentSetOfContinents.put(continent.getName(), continent);
                            }
                        }
                    }
                    if (line.equals("[Territories]")) {
                        while ((line = read.readLine()) != null) {
                            if (!line.trim().isEmpty()) {
                                String input[] = line.split(",");
                                if (continentSetOfTerritories.get(input[3].trim()) == null) {
                                    Continent continent = new Continent(input[3].trim(), 0);
                                    continent.setControlValue(continentSetOfContinents.get(input[3].trim()).getControlValue());
                                    continentSetOfTerritories.put(continent.getName(), continent);
                                }
                                Country country = null;
                                if (!countriesInContinent.keySet().contains(continentSetOfTerritories.get(input[3]))) {
                                    HashSet<Country> countries = new HashSet<>();
                                    if (!countrySet.containsKey(input[0].trim())) {
                                        country = new Country(input[0].trim());
                                        country.setContinent(continentSetOfTerritories.get(input[3]).getName());
                                        country.setxValue(input[1]);
                                        country.setyValue(input[2]);
                                        countrySet.put(input[0].trim(), country);
                                    } else {
                                        country = countrySet.get(input[0].trim());
                                        country.setContinent(continentSetOfTerritories.get(input[3]).getName());
                                        country.setxValue(input[1]);
                                        country.setyValue(input[2]);
                                    }
                                    country.setPartOfContinent(continentSetOfTerritories.get(input[3]));
                                    countries.add(country);
                                    if(!continentSetOfTerritories.get(input[3]).getListOfCountries().contains(country)) {
                                        continentSetOfTerritories.get(input[3]).getListOfCountries().add(country);
                                    }
                                    countriesInContinent.put(continentSetOfTerritories.get(input[3]), countries);
                                } else {
                                    HashSet<Country> countries = getCountriesFromContinent(input[3].trim(), countriesInContinent);
                                    if (!countrySet.containsKey(input[0].trim())) {
                                        country = new Country(input[0].trim());
                                        country.setxValue(input[1]);
                                        country.setyValue(input[2]);
                                        country.setContinent(continentSetOfTerritories.get(input[3]).getName());
                                        countrySet.put(input[0].trim(), country);
                                    } else {
                                        country = countrySet.get(input[0].trim());
                                        country.setContinent(continentSetOfTerritories.get(input[3]).getName());
                                        country.setxValue(input[1]);
                                        country.setyValue(input[2]);
                                    }
                                    country.setPartOfContinent(continentSetOfTerritories.get(input[3]));
                                    countries.add(country);
                                    if(!continentSetOfTerritories.get(input[3]).getListOfCountries().contains(country)) {
                                        continentSetOfTerritories.get(input[3]).getListOfCountries().add(country);
                                    }
                                    countriesInContinent.put(continentSetOfTerritories.get(input[3]), countries);
                                }
                                ArrayList<Country> countries = new ArrayList<>();
                                for (int i = 4; i < input.length; ++i) {
                                    Country adjacentCountry;
                                    if (!countrySet.containsKey(input[i].trim())) {
                                        adjacentCountry = new Country(input[i].trim());
                                        countrySet.put(input[i].trim(), adjacentCountry);
                                    } else {
                                        adjacentCountry = countrySet.get(input[i].trim());
                                    }
                                    countries.add(adjacentCountry);
                                }
                                country.setAdjacentCountries(countries);
                                adjacentCountries.put(country, countries);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            for (Map.Entry<Continent, HashSet<Country>> countries : countriesInContinent.entrySet()) {
                if (countries.getValue().size() < 1) {
                    System.out.println(countries);
                    System.out.println("Number of countries in a continent is less");
                    return false;
                }
            }

            for (Map.Entry<Country, ArrayList<Country>> countries : adjacentCountries.entrySet()) {
                Country checkCountry = countries.getKey();
                ArrayList<Country> neighbours = countries.getValue();
                for (Country adjacent : neighbours) {
                    if (!adjacentCountries.get(adjacent).contains(checkCountry)) {
                        System.out.println("Countries are not adjacent");
                        return false;
                    }
                }
            }
        }
//        ArrayList<Country> arr = new ArrayList<>();
//        for (Map.Entry<String, Country> entry : countrySet.entrySet()) {
//
//            System.out.print(entry.getValue().getName() + " " + entry.getValue().getContinent() + " " + entry.getValue().getxValue() + " " + entry.getValue().getyValue());
//            arr = entry.getValue().getAdjacentCountries();
//            break;
//
//
//        }
////        System.out.println(arr);
//        for (Country c : arr) {
//            System.out.println(c.getName());
//        }

        if (continentSetOfContinents.size() != continentSetOfTerritories.size()) {
            System.out.println("Number of continents defined in continents tag does not match " +
                    " with the continents defined in the territories tag");
            return false;
        }

        return true;
    }

    public HashSet<Country> getCountriesFromContinent(String continent, HashMap<Continent, HashSet<Country>> hm) {
        for (Map.Entry<Continent, HashSet<Country>> pair : hm.entrySet()) {
            if (pair.getKey().getName().equals(continent)) {
                return pair.getValue();
            }
        }
        return null;
    }

    public boolean checkAllTags(String fileData) {
        if (!fileData.contains("[Map]") || countOccurrences(fileData, "[Map]") != 1) {
            return false;
        } else if (!fileData.contains("[Continents]") || countOccurrences(fileData, "[Continents]") != 1) {
            return false;
        } else if (!fileData.contains("[Territories]") || countOccurrences(fileData, "[Territories]") != 1) {
            return false;
        }

        return true;
    }

    public int countOccurrences(String input, String search) {
        int count = 0, startIndex = 0;
        Pattern pattern = Pattern.compile(search, Pattern.LITERAL);
        Matcher match = pattern.matcher(input);

        while (match.find(startIndex)) {
            count++;
            startIndex = match.start() + 1;
        }
        return count;
    }

    public HashMap<String, Country> getCountrySet() {
        return countrySet;
    }

    public void setContinentSetOfContinents(HashMap<String, Continent> continentSetOfContinents) {
        this.continentSetOfContinents = continentSetOfContinents;
    }

}
