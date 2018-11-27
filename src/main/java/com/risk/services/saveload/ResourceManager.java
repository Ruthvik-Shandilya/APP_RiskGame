package com.risk.services.saveload;

import com.risk.view.controller.GamePlayController;

import java.io.*;

public class ResourceManager {

    //method for saving game data to a file
    public static void save(Externalizable data, File fileName) throws Exception{
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            oos.writeObject(data);
        }
    }

    //method for loading game data from the concerned file.
    public static Object load(File fileName) throws Exception{
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            return ois.readObject();
        }
    }

}
