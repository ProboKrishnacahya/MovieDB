package com.cahyaa.moviedb.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.adapter.UpComingAdapter;
import com.cahyaa.moviedb.databinding.FragmentUpComingBinding;
import com.cahyaa.moviedb.helper.ItemClickSupport;
import com.cahyaa.moviedb.model.UpComing;
import com.cahyaa.moviedb.viewmodel.MovieViewModel;

public class UpComingFragment extends Fragment {

    private FragmentUpComingBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UpComingFragment() {
    }

    public static UpComingFragment newInstance(String param1, String param2) {
        UpComingFragment fragment = new UpComingFragment();
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
        binding = FragmentUpComingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getUpComing();
        view_model.getResultGetUpComing().observe(getActivity(), showUpComing);

        return view;
    }

    private Observer<UpComing> showUpComing = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {
            binding.rvUpComingFragment.setLayoutManager(new LinearLayoutManager(getActivity()));
            UpComingAdapter adapter = new UpComingAdapter(getActivity());
            adapter.setListUpComing(upComing.getResults());
            binding.rvUpComingFragment.setAdapter(adapter);

            ItemClickSupport.addTo(binding.rvUpComingFragment).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(getActivity(), "Upcoming", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            ItemClickSupport.addTo(binding.rvUpComingFragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", "" + upComing.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_upComingFragment_to_movieDetailsFragment, bundle);
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