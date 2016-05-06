package com.trial.chiutsui.contactlist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chiutsui on 4/30/16.
 */
public class Contact implements Serializable {
    private String name;
    public ArrayList<String> phoneNumbers;
    public ArrayList<String> email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
