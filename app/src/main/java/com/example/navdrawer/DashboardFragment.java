package com.example.navdrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    int key=1;
    TextView ID,Name,Price,Rating;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);

        ID=view.findViewById(R.id.ID);
        Name=view.findViewById(R.id.Name);
        Price=view.findViewById(R.id.Price);
        Rating=view.findViewById(R.id.Rating);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Key");
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
                        Player player_class=dataSnapshot.getValue(Player.class);
                        Name.setText(player_class.getName());
                        Price.setText(Integer.toString(player_class.getPrice()));
                        Rating.setText(Integer.toString(player_class.getRating()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }
}
