package com.example.navdrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayersFragment extends Fragment {
    FirebaseListAdapter adapter;
    private Player player;
    ListView listView;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_players, container, false);
        Query query = FirebaseDatabase.getInstance().getReference().child("Players");
        listView = view.findViewById(R.id.listview_players);
        FirebaseListOptions<Player> options =  new FirebaseListOptions.Builder<Player>()
                .setLayout(R.layout.listview_player_item)
                .setQuery(query, Player.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                //TODO: Add "Circular"ImageView once appended in Firebase
                TextView name, price, rating, type;
                CircleImageView playerface;
                name = v.findViewById(R.id.lv_player_name);
                price = v.findViewById(R.id.lv_price);
                rating = v.findViewById(R.id.lv_rating);
                type = v.findViewById(R.id.lv_type);
                playerface = v.findViewById(R.id.lv_player_face);

                Player player = (Player)model;

                Glide.with(getActivity()).load(((Player) model).getImageUri()).into(playerface);
                name.setText(player.getName());
                price.setText("₹" + String.valueOf(player.getPrice()));
                rating.setText(String.valueOf(player.getRating()));
                type.setText(player.getType());
            }
        };
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
