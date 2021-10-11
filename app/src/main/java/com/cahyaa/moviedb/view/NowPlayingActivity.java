package com.cahyaa.moviedb.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.adapter.NowPlayingAdapter;
import com.cahyaa.moviedb.model.NowPlaying;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

public class NowPlayingActivity extends AppCompatActivity {

    private RecyclerView rv_now_playing;
    private MovieViewModel view_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        rv_now_playing = findViewById(R.id.rv_now_playing);

        view_model = new ViewModelProvider(NowPlayingActivity.this).get(MovieViewModel.class);
        view_model.getNowPlaying();
        view_model.getResultGetNowPlaying().observe(NowPlayingActivity.this, showNowPlaying);
    }

    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            rv_now_playing.setLayoutManager(new LinearLayoutManager(NowPlayingActivity.this));
            NowPlayingAdapter adapter = new NowPlayingAdapter(NowPlayingActivity.this);
            adapter.setListNowPlaying(nowPlaying.getResults());
            rv_now_playing.setAdapter(adapter);
        }
    };

}