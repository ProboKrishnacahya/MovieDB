package com.cahyaa.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cahyaa.moviedb.R;
import com.cahyaa.moviedb.helper.Const;
import com.cahyaa.moviedb.model.Popular;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isLoading;

    private Context context;
    private List<Popular.Results> listPopular;

    public List<Popular.Results> getListPopular() {
        return listPopular;
    }

    public void setListPopular(List<Popular.Results> listPopular) {
        this.listPopular = listPopular;
    }

    public PopularAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        RecyclerView.ViewHolder viewHolder;
        if (isLoading) {
            viewHolder = new LoadingViewHolder(view);
        } else {
            viewHolder = new PopularAdapter.CardViewViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        int view;
        if (isLoading) {
            view = R.layout.layout_shimmer_effect;
        } else {
            view = R.layout.card_popular;
        }
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Popular.Results results = getListPopular().get(position);
        if (holder instanceof CardViewViewHolder) {
            ((CardViewViewHolder) holder).lbl_title.setText(results.getTitle());
            ((CardViewViewHolder) holder).lbl_overview.setText(results.getOverview());
            ((CardViewViewHolder) holder).lbl_release_date.setText(results.getRelease_date());
            Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(((CardViewViewHolder) holder).img_poster);
        }
    }

    @Override
    public int getItemCount() {
        return getListPopular().size();
    }

    public static class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView lbl_title, lbl_overview, lbl_release_date;
        CardView cv;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_poster_card_popular);
            lbl_title = itemView.findViewById(R.id.lbl_title_card_popular);
            lbl_overview = itemView.findViewById(R.id.lbl_overview_card_popular);
            lbl_release_date = itemView.findViewById(R.id.lbl_releasedate_card_popular);
            cv = itemView.findViewById(R.id.cv_card_popular);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        notifyDataSetChanged();
    }

    public void updateList(List<Popular.Results> newList) {
        listPopular.addAll(newList);
        notifyDataSetChanged();
    }
}
