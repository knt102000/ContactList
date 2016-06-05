package com.trial.chiutsui.contactlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.ExtractedText;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactViewFragment extends android.app.Fragment {

    private int mColor;

    private Contact mContact;

    private int mPosition;

    private TextView mNameText;

    private FieldAdapter mAdapter;

    public ContactViewFragment() {
        // Required empty public constructor
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_view, container, false);

        mContact = ContactList.getInstance().get(mPosition);

        ImageView mainImg = (ImageView) v.findViewById(R.id.mainImage);

        mNameText = (TextView) v.findViewById(R.id.contact_name);

        Toolbar tb = (Toolbar) v.findViewById(R.id.contact_view_toolbar);
        tb.inflateMenu(R.menu.contact_view_menu);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.contact_view_edit) {
                    Intent intent = new Intent(getActivity(), ContactEditActivity.class);
                    intent.putExtra(ContactEditActivity.EXTRA,mPosition);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        ListView contactListInfo = (ListView) v.findViewById(R.id.contact_view_fields);
        mAdapter = new FieldAdapter(mContact.phoneNumbers,mContact.email);
        contactListInfo.setAdapter(mAdapter);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.sunset);
        Palette p = Palette.from(bitmap).generate();
        mColor = p.getDarkVibrantColor(p.getDarkMutedColor(mColor));

        updateUI();

        return v;
    }

    private void updateUI() {
        mNameText.setText(mContact.getName());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private class FieldAdapter extends BaseAdapter {
        ArrayList<String> phoneNumbers;
        ArrayList<String> emails;

        FieldAdapter(ArrayList<String> phoneNumbers, ArrayList<String> emails) {
            this.phoneNumbers=phoneNumbers;
            this.emails=emails;
        }

        @Override
        public int getCount() {
            return phoneNumbers.size() + emails.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView==null) {
                convertView= getActivity().getLayoutInflater().inflate(R.layout.contact_view_field_row,viewGroup, false);
            }

            TextView fieldText = (TextView) convertView.findViewById(R.id.contact_view_row_field);

            String value = (String) getItem(position);
            fieldText.setText(value);

            ImageView fieldIcon = (ImageView) convertView.findViewById(R.id.contact_view_row_field_icon);
            if (isFirst(position)) {
                if (isEmail(position)) {
                    fieldIcon.setImageResource(R.drawable.ic_email);
                } else {
                    fieldIcon.setImageResource(R.drawable.ic_call);
                }
            }

            fieldIcon.setColorFilter(mColor);

            return convertView;
        }

        private boolean isFirst(int position) {
            if ((position == 0)||(position == phoneNumbers.size())) {
                return true;
            }
            return false;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (isEmail(i)) {
                return emails.get(i-phoneNumbers.size());
            }

            return phoneNumbers.get(i);
        }

        private boolean isEmail(int position) {
            if (position > phoneNumbers.size()-1) {
                return true;
            }
            return false;
        }
    }
}
