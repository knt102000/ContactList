package com.trial.chiutsui.contactlist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {

    public static final String EXTRA = "CONTACT";

    private ContactList mContacts;

    private contactAdapter mContactAdapter;

    public ContactListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);

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

        ListView contactListView = (ListView) v.findViewById(R.id.contactListView);
        mContactAdapter = new contactAdapter(mContacts);
        contactListView.setAdapter(mContactAdapter);

        contactListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int previousFirstItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                ActionBar ab = ((ActionBarActivity) getActivity()).getSupportActionBar();

                if (firstVisibleItem>previousFirstItem) {
                    ab.hide();
                } else if (firstVisibleItem<previousFirstItem) {
                    ab.show();
                }

                previousFirstItem = firstVisibleItem;
            }
        });

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ContactViewActivity.class);

                intent.putExtra(EXTRA, i);

                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_view_menu,menu);
    }

    @Override
    public void onResume() {
        super.onResume();

        mContactAdapter.notifyDataSetChanged();
    }

    private class contactAdapter extends ArrayAdapter<Contact> {
        contactAdapter(ArrayList<Contact> contacts) {
            super(getActivity(), R.layout.contact_list_row, R.id.contact_row_name, contacts);
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
