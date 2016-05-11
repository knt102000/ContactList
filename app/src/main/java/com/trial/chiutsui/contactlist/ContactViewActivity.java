package com.trial.chiutsui.contactlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.Display;
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

public class ContactViewActivity extends AppCompatActivity {

    public static final String EXTRA = "CVA Extra";

    private int mColor;

    private Contact mContact;

    private int mPosition;

    private TextView mNameText;

    private FieldAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        RelativeLayout contactViewHeader = (RelativeLayout) findViewById(R.id.contact_view_header);

        mPosition = getIntent().getIntExtra(ContactListActivity.EXTRA, 0);

        mContact = ContactList.getInstance().get(mPosition);

        ImageView mainImg = (ImageView) findViewById(R.id.mainImage);
        Display dis = getWindowManager().getDefaultDisplay();
        Point pt = new Point();
        dis.getSize(pt);

        int width = pt.x;
        int height = pt.y;

        contactViewHeader.setLayoutParams(new LinearLayout.LayoutParams(width,(int) (width*9/16)));

        mNameText = (TextView) findViewById(R.id.contact_name);

        Toolbar tb = (Toolbar) findViewById(R.id.contact_view_toolbar);
        tb.inflateMenu(R.menu.contact_view_menu);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.contact_view_edit) {
                    Intent intent = new Intent(ContactViewActivity.this, ContactEditActivity.class);
                    intent.putExtra(EXTRA,mPosition);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        ListView contactListInfo = (ListView) findViewById(R.id.contact_view_fields);
        mAdapter = new FieldAdapter(mContact.phoneNumbers,mContact.email);
        contactListInfo.setAdapter(mAdapter);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.sunset);
        Palette p = Palette.from(bitmap).generate();
        mColor = p.getDarkVibrantColor(p.getDarkMutedColor(mColor));

        updateUI();
    }

    private void updateUI() {
        mNameText.setText(mContact.getName());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
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
                convertView= ContactViewActivity.this.getLayoutInflater().inflate(R.layout.contact_view_field_row,viewGroup, false);
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
