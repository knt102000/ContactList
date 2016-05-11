package com.trial.chiutsui.contactlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    public static final String EXTRA = "CONTACT";

    private ContactList mContacts;

    private contactAdapter mContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mContacts = ContactList.getInstance();

        for (int i=0; i<=30; i++) {
            Contact c1 = new Contact();
            c1.setName("Steve");
            c1.email = new ArrayList<String>();
            c1.phoneNumbers = new ArrayList<String>();
            c1.email.add("myemail@gmail.com");
            c1.email.add("testemail@gmail.com");
            c1.phoneNumbers.add("1800MYLEMONS");
            mContacts.add(c1);
        }

        ListView contactListView = (ListView) findViewById(R.id.contactListView);
        mContactAdapter = new contactAdapter(mContacts);
        contactListView.setAdapter(mContactAdapter);

        contactListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int previousFirstItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem>previousFirstItem) {
                    getSupportActionBar().hide();
                } else if (firstVisibleItem<previousFirstItem) {
                    getSupportActionBar().show();
                }

                previousFirstItem = firstVisibleItem;
            }
        });

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ContactListActivity.this, ContactViewActivity.class);

                intent.putExtra(EXTRA, i);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mContactAdapter.notifyDataSetChanged();
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
