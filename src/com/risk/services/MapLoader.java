package com.risk.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapLoader {

	private Map<String, Map<String, String>> mapFileContent;
	private Map<String, String> tagContent;
	private ArrayList<String> mapsDetail;
	private ArrayList<String> continentsDetail;
	private ArrayList<String> territoriesDetail;
	private String MAPS_COUNTRY_DELIMETER = "=";
	private String TERRITORY_DELIMETER = ","; 
	
	public MapLoader() {
		this.mapFileContent = new HashMap<String, Map<String, String>>();
		this.tagContent = new HashMap<String, String>();
		this.mapsDetail = new ArrayList<String>();
		this.continentsDetail = new ArrayList<String>();
		this.territoriesDetail = new ArrayList<String>();
	}

	public void validateAndLoadMapFile(String mapFile) {
		
		boolean mapFileValidated;
		MapValidate mapValidate = new MapValidate();
		mapFileValidated = mapValidate.validateMapFile(mapFile);
		File file = new File(mapFile);
		String mapFileLine;
		
		if (mapFileValidated) {
			try {
				BufferedReader read = new BufferedReader(new FileReader(file));

				while ((mapFileLine = read.readLine()) != null) {
					if (mapFileLine.equals("[Map]")) {
						while ((mapFileLine = read.readLine()) != null) {
							if (mapFileLine.equals("")) {
								break;
							}
							mapsDetail.add(0, mapFileLine.split(MAPS_COUNTRY_DELIMETER)[0]);
							mapsDetail.add(1, mapFileLine.split(MAPS_COUNTRY_DELIMETER)[1]);

							tagContent.put(mapsDetail.get(0), mapsDetail.get(1));
							mapsDetail.clear();
						}
						mapFileContent.put("Map", tagContent);
					} else if (mapFileLine.equals("[Continents]")) {
						while ((mapFileLine = read.readLine()) != null) {
							if (mapFileLine.equals("")) {
								break;
							}
							continentsDetail.add(mapFileLine.split(MAPS_COUNTRY_DELIMETER)[0]);
							continentsDetail.add(mapFileLine.split(MAPS_COUNTRY_DELIMETER)[1]);

							tagContent.put(continentsDetail.get(0), continentsDetail.get(1));
							continentsDetail.clear();
						}
						mapFileContent.put("Continents", tagContent);
					} else if (mapFileLine.equals("[Territories]")) {
						while ((mapFileLine = read.readLine()) != null) {
							if (mapFileLine.equals("")) {
								continue;
							}

							territoriesDetail.add(mapFileLine.substring(0, mapFileLine.indexOf(TERRITORY_DELIMETER)));
							territoriesDetail.add(mapFileLine.substring(mapFileLine.indexOf(TERRITORY_DELIMETER) + 1));

							tagContent.put(territoriesDetail.get(0), territoriesDetail.get(1));
							territoriesDetail.clear();
						}
						mapFileContent.put("Territories", tagContent);
					}

				}
				read.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
}
