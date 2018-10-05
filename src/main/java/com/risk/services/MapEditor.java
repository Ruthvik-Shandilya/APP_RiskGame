package com.risk.services;

import com.risk.model.*;

import java.util.*;

public class MapEditor {

	public void createNewMap() {

		int numOfContinents;
		HashSet<Continent> continentSet = new HashSet<Continent>();
		HashSet<Country> countrySet = new HashSet<Country>();

		// For now I dont need this
		// HashMap<Continent, HashSet<Country>> countriesincontinent = new
		// HashMap<Continent, HashSet<Country>>();

		int numberofcountry = 0;
		Continent bufferContinent;
		Country temporaryCountry;
		String nameofcontinent;
		boolean addmorecountries = true;

		Scanner scan = new Scanner(System.in);
		System.out.println("Please provide the following details");

		System.out.println("Please enter name of the author");
		String author = "Author = " + scan.nextLine().trim();

		System.out.println("Please specify warn is yes or no");
		String warn = "Warn = " + scan.nextLine();

		System.out.println("Please specify the image for the map");
		String image = "Image = " + scan.nextLine();

		System.out.println("Please specify wrap is yes or no");
		String wrap = "Wrap = " + scan.nextLine();

		System.out.println("Please specify scroll is horizontal or vertical");
		String scroll = "Scroll = " + scan.nextLine();

		System.out.println("Please provide the number of continents you want to create (only an integer)");
		try {
			numOfContinents = Integer.parseInt(scan.nextLine().trim());
			// System.out.println("Please enter name of continent and control
			// value seperated by \",\"");
			for (int i = 0; i < numOfContinents; i++) {

				System.out.println("Please enter name of continent and control value seperated by \",\"");
				String[] continentAndValue = scan.nextLine().split(",");
				Continent checkContinent = new Continent(continentAndValue[0].trim(),
						Integer.parseInt(continentAndValue[1].trim()));
				// System.out.println(continentSet.contains(checkContinent));
				if ((continentSet.contains(checkContinent))) {
					System.out.println("Continent already exists please enter another Continent");
					i--;
				} else {
					continentSet
							.add(new Continent(continentAndValue[0], Integer.parseInt(continentAndValue[1].trim())));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (addmorecountries) {

			System.out.println("Please enter the continent, to which you want to add countries.");
			bufferContinent = new Continent(scan.nextLine().toLowerCase().trim(), 0);

			// Searching if the continent is there or not
			System.out.println(bufferContinent.getName());
			if (continentSet.contains(bufferContinent)) {
				for (Continent continent : continentSet) {
					// Getting the right continent
					if (continent.equals(bufferContinent)) {
						bufferContinent = continent;
						break;
					}
				}

				System.out.println("Please enter number of countries to be added to the continent");
				numberofcountry = Integer.parseInt(scan.nextLine().trim());
				for (int i = 0; i < numberofcountry; i++) {

					System.out.println(
							"Please enter name of country, x and y coordinate and neighboring countries seperated by ,");
					String[] userdata = scan.nextLine().split(",");

					temporaryCountry = new Country(userdata[0].trim());

					if (countrySet.contains(temporaryCountry)) {
						for (Country country : countrySet) {
							// Getting the right country
							if (country.equals(temporaryCountry)) {
								temporaryCountry = country;
								break;
							}
						}
					}

					temporaryCountry.setxValue(userdata[1].trim());
					temporaryCountry.setyValue(userdata[2].trim());
					if (temporaryCountry.getPartOfContinent() == null) {
						temporaryCountry.setPartOfContinent(bufferContinent);
					}
					for (int neighborCountry = 3; neighborCountry < userdata.length; neighborCountry++) {
						Country temporaryNeighbourCountry = new Country(userdata[neighborCountry].trim());
						if (countrySet.contains(temporaryNeighbourCountry)) {
							for (Country country : countrySet) {
								// Getting the right country
								if (country.equals(temporaryNeighbourCountry)) {
									temporaryNeighbourCountry = country;
									break;
								}
							}
							temporaryCountry.getAdjacentCountries().add(temporaryNeighbourCountry);
						} else {
							countrySet.add(temporaryNeighbourCountry);
							if (temporaryCountry.getPartOfContinent() == null) {
								temporaryCountry.setPartOfContinent(bufferContinent);
							}
							temporaryCountry.getAdjacentCountries().add(temporaryNeighbourCountry);

						}

					}
					if (!(bufferContinent.getListOfCountries().contains(temporaryCountry))) {
						bufferContinent.getListOfCountries().add(temporaryCountry);

					}
					countrySet.add(temporaryCountry);
				}

			} else {
				System.out.println("Continent does not exist");
			}

			for (Country country : countrySet) {
				Country checkCountry = country;
				ArrayList<Country> neighbours = checkCountry.getAdjacentCountries();
				boolean present = true;
				for (Country adjacent : neighbours) {
					if (!adjacent.getAdjacentCountries().contains(checkCountry)) {
						System.out.println(checkCountry.getName() + " is not adjacent to " + adjacent.getName());
						System.out.println(false);
					}
				}
				if (present) {
					System.out.println("everything is fine");
				}
			}

			// Code for removing a
			// country+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			System.out.println(
					"To add more countries press T, else press any key to exit, map will not be saved until complete");

			if (scan.nextLine().toLowerCase().trim().equals("t")) {
				addmorecountries = true;
			} else {
				addmorecountries = false;
			}

		}

	}

	public void editExistingMap(MapValidate map) {

		MapIO mIO = new MapIO(map);

		HashSet<Country> countrySet = new HashSet<Country>();
		HashSet<Continent> continentSet = new HashSet<Continent>();

		// Setting name, x,y and name of continent
		for (Map.Entry<String, Country> countries : mIO.getCountrySet().entrySet()) {
			Country checkCountry = countries.getValue();
			countrySet.add(checkCountry);
		}
		// Setting anem and control value
		for (Map.Entry<String, Continent> continents : mIO.getContinents().entrySet()) {
			Continent checkContinent = continents.getValue();
			continentSet.add(checkContinent);
		}

		for (Map.Entry<Continent, HashSet<Country>> countriesInContinent : mIO.getCountriesInContinent().entrySet()) {
			// listOfCOuntries
			// partOfContinenet
			ArrayList<Country> arOfCountries = new ArrayList<>();
			// convert arraylist to hashset
			for (Country country : countriesInContinent.getValue()) {
				arOfCountries.add(country);
			}

		}

	}
}
