package com.cahyaa.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.databinding.CardNowPlayingBinding;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Search;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CardViewViewHolder> {

    private boolean isLoading;

    private Context context;
    private List<Search.Results> listSearch;

    public List<Search.Results> getListSearch() {
        return listSearch;
    }

    public void setListSearch(List<Search.Results> listSearch) {
        this.listSearch = listSearch;
    }

    public SearchAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent, false);
        return new SearchAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.CardViewViewHolder holder, int position) {
        CardNowPlayingBinding binding = CardNowPlayingBinding.bind(holder.itemView);
        final Search.Results results = getListSearch().get(position);
        Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(binding.imgPosterCardNowplaying);
        binding.lblTitleCardNowplaying.setText(results.getTitle());
        binding.lblOverviewCardNowplaying.setText(results.getOverview());
        binding.lblReleasedateCardNowplaying.setText(results.getRelease_date());
    }

    @Override
    public int getItemCount() {
        return getListSearch().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void updateList(List<Search.Results> newList) {
        listSearch = newList;
        notifyDataSetChanged();
    }
}
