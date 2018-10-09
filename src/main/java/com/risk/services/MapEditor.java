package com.risk.services;

import com.risk.model.*;

import java.util.*;

public class MapEditor {

	private int numberOfContinents;
	private HashSet<Continent> continentSet = new HashSet<Continent>();
	private HashSet<Country> countrySet = new HashSet<Country>();
	private HashMap<String, Continent> continents;
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	private int numberofcountry = 0;
	private Continent bufferContinent;
	private Country temporaryCountry;
	private String nameofcontinent;
	private boolean isMoreCountries = true;
	private boolean isDeleteCountry = true;
	private boolean isAddEdge = true;

	private boolean isFlawPresnet = true;
	private ArrayList<String> mapTagData;

	private String author;
	private String warn;
	private String image;
	private String wrap;
	private String scroll;

	private MapGraph mapGraph;

	public MapEditor() {
		mapTagData = new ArrayList<String>();
		this.editMapTagData();
		continents = new HashMap<String, Continent>();
		adjacentCountries = new HashMap<Country, ArrayList<Country>>();
	}

	public MapEditor(MapGraph mapGraph) {
		this.mapGraph = mapGraph;
		continents = new HashMap<String, Continent>();
		adjacentCountries = new HashMap<Country, ArrayList<Country>>();
	}

	public void createNewMap() {

		Scanner scan = new Scanner(System.in);

		System.out.println("Please provide the number of continents you want to create (only an integer)");
		try {
			numberOfContinents = Integer.parseInt(scan.nextLine().trim());
			for (int i = 0; i < numberOfContinents; i++) {

				System.out.println("Please enter name of continent and control value seperated by ','");
				String[] continentAndValue = scan.nextLine().split(",");
				Continent checkContinent = new Continent(continentAndValue[0].trim().toLowerCase(),
						Integer.parseInt(continentAndValue[1].trim()));
				if ((continentSet.contains(checkContinent))) {
					System.out.println("Continent already exists please enter another Continent");
					i--;
				} else {
					continentSet.add(
							new Continent(continentAndValue[0].trim(), Integer.parseInt(continentAndValue[1].trim())));
				}
			}
		} catch (Exception e) {
			System.out.println("Soemthing wentwrong in adding a continent");
			;
		}

		while (isMoreCountries) {
			System.out.println("Please enter the continent, to which you want to add countries.");
			bufferContinent = new Continent(scan.nextLine().toLowerCase().trim(), 0);
			if (continentSet.contains(bufferContinent)) {
				for (Continent continent : continentSet) {
					if (continent.equals(bufferContinent)) {
						bufferContinent = continent;
						break;
					}
				}
				try {
					while (isMoreCountries) {
						isMoreCountries = this.addMoreCountries(true);
					}

				} catch (Exception e) {
					System.out.println("Something went wrong in adding a country");
				}

			} else {
				System.out.println("Continent does not exist");
			}
		}
		try {
			System.out.println("Enter true to delete a country, else false");
			isDeleteCountry = scan.nextBoolean();
			scan.nextLine();
			while (isDeleteCountry) {
				isDeleteCountry = deleteACountry(isDeleteCountry);
			}
		} catch (Exception e) {
			System.out.println("Something went wrong in deleting a country");
		}

		try {
			System.out.println("Enter true to add edge between countries, else false");
			isAddEdge = scan.nextBoolean();
			scan.nextLine();
			while (isAddEdge) {
				System.out.println("Enter 2 countries you want to add edge sepearated be ','");
				isAddEdge = addEdgeBetweenCountries(scan.nextLine().trim().toLowerCase(),
						scan.nextLine().trim().toLowerCase());
			}
		} catch (Exception e) {
			System.out.println("Something went wrong in adding edge");
		}

		for (Continent continent : continentSet) {
			continents.put(continent.getName(), continent);
		}

		for (Country country : countrySet) {
			adjacentCountries.put(country, country.getAdjacentCountries());
		}

	}

