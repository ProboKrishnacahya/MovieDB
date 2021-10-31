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
import com.cahyaa.moviedb.adapter.UpComingAdapter;
import com.cahyaa.moviedb.databinding.FragmentUpComingBinding;
import com.cahyaa.moviedb.helper.ItemClickSupport;
import com.cahyaa.moviedb.model.UpComing;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class UpComingFragment extends Fragment {

    private FragmentUpComingBinding binding;

    private MovieViewModel view_model;

    UpComingAdapter adapter;

    private int page = 1;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpComingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new UpComingAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvUpComingFragment.setLayoutManager(linearLayoutManager);
        adapter.setListUpComing((new ArrayList<>()));
        binding.rvUpComingFragment.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                adapter.setLoading(true);
                loadNextDataFromApi(page);
            }
        };

        binding.rvUpComingFragment.addOnScrollListener(scrollListener);

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getUpComing(page);
        view_model.getResultGetUpComing().observe(getActivity(), showUpComing);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemClickSupport.addTo(binding.rvUpComingFragment).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getActivity(), "Upcoming Movie", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ItemClickSupport.addTo(binding.rvUpComingFragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", "" + adapter.getListUpComing().get(position).getId());
                Navigation.findNavController(v).navigate(R.id.action_upComingFragment_to_movieDetailsFragment, bundle);
            }
        });
    }

    private Observer<UpComing> showUpComing = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {
            adapter.setLoading(false);
            adapter.updateList(upComing.getResults());
        }
    };

    public void loadNextDataFromApi(int offset) {
        view_model.getUpComing(offset);
        view_model.getResultGetUpComing().observe(getActivity(), showUpComing);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}