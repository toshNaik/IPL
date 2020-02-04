package com.example.navdrawer;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    int key=1;
    TextView ID,Name,Price,Rating;
    private AnimationDrawable animationDrawable;
    ImageView playerback;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);

        ID=view.findViewById(R.id.ID);
        Name=view.findViewById(R.id.Name);
        Price=view.findViewById(R.id.Price);
        Rating=view.findViewById(R.id.Rating);
        linearLayout = view.findViewById(R.id.linearl);
        playerback = view.findViewById(R.id.backimg);
        animationDrawable = (AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        String roomkey = getActivity().getIntent().getStringExtra("RoomKey");


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(roomkey);
        reference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        key=dataSnapshot.getValue(Integer.class);
                        //ID.setText(Integer.toString(key));
                        display(key);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );


        return view;
    }

    private void display(int key1) {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Players").child(Integer.toString(key1));
        databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Player player_class = dataSnapshot.getValue(Player.class);
                        Name.setText(player_class.getName());
                        Price.setText(Integer.toString(player_class.getPrice()));
                        Rating.setText(Integer.toString(player_class.getRating()));
                        Glide.with(getContext()).load(player_class.getImageUri()).circleCrop().into(playerback);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }
}
