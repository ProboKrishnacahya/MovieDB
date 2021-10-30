package com.cahyaa.moviedb.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.databinding.FragmentSplashScreenBinding;

public class SplashScreenFragment extends Fragment {

    private FragmentSplashScreenBinding binding;

    Animation bottomAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        bottomAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_animation);

        binding.imgLogoSplashScreenFragment.setAnimation(bottomAnimation);
        binding.txtLogoSplashScreenFragment.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavDirections action = SplashScreenFragmentDirections.actionSplashScreenFragmentToNowPlayingFragment();
                Navigation.findNavController(view).navigate(action);
            }
        }, 3000);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}