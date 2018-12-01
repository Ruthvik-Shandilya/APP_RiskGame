package com.risk.services.saveload;

import org.junit.Test;
import org.junit.Assert;

import java.io.*;

public class ResourceManagerTest {
	
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
