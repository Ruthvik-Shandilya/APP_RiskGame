package com.risk.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Class for MapValidation
 *
 * @author Neha Pal
 *
 */
class MapValidateTest {

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

	/**
	 * Set up the initial objects for Map Validation
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		System.out.println("In setup");
		mapValidation = new MapValidate();
		invalidTag = " [Continents] [Territories]";
		validTag = "[Map] [Continents] [Territories]";
		validMapFile = "D:\\Europe.map";
		invalidMapFile = "D:\\Africa.map";
	}

	/**
	 * Test method for testing validation of a file
	 * @throws Exception
	 */
	@Test
	public void testValidateFileValidFile() throws Exception {

		assertTrue(mapValidation.validateMapFile(validMapFile));
	}
	@Test
	public void testValidateFileInValidFile() throws Exception {

		assertFalse(mapValidation.validateMapFile(invalidMapFile));
	}

	/**
	 * test method for checking all necessary tags like [Map], [Continents], [Territories]
	 * @throws Exception
	 */
	@Test
	public void testCheckMandatoryTagsValidTags() throws Exception {

		assertTrue(mapValidation.checkAllTags(validTag));
	}

	@Test
	public void testCheckMandatoryTagsInValidTags() throws Exception {

		assertFalse(mapValidation.checkAllTags(invalidTag));
	}
}