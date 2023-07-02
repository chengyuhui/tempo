package com.cappielloantonio.tempo.ui.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cappielloantonio.tempo.databinding.ItemLibraryMusicIndexBinding;
import com.cappielloantonio.tempo.glide.CustomGlideRequest;
import com.cappielloantonio.tempo.interfaces.ClickCallback;
import com.cappielloantonio.tempo.subsonic.models.Artist;
import com.cappielloantonio.tempo.util.Constants;

import java.util.Collections;
import java.util.List;

@UnstableApi
public class MusicIndexAdapter extends RecyclerView.Adapter<MusicIndexAdapter.ViewHolder> {
    private final ClickCallback click;

    private List<Artist> artists;

    public MusicIndexAdapter(ClickCallback click) {
        this.click = click;
        this.artists = Collections.emptyList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLibraryMusicIndexBinding view = ItemLibraryMusicIndexBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artist artist = artists.get(position);

        holder.item.musicIndexTitleTextView.setText(artist.getName());

        CustomGlideRequest.Builder
                .from(holder.itemView.getContext(), artist.getName())
                .build()
                .into(holder.item.musicIndexCoverImageView);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void setItems(List<Artist> artists) {
        this.artists = artists;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLibraryMusicIndexBinding item;

        ViewHolder(ItemLibraryMusicIndexBinding item) {
            super(item.getRoot());

            this.item = item;

            item.musicIndexTitleTextView.setSelected(true);

            itemView.setOnClickListener(v -> onClick());
            item.musicIndexMoreButton.setOnClickListener(v -> onClick());
        }

        public void onClick() {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.MUSIC_DIRECTORY_ID, artists.get(getBindingAdapterPosition()).getId());
            click.onMusicIndexClick(bundle);
        }
    }
}
