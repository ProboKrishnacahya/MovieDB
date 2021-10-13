package com.cahyaa.moviedb.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.databinding.ActivityMovieDetailsBinding;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Movies;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;

    private String movie_id = "";
    private String movie_genre = "";
    private MovieViewModel view_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbarMoviedetails);
        binding.toolbarMoviedetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");

        view_model = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        view_model.getMovieById(movie_id);
        view_model.getResultGetMovieById().observe(MovieDetailsActivity.this, showResultMovie);
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String backdrop_path = Const.IMG_URL + movies.getBackdrop_path().toString();
            Glide.with(MovieDetailsActivity.this).load(backdrop_path).into(binding.imgBackdropMoviedetails);
            String poster_path = Const.IMG_URL + movies.getPoster_path().toString();
            Glide.with(MovieDetailsActivity.this).load(poster_path).into(binding.imgPosterMoviedetails);
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() - 1) {
                    movie_genre += movies.getGenres().get(i).getName();
                } else {
                    movie_genre += movies.getGenres().get(i).getName() + " | ";
                }
            }
            binding.lblTitleMoviedetails.setText(movies.getTitle());
            binding.lblOverviewMoviedetails.setText(movies.getOverview());
            binding.lblGenreMoviedetails.setText(movie_genre);
            binding.lblPopularityMoviedetails.setText(String.valueOf(movies.getPopularity()));
            binding.lblRatingMoviedetails.setText(movies.getVote_average() + "-" + movies.getVote_count());
//            lbl_rating_count.setText("Rating Count: " + movies.getVote_count());
            binding.lblIdMoviedetails.setText("Movie ID: " + movie_id);
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }
}