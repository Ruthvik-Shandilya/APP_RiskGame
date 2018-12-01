package com.risk.services.saveload;

import java.io.*;

/**
 * Resource Manager class contains methods for saving game data into a file
 * and loading game data from file
 *
 * @author Farhan Shaheen
 * @author Palash Jain
 */
public class ResourceManager {
    /**
     * Method for to save game data into a file
     *
     * @param data     Object of interface.
     * @param fileName name of file for saving data.
     */
    //method for saving game data to a file
    public static void save(Externalizable data, File fileName) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        }
    }

    /**
     * Method for to load game data from a file
     *
     * @param fileName name of file for saving data.
     * @return data from file.
     * @throws Exception may throw IOException, ClassNotFoundException
     */
    //method for loading game data from the concerned file.
    public static Object load(File fileName) throws Exception{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        }
    }

}
