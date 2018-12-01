package com.risk.services.saveload;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import java.io.*;

/**
 * Test class for ResourceManager.
 * 
 * @author Neha Pal
 * @author Ruthvik Shandilya
 *
 */
public class ResourceManagerTest {

	/**
	 * Test to check name and code saved.
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @throws IOException IOException
	 */
	@Test
	public void saveLoadTest() throws IOException,ClassNotFoundException{

		SaveData test = new SaveData();
		test.setCode(374);
		test.setName("Canada");

		FileOutputStream fileOutputStream = new FileOutputStream("OUTPUT_FILE");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		test.writeExternal(objectOutputStream);

		objectOutputStream.flush();
		objectOutputStream.close();
		fileOutputStream.close();

		FileInputStream fileInputStream = new FileInputStream("OUTPUT_FILE");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);


		SaveData test1 = new SaveData();
		test1.readExternal(objectInputStream);

		objectInputStream.close();
		fileInputStream.close();

		Assert.assertTrue(test1.getCode()== test.getCode());
		Assert.assertEquals(test1.getName(),(test.getName()));

	}

}
