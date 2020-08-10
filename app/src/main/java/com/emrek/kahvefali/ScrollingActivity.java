package com.emrek.kahvefali;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.emrek.kahvefali.ui.main.Fragment1;
import com.emrek.kahvefali.ui.main.Fragment1ListViewAdapter;
import com.emrek.kahvefali.ui.main.Fragment2;
import com.emrek.kahvefali.ui.main.Fragment2ListViewAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {
    ImageView scroll_image;
    Toolbar toolbar;
    TextView content_text;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);


        scroll_image = findViewById(R.id.scroll_image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        content_text = findViewById(R.id.content_text);

        Intent intent = getIntent();
        if (intent.getIntExtra("fragment", 0) == 1) {
            toolbar.setTitle(Fragment1.otherHeader.getText().toString());
            content_text.setText(Fragment1.user.getText().toString() + "\n\n" + Fragment1.otherText.getText().toString() + "\n" + Fragment1.otherTime.getText().toString());
            Picasso.get().load(Fragment1ListViewAdapter.url).into(scroll_image);
        } else if (intent.getIntExtra("fragment", 0) == 2) {
            toolbar.setTitle(Fragment2.otherHeader.getText().toString());
            content_text.setText(Fragment2.user.getText().toString() + "\n\n" + Fragment2.otherText.getText().toString() + "\n" + Fragment2.otherTime.getText().toString());
            Picasso.get().load(Fragment2ListViewAdapter.url).into(scroll_image);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.searchFortune = "";
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "select"), 1);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(61, 70, 120));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
