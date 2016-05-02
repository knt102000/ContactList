package com.trial.chiutsui.contactlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ArrayList<Contact> contacts = new ArrayList<Contact>();
        Contact c1 = new Contact();
        c1.setName("Steve");
        contacts.add(c1);

        ListView contactListView = (ListView) findViewById(R.id.contactListView);
        contactListView.setAdapter(new contactAdapter(contacts));
    }

    private class contactAdapter extends ArrayAdapter<Contact> {
        contactAdapter(ArrayList<Contact> contacts) {
            super(ContactListActivity.this, R.layout.contact_list_row, R.id.contact_row_name, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.contact_row_name);
            Contact contact = getItem(position);

            nameTextView.setText(contact.getName());

            return convertView;
        }
    }
}
