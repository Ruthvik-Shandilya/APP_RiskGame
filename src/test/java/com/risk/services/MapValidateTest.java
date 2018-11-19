package com.risk.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for MapValidation
 *
 * @author Neha Pal
 *
 */
public class MapValidateTest {

	/** Object for MapValidation class*/
	private MapValidate mapValidation;

	/** String to hold Invalid Tags */
	private String invalidTag;

	/** String to hold Valid Tags */    
	private String validTag;

	/** String to hold valid Map File */
	private String validMapFile;

	/** String to hold an Invalid Map File */
	private String invalidMapFile;

	private String invalidContinentConfigFile;

	private String countriesNotAdjacentFile;

	private String notAConnectedGraph;

	/**
	 * Set up the initial objects for Map Validation
	 * 
	 */
	@Before
	public void initialize() {
		System.out.println("In setup");
		mapValidation = new MapValidate();
		invalidTag = " [Continents] [Territories]";
		validTag = "[Map] [Continents] [Territories]";
		validMapFile = "src/main/maps/Europe.map";
		invalidMapFile = "src/main/maps/Africa.map";
		invalidContinentConfigFile = "src/test/maps/IndiaNC.map";
		countriesNotAdjacentFile = "src/test/maps/IndiaNA.map";
		notAConnectedGraph = "src/test/maps/India.map";
	}

	/**
	 * Test method for testing validation of a file
	 * 
	 */
	@Test
	public void validateFileValidFileTest() {

		assertTrue(mapValidation.validateMapFile(validMapFile));
	}
	
	/**
	 * Test method for testing invalid file
	 * 
	 */
	@Test
	public void validateFileInValidFileTest() {

		assertFalse(mapValidation.validateMapFile(invalidMapFile));
	}

	@Test
	public void validateFileConfigTest() {

		assertFalse(mapValidation.validateMapFile(invalidContinentConfigFile));
	}

	@Test
	public void validateCountryAdjacencyTest() {

		assertFalse(mapValidation.validateMapFile(countriesNotAdjacentFile));
	}

	@Test
	public void validateFileConnectedTest() {

		assertFalse(mapValidation.validateMapFile(notAConnectedGraph));
	}

	/**
	 * Test method for checking all necessary tags like [Map], [Continents], [Territories]
	 * 
	 */
	@Test
	public void checkMandatoryTagsValidTagsTest() {

		assertTrue(mapValidation.checkAllTags(validTag));
	}

	/**
	 * Test method for checking invalid tags
	 * 
	 */
	@Test
	public void checkMandatoryTagsInValidTagsTest() {

		assertFalse(mapValidation.checkAllTags(invalidTag));
	}
}
