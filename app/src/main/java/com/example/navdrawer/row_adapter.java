package com.example.navdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class row_adapter extends RecyclerView.Adapter<Priyav>{

    private Context context;
    private List<Player> list;

    public row_adapter(Context context, List<Player> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public Priyav onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.player_row_item,viewGroup,false);
        return new Priyav(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull Priyav player, int i) {
        player.name.setText(list.get(i).getName());
        player.rating.setText(Integer.toString(list.get(i).getRating()));
        player.price.setText(Integer.toString(list.get(i).getPrice()));
        player.type.setText(list.get(i).getType());
        Glide.with(context).load(list.get(i).getImageUri()).into(player.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


class Priyav extends RecyclerView.ViewHolder {

    TextView name,rating,price,type;
    //ImageView imageView;
    CircleImageView imageView;

    public Priyav(@NonNull View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.lv_player_name);
        price=itemView.findViewById(R.id.lv_price);
        rating=itemView.findViewById(R.id.lv_rating);
        type=itemView.findViewById(R.id.lv_type);
        imageView=itemView.findViewById(R.id.lv_player_face);
    }
}