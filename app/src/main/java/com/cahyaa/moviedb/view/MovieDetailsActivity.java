package com.cahyaa.moviedb.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.adapter.CreditsAdapter;
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
        view_model.getCredits(movie_id);
        view_model.getResultGetCredits().observe(MovieDetailsActivity.this, showResultCredits);
    }

    private final Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            binding.toolbarMoviedetails.setTitle(movies.getTitle());

            String backdrop_path = Const.IMG_URL + movies.getBackdrop_path().toString();
            Glide.with(MovieDetailsActivity.this).load(backdrop_path).into(binding.imgBackdropMoviedetails);

            binding.lblRatingMoviedetails.setText(movies.getVote_average() + "/10");
            binding.lblVoteCount.setText(String.valueOf(movies.getVote_count()));
            binding.lblPopularityMoviedetails.setText(String.valueOf(movies.getPopularity()));

            String poster_path = Const.IMG_URL + movies.getPoster_path().toString();
            Glide.with(MovieDetailsActivity.this).load(poster_path).into(binding.imgPosterMoviedetails);

            binding.lblTitleMoviedetails.setText(movies.getTitle());
            binding.lblRuntimeMoviedetails.setText(String.valueOf(movies.getRuntime() + "'"));
            binding.lblIdMoviedetails.setText(movie_id);

            if (movies.isVideo()) {
                binding.lblVideoMoviedetails.setText(R.string.video_true);
            } else {
                binding.lblVideoMoviedetails.setText(R.string.video_false);
            }

            if (movies.isAdult()) {
                binding.lblAdultMoviedetails.setText(R.string.adult_true);
            } else {
                binding.lblAdultMoviedetails.setText(R.string.adult_false);
            }

            for (int i = 0; i < movies.getSpoken_languages().size(); i++) {
                if (i == movies.getSpoken_languages().size() - 1) {
                    spoken_language += movies.getSpoken_languages().get(i).getName();
                } else {
                    spoken_language += movies.getSpoken_languages().get(i).getName() + ", ";
                }
            }
            binding.lblLanguageMoviedetails.setText(spoken_language);

            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() - 1) {
                    movie_genre += movies.getGenres().get(i).getName();
                } else {
                    movie_genre += movies.getGenres().get(i).getName() + " | ";
                }
            }
            binding.lblGenreMoviedetails.setText(movie_genre);

            if (movies.getTagline().isEmpty()) {
                binding.lblTaglineMoviedetails.setText("-");
            } else {
                binding.lblTaglineMoviedetails.setText(movies.getTagline());
            }

            binding.lblOverviewMoviedetails.setText(movies.getOverview());

            binding.btnHomepageMoviedetails.setOnClickListener(new View.OnClickListener() {
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
            CreditsAdapter adapter = new CreditsAdapter(MovieDetailsActivity.this);
            adapter.setListNowPlaying(credits.getCast());
            binding.rvCredits.setAdapter(adapter);
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }
}