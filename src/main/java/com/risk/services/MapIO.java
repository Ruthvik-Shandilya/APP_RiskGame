package com.risk.services;

import com.risk.model.Continent;
import com.risk.model.Country;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class MapIO {

	private String fileName;

	private ArrayList<String> mapTagData;

	private String newFileName;

	private static final String COMMA_DELIMITER = ",";

	private MapGraph mapGraph;
	
	public MapIO() {
		this.mapGraph = new MapGraph();
		this.mapTagData = new ArrayList<>();
	}

	public MapIO(MapValidate map) {
		this.mapGraph = new MapGraph();
		this.mapGraph.setContinents(map.getContinentSetOfTerritories());
		this.mapGraph.setAdjacentCountries(map.getAdjacentCountries());
		this.mapGraph.setCountriesInContinent(map.getCountriesInContinent());
		this.fileName = map.getFileName();
		this.mapTagData = map.getMapTagData();
		this.mapGraph.setCountrySet(map.getCountrySet());
		
	}

	public MapIO readFile() {
		return this;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public MapIO writeToFile(boolean isNewFile) {
		File file;
		StringBuilder stringBuilder = new StringBuilder();
		if (isNewFile) {
			file = new File(new File("").getAbsolutePath() + "\\src\\main\\maps\\" + this.fileName + ".map");
		} else {
			file = new File(new File("").getAbsolutePath() + "\\src\\main\\maps\\" + this.newFileName + ".map");
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {

			stringBuilder.append("[Map]\n");
			for (String line : mapTagData) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
			stringBuilder.append("\n");


			stringBuilder.append("[Continents]\n");
			for (Map.Entry<String, Continent> continentEntry : mapGraph.getContinents().entrySet()) {
				stringBuilder.append(continentEntry.getValue().getName() + "=" + continentEntry.getValue().getControlValue());
				stringBuilder.append("\n");
			}
			stringBuilder.append("\n");

			stringBuilder.append("[Territories]\n");
			for (Map.Entry<Country,ArrayList<Country>> adjacentCountriesEntry : mapGraph.getAdjacentCountries().entrySet()) {
				Country country = adjacentCountriesEntry.getKey();
				ArrayList<Country> neighbourList = adjacentCountriesEntry.getValue();
				String line = country.getName() + COMMA_DELIMITER + country.getxValue() + COMMA_DELIMITER + country.getyValue() + COMMA_DELIMITER +
						country.getContinent();
				for(Country adjacentCountry: neighbourList) {
					line += COMMA_DELIMITER + adjacentCountry.getName();
				}
				stringBuilder.append(line);
				
				stringBuilder.append("\n");
			}

			writer.write(stringBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public MapGraph getMapGraph() {
		return mapGraph;
	}

	public ArrayList<String> getMapTagData() {
		return mapTagData;
	}
	
}