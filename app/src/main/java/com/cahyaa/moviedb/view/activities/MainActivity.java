package com.cahyaa.moviedb.view.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.databinding.ActivityMainBinding;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Movies;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(MainActivity.this).get(MovieViewModel.class);

        binding.btnHitMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieId = binding.tilMovieIdMain.getEditText().getText().toString().trim();
                if (movieId.isEmpty()) {
                    binding.tilMovieIdMain.setError("Please fill Movie ID Field!");
                } else {
                    binding.tilMovieIdMain.setError(null);
                }
                viewModel.getMovieById(movieId);
                viewModel.getResultGetMovieById().observe(MainActivity.this, showResultMovie);
            }
        });
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            if (movies == null) {
                binding.txtShowMain.setText(R.string.null_movie_id);
            } else {
                String title = movies.getTitle();
                String img_path = Const.IMG_URL + movies.getPoster_path().toString();
                Glide.with(MainActivity.this).load(img_path).into(binding.imgPosterMain);
                binding.txtShowMain.setText(title);
            }
        }
    };

}