package com.risk.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Class for MapValidation
 *
 * @author Neha Pal
 * @author Farhan Shaheen
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
	private String invalidMapFile0;
	/** String to hold an Invalid Map File */
	private String invalidMapFile1;
	/** String to hold an Invalid Map File */
	private String invalidMapFile2;
	/** String to hold an Invalid Map File */
	private String invalidMapFile3;

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
		validMapFile = "C:\\Temp\\Alberta.map";
		invalidMapFile0 = "C:\\Temp\\FileDoesnotExist.map";
		invalidMapFile1 = "C:\\Temp\\Test1.map";
		invalidMapFile2 = "C:\\Temp\\Test2.map";
		invalidMapFile3 = "C:\\Temp\\Test3.map";
	}

	/**
	 * Test method for testing validation of a file
	 * @throws Exception
	 */
	@Test
	public void testValidateFileValidFile() throws Exception {

		assertTrue(mapValidation.validateMapFile(validMapFile));
	}
	/**
	 * File Does not exist
	 * <code>
	 * if (mapFile != null) {
	 * </code>
	 * @throws Exception
	 */
	@Test
	public void testValidateFileInValidFile0() throws Exception {
		
		assertFalse(mapValidation.validateMapFile(invalidMapFile0));
	}
	/**
	 * Testing for invalid TAGS
	 * <code>
	 * if (!checkAllTags(inputText)) {
	 * </code>
	 * @throws Exception
	 */
	@Test
	public void testValidateFileInValidFile1() throws Exception {
		
		assertFalse(mapValidation.validateMapFile(invalidMapFile1));
	}
	/**
	 * Wrong TAGS
	 * @throws Exception
	 */
	@Test
	public void testValidateFileInValidFile2() throws Exception {
		
		assertFalse(mapValidation.validateMapFile(invalidMapFile2));
	}
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testValidateFileInValidFile3() throws Exception {
		
		assertFalse(mapValidation.validateMapFile(invalidMapFile3));
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
