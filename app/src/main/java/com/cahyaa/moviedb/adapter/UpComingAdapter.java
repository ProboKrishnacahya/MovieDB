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
import com.cahyaa.moviedb.model.UpComing;

import java.util.List;

public class UpComingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isLoading;

    private Context context;
    private List<UpComing.Results> listUpComing;

    public List<UpComing.Results> getListUpComing() {
        return listUpComing;
    }

    public void setListUpComing(List<UpComing.Results> listUpComing) {
        this.listUpComing = listUpComing;
    }

    public UpComingAdapter(Context context) {
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
            viewHolder = new UpComingAdapter.CardViewViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        int view;
        if (isLoading) {
            view = R.layout.layout_shimmer_effect;
        } else {
            view = R.layout.card_up_coming;
        }
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final UpComing.Results results = getListUpComing().get(position);
        if (holder instanceof CardViewViewHolder) {
            ((CardViewViewHolder) holder).lbl_title.setText(results.getTitle());
            ((CardViewViewHolder) holder).lbl_overview.setText(results.getOverview());
            ((CardViewViewHolder) holder).lbl_release_date.setText(results.getRelease_date());
            Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(((CardViewViewHolder) holder).img_poster);
        }
    }

    @Override
    public int getItemCount() {
        return getListUpComing().size();
    }

    public static class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView lbl_title, lbl_overview, lbl_release_date;
        CardView cv;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_poster_card_upcoming);
            lbl_title = itemView.findViewById(R.id.lbl_title_card_upcoming);
            lbl_overview = itemView.findViewById(R.id.lbl_overview_card_upcoming);
            lbl_release_date = itemView.findViewById(R.id.lbl_releasedate_card_upcoming);
            cv = itemView.findViewById(R.id.cv_card_upcoming);
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

    public void updateList(List<UpComing.Results> newList) {
        listUpComing.addAll(newList);
        notifyDataSetChanged();
    }
}
