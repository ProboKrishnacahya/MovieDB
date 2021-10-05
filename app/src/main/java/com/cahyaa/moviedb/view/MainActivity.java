package com.cahyaa.moviedb.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.model.Movies;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private Button btn_hit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(MainActivity.this).get(MovieViewModel.class);

        btn_hit = findViewById(R.id.btn_hit_main);
        btn_hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getMovieById("350");
                viewModel.getResultGetMovieById().observe(MainActivity.this, showResultMovie);
            }
        });
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {

        }
    };

}