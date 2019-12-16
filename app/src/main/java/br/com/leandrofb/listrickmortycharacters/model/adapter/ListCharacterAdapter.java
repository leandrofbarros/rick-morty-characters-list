package br.com.leandrofb.listrickmortycharacters.model.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.leandrofb.listrickmortycharacters.R;
import br.com.leandrofb.listrickmortycharacters.model.pojo.Character;
import br.com.leandrofb.listrickmortycharacters.model.utilities.DownloadImageTask;
import br.com.leandrofb.listrickmortycharacters.view.CharacterActivity;

public class ListCharacterAdapter extends RecyclerView.Adapter<ListCharacterAdapter.CustomViewHolder> {

    private static final String TAG = "ListCharacterAdapter";

    private List<Character> characters;
    private Context mContext;

    public ListCharacterAdapter(List<Character> characters, Context context) {
        this.characters = characters;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row2, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        final Character character = characters.get(position);
        holder.name.setText(character.getName());
        holder.description.setText(character.getStatus());
        holder.setImage(character.getImage());

        if (character.getStatus().toUpperCase().equals("ALIVE")) {
            holder.imgStatus.setImageResource(R.drawable.live);
        } else if (character.getStatus().toUpperCase().equals("DEAD")) {
            holder.imgStatus.setImageResource(R.drawable.die);
        } else {
            holder.imgStatus.setImageResource(0);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CharacterActivity.class);
                intent.putExtra("CHARACTER", character.getId().toString());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return characters.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        public ImageView image, imgStatus;
        RelativeLayout parentLayout;

        public CustomViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            image = view.findViewById(R.id.image);
            imgStatus = view.findViewById(R.id.imgStatus);

        }

        private void setImage(String imageUrl) {
            new DownloadImageTask(image).execute(imageUrl);
        }

    }


}
