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

    public static final String EXTRA = "CONTACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        if (getFragmentManager().findFragmentById(R.id.view_fragment_container)==null) {
            ContactViewFragment cvf = new ContactViewFragment();
            cvf.setPosition(getIntent().getIntExtra(EXTRA,0));
            getFragmentManager().beginTransaction()
                    .add(R.id.view_fragment_container, cvf)
                    .commit();
        }
    }
}
