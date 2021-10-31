package com.cahyaa.moviedb.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.adapter.CastAdapter;
import com.cahyaa.moviedb.databinding.ActivityMovieDetailsBinding;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Credits;
import com.cahyaa.moviedb.model.Movies;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;

    private MovieViewModel view_model;

    private String movie_id = "";
    private String spoken_language = "";
    private String movie_genre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbarMovieDetails);
        binding.toolbarMovieDetails.setNavigationOnClickListener(new View.OnClickListener() {
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
        view_model.getCredits(movie_id);
        view_model.getResultGetCredits().observe(MovieDetailsActivity.this, showResultCredits);
    }

    private final Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            binding.toolbarMovieDetails.setTitle(movies.getTitle());

            String backdrop_path = Const.IMG_URL + movies.getBackdrop_path().toString();
            Glide.with(MovieDetailsActivity.this).load(backdrop_path).into(binding.imgBackdropMovieDetails);

            binding.lblRatingMovieDetails.setText(movies.getVote_average() + "/10");
            binding.lblVoteCount.setText(String.valueOf(movies.getVote_count()));
            binding.lblPopularityMovieDetails.setText(String.valueOf(movies.getPopularity()));

            String poster_path = Const.IMG_URL + movies.getPoster_path().toString();
            Glide.with(MovieDetailsActivity.this).load(poster_path).into(binding.imgPosterMovieDetails);

            binding.lblTitleMovieDetails.setText(movies.getTitle());
            binding.lblRuntimeMovieDetails.setText(String.valueOf(movies.getRuntime() + "'"));
            binding.lblIdMovieDetails.setText(movie_id);

            for (int i = 0; i < movies.getSpoken_languages().size(); i++) {
                if (i == movies.getSpoken_languages().size() - 1) {
                    spoken_language += movies.getSpoken_languages().get(i).getName();
                } else {
                    spoken_language += movies.getSpoken_languages().get(i).getName() + ", ";
                }
            }
            binding.lblLanguageMovieDetails.setText(spoken_language);

            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() - 1) {
                    movie_genre += movies.getGenres().get(i).getName();
                } else {
                    movie_genre += movies.getGenres().get(i).getName() + " | ";
                }
            }
            binding.lblGenreMovieDetails.setText(movie_genre);

            if (movies.getTagline().isEmpty()) {
                binding.lblTaglineMovieDetails.setText("-");
            } else {
                binding.lblTaglineMovieDetails.setText(movies.getTagline());
            }

            binding.lblOverviewMovieDetails.setText(movies.getOverview());

            binding.btnHomepageMovieDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(movies.getHomepage())));
                }
            });
        }
    };

    private Observer<Credits> showResultCredits = new Observer<Credits>() {
        @Override
        public void onChanged(Credits credits) {
            CastAdapter adapter = new CastAdapter(MovieDetailsActivity.this);
            adapter.setListNowPlaying(credits.getCast());
            binding.rvCredits.setAdapter(adapter);
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }
}