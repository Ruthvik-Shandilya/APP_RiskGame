package com.risk.services;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapValidate {

    private HashSet<String> continentSetOfContinents;

    private HashSet<String> continentSetOfTerritories;

    private HashMap<String,ArrayList<String>> adjacentCountries;

    private HashMap<String, HashSet<String>> countriesInContinent;

    public MapValidate(){
        this.continentSetOfContinents = new HashSet<>();
        this.continentSetOfTerritories = new HashSet<>();
        this.adjacentCountries = new HashMap<>();
        this.countriesInContinent = new HashMap<>();
    }

    public boolean validateMapFile(String mapFile){
        if(mapFile!=null){
            try(BufferedReader read = new BufferedReader(new FileReader(mapFile))) {
                String inputText = new String(Files.readAllBytes(Paths.get(mapFile)), StandardCharsets.UTF_8);
                if (!checkAllTags(inputText)) {
                    System.out.println("Missing tags or wrong tags.");
                    return false;
                }

                for(String line; (line = read.readLine()) != null; ) {
                    if (line.trim().equals("[Map]")) {
                        while (!((line = read.readLine()).equals("[Continents]"))) {
                            line = read.readLine();
                            if (!line.trim().isEmpty() && !(line.contains("="))) {
                                System.out.println("line=" + line);
                                System.out.print("Invalid map configuration");
                                return false;
                            }
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
                                if (continentSetOfContinents.contains(line.split("=")[0])) {
                                    System.out.println("Continent is already defined.");
                                    return false;
                                } else {
                                    continentSetOfContinents.add(line.split("=")[0]);
                                }
                            }

                        }
                    }
                    if (line.equals("[Territories]")) {
                        while ((line = read.readLine()) != null) {
                            if (!line.trim().isEmpty()) {
                                String input[] = line.split(",");
                                continentSetOfTerritories.add(input[3].trim());
                                if (!countriesInContinent.containsKey(input[3])) {
                                    HashSet<String> countries = new HashSet<>();
                                    countries.add(input[0].trim());
                                    countriesInContinent.put(input[3].trim(), countries);
                                } else {
                                    HashSet<String> countries = countriesInContinent.get(input[3].trim());
                                    countries.add(input[0].trim());
                                    countriesInContinent.put(input[3].trim(), countries);
                                }
                                ArrayList<String> countries = new ArrayList<>();
                                for (int i = 4; i < input.length; ++i) {
                                    countries.add(input[i].trim());
                                }
                                adjacentCountries.put(input[0].trim(), countries);

                            }
                        }
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }


        for(Map.Entry<String,HashSet<String>> countries: countriesInContinent.entrySet()){
            if(countries.getValue().size() < 1){
                System.out.println(countries);
                System.out.println("Number of countries in a continent is less");
                return false;
            }
        }

        for(Map.Entry<String,ArrayList<String>> countries: adjacentCountries.entrySet()){
            String checkCountry = countries.getKey();
            ArrayList<String> neighbours = countries.getValue();
            for(String adjacent : neighbours){
                if(!adjacentCountries.get(adjacent).contains(checkCountry)){
                    System.out.println("Countries are not adjacent");
                    return false;
                }
            }
        }
        }

        if(continentSetOfContinents.size()!=continentSetOfTerritories.size()){
           System.out.println("Number of continents defined in continents tag does not match " +
                    " with the continents defined in the territories tag");
            return false;
        }

        return true;
    }

    public boolean checkAllTags(String fileData){
        if(!fileData.contains("[Map]")|| countOccurrences(fileData,"[Map")!=1){
            return false;
        }

        else if(!fileData.contains("[Continents]")|| countOccurrences(fileData,"[Continents")!=1){
            return false;
        }

        else if(!fileData.contains("[Territories]")|| countOccurrences(fileData,"[Territories")!=1){
            return false;
        }

        return true;
    }

    public int countOccurrences(String input, String search){
        int count=0, startIndex=0;
        Pattern pattern = Pattern.compile(search,Pattern.LITERAL);
        Matcher match = pattern.matcher(input);

        while(match.find(startIndex)){
            count++;
            startIndex = match.start()+1;
        }
        return count;
    }

}
