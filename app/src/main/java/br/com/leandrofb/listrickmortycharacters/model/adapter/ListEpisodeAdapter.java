package br.com.leandrofb.listrickmortycharacters.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.leandrofb.listrickmortycharacters.R;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Episode;
import br.com.leandrofb.listrickmortycharacters.view.ListCharacterActivity;


public class ListEpisodeAdapter extends RecyclerView.Adapter<ListEpisodeAdapter.CustomViewHolder> {

    private static final String TAG = "ListEpisodeAdapter";

    private List<Episode> episodes;
    private Context mContext;

    public ListEpisodeAdapter(List<Episode> episodes, Context context) {
        this.episodes = episodes;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Episode episode = episodes.get(position);
        final String characters = episode.getCharacters().toString().replaceAll("(\\[)|(\\])", "");
        final String descEpisode = episode.getEpisode();
        holder.name.setText(episode.getName());
        holder.description.setText(episode.getEpisode());


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListCharacterActivity.class);
                intent.putExtra("CHARACTERS", characters);
                intent.putExtra("EPISODE", descEpisode);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        RelativeLayout parentLayout;

        public CustomViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
