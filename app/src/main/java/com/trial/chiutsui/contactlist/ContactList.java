package com.trial.chiutsui.contactlist;

import java.util.ArrayList;

/**
 * Created by chiutsui on 5/9/16.
 */
public class ContactList extends ArrayList<Contact> {

    private static ContactList sInstance = null;

    private ContactList() {};

    public static ContactList getInstance() {
        if (sInstance == null) {
            sInstance = new ContactList();
        }

        return sInstance;
    }
}
