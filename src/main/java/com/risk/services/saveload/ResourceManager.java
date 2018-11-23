package com.risk.services.saveload;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceManager {

    //method for saving game data to a file
    public static void save(Serializable data, String fileName) throws Exception{
        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
            oos.writeObject(data);
        }
    }

    //method for loading game data from the concerned file.
    public static Object load(String fileName) throws Exception{
        try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
            return ois.readObject();
        }
    }

}
