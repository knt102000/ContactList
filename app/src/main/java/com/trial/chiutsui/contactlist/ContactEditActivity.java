package com.trial.chiutsui.contactlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactEditActivity extends AppCompatActivity {

    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        mContact = (Contact) getIntent().getSerializableExtra(ContactViewActivity.EXTRA);
    }
}
