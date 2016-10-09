package com.trial.chiutsui.contactlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactEditActivity extends AppCompatActivity {

    public static final String EXTRA = "CVA Extra";

    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        int position = getIntent().getIntExtra(EXTRA, 0);
        mContact = ContactList.getInstance().get(position);

        EditText mEditName = (EditText) findViewById(R.id.edit_name);
        mEditName.setText(mContact.getName());

        addToSection(R.id.phoneNumber_section,mContact.phoneNumbers);
        addToSection(R.id.email_section,mContact.email);

        TextView addNewPhone = (TextView) findViewById(R.id.add_new_phone);
        addNewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToSection(R.id.phoneNumber_section,"");
            }
        });

        TextView addNewEmail = (TextView) findViewById(R.id.add_new_email);
        addNewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToSection(R.id.email_section,"");
            }
        });

        Toolbar tb = (Toolbar) findViewById(R.id.editContactToolbar);
        tb.setTitle(getResources().getString(R.string.edit_contact));
        tb.setNavigationIcon(R.drawable.ic_done_white);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName = (EditText) findViewById(R.id.edit_name);
                mContact.setName(editName.getText().toString());

                mContact.email = getSectionValues(R.id.email_section);
                mContact.phoneNumbers = getSectionValues(R.id.phoneNumber_section);

                Toast.makeText(ContactEditActivity.this,"Saved Contact", Toast.LENGTH_LONG)
                        .show();

                finish();
            }
        });
    }

    private ArrayList<String> getSectionValues(int sectionId) {
        ArrayList<String> values = new ArrayList<String>();
        LinearLayout section = (LinearLayout) findViewById(sectionId);
        for (int i = 0; i < section.getChildCount(); i++) {
            EditText editValue = (EditText) section.getChildAt(i);
            values.add(editValue.getText().toString());
        }
        return values;
    }

    private void addToSection(int sectionId, String value) {
        LinearLayout section = (LinearLayout) findViewById(sectionId);
        EditText et = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);
        et.setText(value);
        section.addView(et);
    }

    private void addToSection(int sectionId, ArrayList<String> values) {
        LinearLayout section = (LinearLayout) findViewById(sectionId);
        for (int i =0; i < values.size(); i++) {
            EditText et = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(lp);
            et.setText(values.get(i));
            section.addView(et);
        }
    }
}
