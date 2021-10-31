package com.cahyaa.moviedb.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.adapter.SearchAdapter;
import com.cahyaa.moviedb.databinding.FragmentSearchBinding;
import com.cahyaa.moviedb.helper.ItemClickSupport;
import com.cahyaa.moviedb.model.Search;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    private MovieViewModel view_model;

    SearchAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new SearchAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvSearchFragment.setLayoutManager(linearLayoutManager);
        adapter.setListSearch(new ArrayList<>());
        binding.rvSearchFragment.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);

        binding.searchViewSearchFragment.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                view_model.getMovieResult(s);
                view_model.getResultGetMovieResult().observe(getActivity(), showSearch);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        ItemClickSupport.addTo(binding.rvSearchFragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", "" + adapter.getListSearch().get(position).getId());
                Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_movieDetailsFragment, bundle);
            }
        });
    }

    private Observer<Search> showSearch = new Observer<Search>() {

        @Override
        public void onChanged(Search search) {
            adapter.updateList(search.getResults());
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}