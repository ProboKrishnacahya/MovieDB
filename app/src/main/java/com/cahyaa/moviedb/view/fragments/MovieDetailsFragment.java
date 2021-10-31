package com.cahyaa.moviedb.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.adapter.CreditsAdapter;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Credits;
import com.cahyaa.moviedb.model.Movies;
import com.cahyaa.moviedb.model.Videos;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Objects;

public class MovieDetailsFragment extends Fragment {

    private MovieViewModel view_model;

    private String movie_id = "";
    private String spoken_language = "";
    private String movie_genre = "";

    private TextView lbl_rating, lbl_vote_count, lbl_popularity, lbl_title, lbl_release_date, lbl_runtime, lbl_id, lbl_language, lbl_genre, lbl_tagline, lbl_overview;
    private ImageView img_backdrop, img_poster;
    private YouTubePlayerView youtube_player_view;
    private RecyclerView rv_credits;
    private LinearLayout linearLayout_production_companies;
    private Button btn_homepage_movie_details_fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        img_backdrop = view.findViewById(R.id.img_backdrop_movie_details_fragment);
        lbl_rating = view.findViewById(R.id.lbl_rating_movie_details_fragment);
        lbl_vote_count = view.findViewById(R.id.lbl_vote_count_fragment);
        lbl_popularity = view.findViewById(R.id.lbl_popularity_movie_details_fragment);
        img_poster = view.findViewById(R.id.img_poster_movie_details_fragment);
        lbl_title = view.findViewById(R.id.lbl_title_movie_details_fragment);
        lbl_release_date = view.findViewById(R.id.lbl_release_date_movie_details_fragment);
        lbl_runtime = view.findViewById(R.id.lbl_runtime_movie_details_fragment);
        lbl_id = view.findViewById(R.id.lbl_id_movie_details_fragment);
        youtube_player_view = view.findViewById(R.id.youtube_player_view_movie_details_fragment);
        lbl_language = view.findViewById(R.id.lbl_language_movie_details_fragment);
        lbl_genre = view.findViewById(R.id.lbl_genre_movie_details_fragment);
        lbl_tagline = view.findViewById(R.id.lbl_tagline_movie_details_fragment);
        lbl_overview = view.findViewById(R.id.lbl_overview_movie_details_fragment);
        rv_credits = view.findViewById(R.id.rv_credits_fragment);
        linearLayout_production_companies = view.findViewById(R.id.linearLayout_production_companies_movie_details_fragment);
        btn_homepage_movie_details_fragment = view.findViewById(R.id.btn_homepage_movie_details_fragment);

        movie_id = getArguments().getString("movieId");

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getMovieById(movie_id);
        view_model.getResultGetMovieById().observe(getActivity(), showResultMovie);
        view_model.getCredits(movie_id);
        view_model.getResultGetCredits().observe(getActivity(), showResultCredits);
        view_model.getVideos(movie_id);
        view_model.getResultGetVideos().observe(getActivity(), showResultVideos);

        return view;
    }

    private final Observer<Movies> showResultMovie = new Observer<Movies>() {
        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onChanged(Movies movies) {
            if (movies.getBackdrop_path() != null) {
                String backdrop_path = Const.IMG_URL + movies.getBackdrop_path();
                Glide.with(getActivity()).load(backdrop_path).into(img_backdrop);
            } else {
                Glide.with(getActivity()).load(R.drawable.backdrop).into(img_backdrop);
            }

            lbl_rating.setText(movies.getVote_average() + "/10");
            lbl_vote_count.setText(String.valueOf(movies.getVote_count()));
            lbl_popularity.setText(String.valueOf(movies.getPopularity()));

            if (movies.getPoster_path() != null) {
                String poster_path = Const.IMG_URL + movies.getPoster_path().toString();
                Glide.with(getActivity()).load(poster_path).into(img_poster);
            } else {
                Glide.with(getActivity()).load(R.drawable.poster_backdrop).into(img_poster);
            }

            lbl_title.setText(movies.getTitle());
            lbl_release_date.setText(movies.getRelease_date());
            lbl_runtime.setText(movies.getRuntime() + "'");
            lbl_id.setText(movie_id);

            for (int i = 0; i < movies.getSpoken_languages().size(); i++) {
                if (i == movies.getSpoken_languages().size() - 1) {
                    spoken_language += movies.getSpoken_languages().get(i).getName();
                } else {
                    spoken_language += movies.getSpoken_languages().get(i).getName() + ", ";
                }
            }
            lbl_language.setText(spoken_language);

            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() - 1) {
                    movie_genre += movies.getGenres().get(i).getName();
                } else {
                    movie_genre += movies.getGenres().get(i).getName() + " | ";
                }
            }
            lbl_genre.setText(movie_genre);

            if (movies.getTagline().isEmpty()) {
                lbl_tagline.setText("-");
            } else {
                lbl_tagline.setText(movies.getTagline());
            }

            if (movies.getOverview().isEmpty()) {
                lbl_overview.setText("-");
            } else {
                lbl_overview.setText(movies.getOverview());
            }

            btn_homepage_movie_details_fragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(movies.getHomepage())));
                }
            });

            for (int i = 0; i < movies.getProduction_companies().size(); i++) {
                ImageView img_production_companies = new ImageView(linearLayout_production_companies.getContext());
                String production_companies_logo = Const.IMG_URL + movies.getProduction_companies().get(i).getLogo_path();
                String production_companies_name = movies.getProduction_companies().get(i).getName();
                if (movies.getProduction_companies().get(i).getLogo_path() == null) {
                    img_production_companies.setImageDrawable(getResources().getDrawable(R.drawable.production_companies, getActivity().getTheme()));
                } else if (!Objects.equals(production_companies_logo, "https://image.tmdb.org/3/t/p/w500/null")) {
                    Glide.with(getActivity()).load(production_companies_logo).into(img_production_companies);
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(300, 300);
                lp.setMargins(0, 0, 16, 0);
                img_production_companies.setLayoutParams(lp);
                linearLayout_production_companies.addView(img_production_companies);
                img_production_companies.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getContext(), production_companies_name, Toast.LENGTH_SHORT);
                        toast.setText(production_companies_name);
                        toast.show();
                    }
                });
            }
        }
    };

    private Observer<Credits> showResultCredits = new Observer<Credits>() {
        @Override
        public void onChanged(Credits credits) {
            CreditsAdapter adapter = new CreditsAdapter(getActivity());
            adapter.setListNowPlaying(credits.getCast());
            rv_credits.setAdapter(adapter);
        }
    };

    private Observer<Videos> showResultVideos = new Observer<Videos>() {
        @Override
        public void onChanged(Videos videos) {
            getLifecycle().addObserver(youtube_player_view);

            youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    if (!videos.getResults().isEmpty()) {
                        String videoId = videos.getResults().get(0).getKey();
                        youTubePlayer.loadVideo(videoId, 0);
                    } else {
                        youtube_player_view.setVisibility(View.GONE);
                    }
                }
            });
        }
    };

}