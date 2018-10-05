package com.risk.services;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapLoaderTest {

	private MapLoader maploader;
	
	private String mapFile;
	@BeforeEach
	void setUp() throws Exception {
		
		maploader= new MapLoader();
		mapFile = "D:\\Europe.map";
	}

	@Test
	public void TestMapLoading()throws Exception {
		assertTrue(maploader.validateAndLoadMapFile(mapFile));
	}

}
