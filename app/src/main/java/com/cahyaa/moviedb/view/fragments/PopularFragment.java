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
import com.cahyaa.moviedb.adapter.PopularAdapter;
import com.cahyaa.moviedb.databinding.FragmentPopularBinding;
import com.cahyaa.moviedb.helper.ItemClickSupport;
import com.cahyaa.moviedb.model.Popular;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class PopularFragment extends Fragment {

    private FragmentPopularBinding binding;

    private MovieViewModel view_model;

    PopularAdapter adapter;

    private int page = 1;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPopularBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new PopularAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvPopularFragment.setLayoutManager(linearLayoutManager);
        adapter.setListPopular((new ArrayList<>()));
        binding.rvPopularFragment.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                adapter.setLoading(true);
                loadNextDataFromApi(page);
            }
        };

        binding.rvPopularFragment.addOnScrollListener(scrollListener);

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getPopular(page);
        view_model.getResultGetPopular().observe(getActivity(), showPopular);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemClickSupport.addTo(binding.rvPopularFragment).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getActivity(), "Popular Movie", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ItemClickSupport.addTo(binding.rvPopularFragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", "" + adapter.getListPopular().get(position).getId());
                Navigation.findNavController(v).navigate(R.id.action_popularFragment_to_movieDetailsFragment, bundle);
            }
        });
    }

    private Observer<Popular> showPopular = new Observer<Popular>() {
        @Override
        public void onChanged(Popular popular) {
            adapter.setLoading(false);
            adapter.updateList(popular.getResults());
        }
    };

    public void loadNextDataFromApi(int offset) {
        view_model.getPopular(offset);
        view_model.getResultGetPopular().observe(getActivity(), showPopular);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}