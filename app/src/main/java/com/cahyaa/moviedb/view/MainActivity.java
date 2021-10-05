package com.cahyaa.moviedb.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Movies;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private TextInputLayout til_movie_id;
    private Button btn_hit;
    private ImageView img_poster;
    private TextView txt_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        til_movie_id = findViewById(R.id.til_movie_id_main);
        img_poster = findViewById(R.id.img_poster_main);
        txt_show = findViewById(R.id.txt_show_main);
        viewModel = new ViewModelProvider(MainActivity.this).get(MovieViewModel.class);

        btn_hit = findViewById(R.id.btn_hit_main);
        btn_hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieId = til_movie_id.getEditText().getText().toString().trim();
                if (movieId.isEmpty()) {
                    til_movie_id.setError("Please fill Movie ID Field!");
                } else {
                    til_movie_id.setError(null);
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
                txt_show.setText(R.string.null_movie_id);
            } else {
                String title = movies.getTitle();
                String img_path = Const.IMG_URL + movies.getPoster_path().toString();
                Glide.with(MainActivity.this).load(img_path).into(img_poster);
                txt_show.setText(title);
            }
        }
    };

}