	public void editExistingMap() {

		Scanner scan = new Scanner(System.in);
		MapIO mapIO = mapGraph.getMapIO();
		HashMap<String, Continent> hashMapContinent = mapIO.getContinents();
		System.out.println(
				"List of already existing coontinent along with control value, list of countries and adjacent countries is listed below");
		for (Map.Entry<String, Continent> entry : hashMapContinent.entrySet()) {
			continentSet.add(entry.getValue());
			System.out.println();
			System.out.println("Name of continent =" + " " + entry.getValue().getName() + ", control value = "
					+ entry.getValue().getControlValue());
			for (Country country : entry.getValue().getListOfCountries()) {
				System.out.print("Adjacent countries to " + country.getName() + " are : ");
				for (Country countryInList : country.getAdjacentCountries()) {
					System.out.print(" " + countryInList.getName() + ", ");
				}
				System.out.println();
			}
		}

		for (Continent testcontinent : continentSet) {
			for (Country countryInList : testcontinent.getListOfCountries()) {
				if (!countrySet.contains(countryInList)) {
					countrySet.add(countryInList);
				}
			}
		}

		System.out.println(
				"Press 1 for creating a new continent, or 2 for editing an old continent(for OPERATIONS on countries select edit continent)");

		int newOrOld = Integer.parseInt(scan.nextLine());
		try {
			if (newOrOld == 1) {
				System.out.println("Please enter number of continent you want to add");
				numberOfContinents = Integer.parseInt(scan.nextLine().trim());
				for (int i = 0; i < numberOfContinents; i++) {

					System.out.println("Please enter name of continent and control value seperated by \",\"");
					String[] continentAndValue = scan.nextLine().split(",");
					bufferContinent = new Continent(continentAndValue[0].trim(),
							Integer.parseInt(continentAndValue[1].trim()));
					if ((continentSet.contains(bufferContinent))) {
						System.out.println("Continent already exists please enter another Continent");
						i--;
						isMoreCountries = false;
					} else {
						continentSet.add(bufferContinent);
						isMoreCountries = true;
					}
					try {
						while (isMoreCountries) {
							isMoreCountries = addMoreCountries(isMoreCountries);
						}

					} catch (Exception e) {
						System.out.println("Something went wrong in adding a country");
					}

				}
				System.out.println("Enter true to delete a country, else false");
				isDeleteCountry = scan.nextBoolean();
				scan.nextLine();
				while (isDeleteCountry) {
					isDeleteCountry = deleteACountry(isDeleteCountry);
				}
				System.out.println("Enter true to add edge between countries, else false");
				isAddEdge = scan.nextBoolean();
				scan.nextLine();
				while (isAddEdge) {
					System.out.println("Enter 2 countries you want to add edge sepearated be ','");
					isAddEdge = addEdgeBetweenCountries(scan.nextLine().trim().toLowerCase(),
							scan.nextLine().trim().toLowerCase());
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong in adding new continent");
		}

		try {
			if (newOrOld == 2) {

				System.out.println(
						"Please enter number of continent you want to edit (to ADD a country select a continent first, for deletion or adding edge enter 0)");
				numberOfContinents = Integer.parseInt(scan.nextLine().trim());
				for (int i = 0; i < numberOfContinents; i++) {
					System.out.println("Please enter name of continent");
					String continent = scan.nextLine().trim().toLowerCase();
					bufferContinent = new Continent(continent.trim().toLowerCase(), 0);
					if (continentSet.contains(bufferContinent)) {
						for (Continent originalcontinent : continentSet) {
							if (originalcontinent.equals(bufferContinent)) {
								bufferContinent = originalcontinent;
								isMoreCountries = true;
								break;
							}
						}
					} else {
						System.out.println("Continent doesnt exsit");
						i--;
						isMoreCountries = false;
						break;
					}

					while (isMoreCountries) {
						isMoreCountries = addMoreCountries(isMoreCountries);
					}
				}
				System.out.println("Enter true to delete a country, else false");
				isDeleteCountry = scan.nextBoolean();
				scan.nextLine();
				while (isDeleteCountry) {
					isDeleteCountry = deleteACountry(isDeleteCountry);
				}
				System.out.println("Enter true to add edge between countries, else false");
				isAddEdge = scan.nextBoolean();
				scan.nextLine();
				while (isAddEdge) {
					System.out.println("Enter 2 countries you want to add edge, each on seperate line");
					isAddEdge = addEdgeBetweenCountries(scan.nextLine().trim().toLowerCase(),
							scan.nextLine().trim().toLowerCase());
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong in adding new continent");
		}

		for (Continent continent : continentSet) {
			continents.put(continent.getName(), continent);
		}

		for (Country country : countrySet) {
			adjacentCountries.put(country, country.getAdjacentCountries());
		}
	}

	public void editMapTagData() {

		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter name of the author");
		author = "Author = " + scan.nextLine().trim();

		System.out.println("Please specify warn is yes or no");
		warn = "Warn = " + scan.nextLine().trim();

		System.out.println("Please specify the image for the map");
		image = "Image = " + scan.nextLine().trim();

		System.out.println("Please specify wrap is yes or no");
		wrap = "Wrap = " + scan.nextLine().trim();

		System.out.println("Please specify scroll is horizontal or vertical");
		scroll = "Scroll = " + scan.nextLine().trim();
		// scan.close();

		mapTagData.add(author);
		mapTagData.add(warn);
		mapTagData.add(image);
		mapTagData.add(wrap);
		mapTagData.add(scroll);

	}

	public boolean addMoreCountries(boolean bool) {

		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter number of countries to be added to " + bufferContinent.getName());
		numberofcountry = Integer.parseInt(scan.nextLine().trim());
		for (int i = 0; i < numberofcountry; i++) {

			System.out.println(
					"Please enter name of country, x and y coordinate and neighboring countries seperated by ,");
			String[] userdata = scan.nextLine().split(",");
			temporaryCountry = new Country(userdata[0].trim().toLowerCase());

			if (countrySet.contains(temporaryCountry)) {
				for (Country country : countrySet) {
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
				Country temporaryNeighbourCountry = new Country(userdata[neighborCountry].trim().toLowerCase());
				if (countrySet.contains(temporaryNeighbourCountry)) {
					for (Country country : countrySet) {
						if (country.equals(temporaryNeighbourCountry)) {
							temporaryNeighbourCountry = country;
							if (temporaryNeighbourCountry.getPartOfContinent() == null) {
								temporaryNeighbourCountry.setPartOfContinent(bufferContinent);
								if (!bufferContinent.getListOfCountries().contains(temporaryNeighbourCountry)) {
									bufferContinent.getListOfCountries().add(temporaryNeighbourCountry);
								}
							}
							break;
						}
					}
					if (!temporaryCountry.getAdjacentCountries().contains(temporaryNeighbourCountry)) {
						temporaryCountry.getAdjacentCountries().add(temporaryNeighbourCountry);
					}
					if (!temporaryNeighbourCountry.getAdjacentCountries().contains(temporaryCountry)) {
						temporaryNeighbourCountry.getAdjacentCountries().add(temporaryCountry);
					}
				} else {
					countrySet.add(temporaryNeighbourCountry);
					if (temporaryCountry.getPartOfContinent() == null) {
						temporaryCountry.setPartOfContinent(bufferContinent);
					}
					if (temporaryNeighbourCountry.getPartOfContinent() == null) {
						temporaryNeighbourCountry.setPartOfContinent(bufferContinent);
					}
					if (!temporaryCountry.getAdjacentCountries().contains(temporaryNeighbourCountry)) {
						temporaryCountry.getAdjacentCountries().add(temporaryNeighbourCountry);
					}
					if (!temporaryNeighbourCountry.getAdjacentCountries().contains(temporaryCountry)) {
						temporaryNeighbourCountry.getAdjacentCountries().add(temporaryCountry);
					}
					if (!bufferContinent.getListOfCountries().contains(temporaryNeighbourCountry)) {
						bufferContinent.getListOfCountries().add(temporaryNeighbourCountry);
					}
				}
				countrySet.add(temporaryNeighbourCountry);

			}
			if (!(bufferContinent.getListOfCountries().contains(temporaryCountry))) {
				bufferContinent.getListOfCountries().add(temporaryCountry);
			}
			countrySet.add(temporaryCountry);
		}

		for (Country country : countrySet) {
			Country checkCountry = country;
			ArrayList<Country> neighbours = checkCountry.getAdjacentCountries();
			for (Country adjacent : neighbours) {
				if (!adjacent.getAdjacentCountries().contains(checkCountry)) {
					System.out.println(adjacent.getName() + " is not adjacent to " + checkCountry.getName());
					isFlawPresnet = true;
				} else {
				}
			}
		}
		if (!isFlawPresnet) {
			System.out.println("everything is fine");
		}

		System.out.println();

		System.out.println("To add more countries enter true, else enter false");
		if (scan.nextBoolean()) {
			scan.nextLine();
			return true;
		} else {
			scan.nextLine();
			return false;
		}

	}

	public boolean deleteACountry(boolean bool) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter countries you want to delete seperated by a ','");
		String[] countriestodelete = scan.nextLine().split(",");

		for (String countrytodelete : countriestodelete) {
			Country buffercountry = new Country(countrytodelete.trim().toLowerCase());
			System.out.println(buffercountry.getName());

			buffercountry = searchCountry(buffercountry);
			if (buffercountry == null) {
				System.out.println(countrytodelete + " does not exist");

			}
			for (Country country : buffercountry.getAdjacentCountries()) {
				country.getAdjacentCountries().remove(buffercountry);
			}
			buffercountry.getPartOfContinent().getListOfCountries().remove(buffercountry);
			countrySet.remove(buffercountry);
		}

		for (Country country : countrySet) {
			System.out.println(country.getName());
			for (Country countryx : country.getAdjacentCountries()) {
				System.out.println(countryx.getName() + " neightbour");
			}
		}
		System.out.println(countrySet.size());

		System.out.println("Enter true to delete more countries, else false");
		if (scan.nextBoolean()) {
			scan.nextLine();
			return true;
		} else {
			scan.nextLine();
			return false;
		}
	}

	public boolean addEdgeBetweenCountries(String countrysource, String countrydestination) {

		Scanner scan = new Scanner(System.in);

		Country source = new Country(countrysource);
		Country destination = new Country(countrydestination);

		source = searchCountry(source);
		if (source == null) {
			System.out.println(countrysource + " does not exist");

		}

		destination = searchCountry(destination);
		if (destination == null) {
			System.out.println(countrydestination + " does not exist");

		}

		if (!source.getAdjacentCountries().contains(destination)) {
			source.getAdjacentCountries().add(destination);
		}
		if (!destination.getAdjacentCountries().contains(source)) {
			destination.getAdjacentCountries().add(source);
		}

		System.out.println("To add more edge enter true, else false");
		isAddEdge = scan.nextBoolean();
		scan.nextLine();

		if (isAddEdge) {
			return true;
		} else {
			return false;
		}
	}

	public Country searchCountry(Country country) {

		for (Country mycountry : countrySet) {
			if (mycountry.getName().toLowerCase().trim().equals(country.getName().toLowerCase().trim())) {
				return mycountry;
			}
		}
		return null;
	}
}
