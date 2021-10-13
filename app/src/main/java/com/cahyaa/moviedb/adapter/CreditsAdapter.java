package com.cahyaa.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.databinding.CardCastMovieDetailsBinding;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Credits;

import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CardViewViewHolder> {

    private Context context;
    private List<Credits.Cast> listCredits;

    private List<Credits.Cast> getListCredits() {
        return listCredits;
    }

    public void setListNowPlaying(List<Credits.Cast> listCredits) {
        this.listCredits = listCredits;
    }

    public CreditsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CreditsAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cast_movie_details, parent, false);
        return new CreditsAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditsAdapter.CardViewViewHolder holder, int position) {
        CardCastMovieDetailsBinding binding = CardCastMovieDetailsBinding.bind(holder.itemView);
        final Credits.Cast cast = getListCredits().get(position);
        Glide.with(context).load(Const.IMG_URL + cast.getProfile_path()).into(binding.imgCastMoviedetails);
        binding.lblCharacterMoviedetails.setText(cast.getCharacter());
        binding.lblCastMoviedetails.setText(cast.getOriginal_name());
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
