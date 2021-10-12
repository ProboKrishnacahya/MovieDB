package com.cahyaa.moviedb.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_text;
    private String movie_id = "";
    private Toolbar toolbar_moviedetails;
    private MovieViewModel view_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");
        lbl_text = findViewById(R.id.lbl_id_moviedetails);
        lbl_text.setText("Movie ID: " + movie_id);

        toolbar_moviedetails = (Toolbar) findViewById(R.id.toolbar_moviedetails);
        setSupportActionBar(toolbar_moviedetails);
        toolbar_moviedetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}