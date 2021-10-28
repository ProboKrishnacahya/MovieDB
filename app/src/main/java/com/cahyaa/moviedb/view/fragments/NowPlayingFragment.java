package com.cahyaa.moviedb.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.adapter.EndlessRecyclerViewScrollListener;
import com.cahyaa.moviedb.adapter.NowPlayingAdapter;
import com.cahyaa.moviedb.databinding.FragmentNowPlayingBinding;
import com.cahyaa.moviedb.helper.ItemClickSupport;
import com.cahyaa.moviedb.model.NowPlaying;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class NowPlayingFragment extends Fragment {

    private FragmentNowPlayingBinding binding;

    private MovieViewModel view_model;

    NowPlayingAdapter adapter;

    private int page = 1;

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new NowPlayingAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvNowPlayingFragment.setLayoutManager(linearLayoutManager);
        adapter.setListNowPlaying(new ArrayList<>());
        binding.rvNowPlayingFragment.setAdapter(adapter);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                adapter.setLoading(true);
                loadNextDataFromApi(page);
            }
        };

        binding.rvNowPlayingFragment.addOnScrollListener(scrollListener);

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getNowPlaying(page);
        view_model.getResultGetNowPlaying().observe(getActivity(), showNowPlaying);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            adapter.setLoading(false);
            adapter.updateList(nowPlaying.getResults());

            ItemClickSupport.addTo(binding.rvNowPlayingFragment).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(getActivity(), "Now Playing", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            ItemClickSupport.addTo(binding.rvNowPlayingFragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", "" + nowPlaying.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_nowPlayingFragment_to_movieDetailsFragment, bundle);
                }
            });
        }
    };

    public void loadNextDataFromApi(int offset) {
        view_model.getNowPlaying(offset);
        view_model.getResultGetNowPlaying().observe(getActivity(), showNowPlaying);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}