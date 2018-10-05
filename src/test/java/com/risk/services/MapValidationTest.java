package com.risk.services;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Class for MapValidation
 *
 * @author neha_
 *
 */
class MapValidationTest {

	/** MapValidation Object */

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
	 * Set up the initial objects
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
	public void testValidateFile() throws Exception {

		assertTrue(mapValidation.validateMapFile(validMapFile));
		assertFalse(mapValidation.validateMapFile(invalidMapFile));
	}


	/**
	 * test method for checking all necessary tags like [Map], [Continents], [Territories]
	 * @throws Exception
	 */
	@Test
	public void testCheckMandatoryTags() throws Exception {

		assertFalse(mapValidation.checkAllTags(invalidTag));
		System.out.println(mapValidation);
		System.out.println("hello");
		assertTrue(mapValidation.checkAllTags(validTag));
	}

}
