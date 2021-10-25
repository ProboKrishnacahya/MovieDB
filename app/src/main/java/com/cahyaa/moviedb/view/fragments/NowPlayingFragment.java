package com.cahyaa.moviedb.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.adapter.NowPlayingAdapter;
import com.cahyaa.moviedb.databinding.FragmentNowPlayingBinding;
import com.cahyaa.moviedb.helper.ItemClickSupport;
import com.cahyaa.moviedb.model.NowPlaying;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

public class NowPlayingFragment extends Fragment {

    private FragmentNowPlayingBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
    }

    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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

    private MovieViewModel view_model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getNowPlaying();
        view_model.getResultGetNowPlaying().observe(getActivity(), showNowPlaying);

        return view;
    }

    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            binding.rvNowPlayingFragment.setLayoutManager(new LinearLayoutManager(getActivity()));
            NowPlayingAdapter adapter = new NowPlayingAdapter(getActivity());
            adapter.setListNowPlaying(nowPlaying.getResults());
            binding.rvNowPlayingFragment.setAdapter(adapter);

            ItemClickSupport.addTo(binding.rvNowPlayingFragment).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                    return false;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}