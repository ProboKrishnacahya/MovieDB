package com.cahyaa.moviedb.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.adapter.CreditsAdapter;
import com.cahyaa.moviedb.databinding.FragmentMovieDetailsBinding;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Credits;
import com.cahyaa.moviedb.model.Movies;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsFragment extends Fragment {

private FragmentMovieDetailsBinding binding;

    private MovieViewModel view_model;

    private String movie_id = "";
    private String spoken_language = "";
    private String movie_genre = "";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
    }

    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    private TextView lbl_movie_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        lbl_movie_id = view.findViewById(R.id.lbl_id_movie_details_fragment);
//
//        String movieId = getArguments().getString("movieId");
//        lbl_movie_id.setText(movieId);

//        Intent intent = getIntent();
//        movie_id = intent.getStringExtra("movie_id");

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getMovieById(movie_id);
        view_model.getResultGetMovieById().observe(getActivity(), showResultMovie);
        view_model.getCredits(movie_id);
        view_model.getResultGetCredits().observe(getActivity(), showResultCredits);

        return view;
    }

    private final Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            binding.toolbarMovieDetailsFragment.setTitle(movies.getTitle());

            String backdrop_path = Const.IMG_URL + movies.getBackdrop_path().toString();
            Glide.with(getActivity()).load(backdrop_path).into(binding.imgBackdropMovieDetailsFragment);

            binding.lblRatingMovieDetailsFragment.setText(movies.getVote_average() + "/10");
            binding.lblVoteCountFragment.setText(String.valueOf(movies.getVote_count()));
            binding.lblPopularityMovieDetailsFragment.setText(String.valueOf(movies.getPopularity()));

            String poster_path = Const.IMG_URL + movies.getPoster_path().toString();
            Glide.with(getActivity()).load(poster_path).into(binding.imgPosterMovieDetailsFragment);

            binding.lblTitleMovieDetailsFragment.setText(movies.getTitle());
            binding.lblRuntimeMovieDetailsFragment.setText(String.valueOf(movies.getRuntime() + "'"));
            binding.lblIdMovieDetailsFragment.setText(movie_id);

            if (movies.isVideo()) {
                binding.lblVideoMovieDetailsFragment.setText(R.string.video_true);
            } else {
                binding.lblVideoMovieDetailsFragment.setText(R.string.video_false);
            }

            if (movies.isAdult()) {
                binding.lblAdultMovieDetailsFragment.setText(R.string.adult_true);
            } else {
                binding.lblAdultMovieDetailsFragment.setText(R.string.adult_false);
            }

            for (int i = 0; i < movies.getSpoken_languages().size(); i++) {
                if (i == movies.getSpoken_languages().size() - 1) {
                    spoken_language += movies.getSpoken_languages().get(i).getName();
                } else {
                    spoken_language += movies.getSpoken_languages().get(i).getName() + ", ";
                }
            }
            binding.lblLanguageMovieDetailsFragment.setText(spoken_language);

            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() - 1) {
                    movie_genre += movies.getGenres().get(i).getName();
                } else {
                    movie_genre += movies.getGenres().get(i).getName() + " | ";
                }
            }
            binding.lblGenreMovieDetailsFragment.setText(movie_genre);

            if (movies.getTagline().isEmpty()) {
                binding.txtTaglineMovieDetailsFragment.setText("-");
            } else {
                binding.lblTaglineMovieDetailsFragment.setText(movies.getTagline());
            }

            binding.lblOverviewMovieDetailsFragment.setText(movies.getOverview());

            binding.btnHomepageMovieDetailsFragment.setOnClickListener(new View.OnClickListener() {
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
            CreditsAdapter adapter = new CreditsAdapter(getActivity());
            adapter.setListNowPlaying(credits.getCast());
            binding.rvCredits.setAdapter(adapter);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}