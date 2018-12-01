package com.risk.services.saveload;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
/**
 * 
 * @author Neha Pal
 * @author Karandeep Singh
 * 
 */
public class SaveData implements Externalizable {

	private static final long serialVersionUID = 1L;
	
	/** String for name*/
    private String name;
    
    /** Integer variable for code */
    private int code;

    /** Getter for name 
     * 
     *  @return name 
     */    
    public String getName() {
        return name;
    }

    /**
     * Setter for Name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for code
     * 
     * @return code
     */
    public int getCode() {
        return code;
    }
    
    /**
     * Setter for code
     * 
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * {@inheritDoc} method to save content
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(code);
    }

    /**
     * {@inheritDoc} method to restore its content
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name=(String)in.readObject();
        this.code=(int)in.readObject();
    }
}
