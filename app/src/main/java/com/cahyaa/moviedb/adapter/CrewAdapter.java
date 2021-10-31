package com.cahyaa.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.databinding.CardCrewMovieDetailsBinding;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Credits;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CardViewViewHolder>{

    private Context context;
    private List<Credits.Crew> listCredits;

    private List<Credits.Crew> getListCredits() {
        return listCredits;
    }

    public void setListNowPlaying(List<Credits.Crew> listCredits) {
        this.listCredits = listCredits;
    }

    public CrewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CrewAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_crew_movie_details, parent, false);
        return new CrewAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewAdapter.CardViewViewHolder holder, int position) {
        CardCrewMovieDetailsBinding binding = CardCrewMovieDetailsBinding.bind(holder.itemView);
        final Credits.Crew crew = getListCredits().get(position);
        Glide.with(context).load(Const.IMG_URL + crew.getProfile_path()).into(binding.imgCrewMoviedetails);
        binding.lblCrewMoviedetails.setText(crew.getOriginal_name());
        binding.lblRoleMoviedetails.setText(crew.getJob());
    }

    @Override
    public int getItemCount() {
        return getListCredits().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
