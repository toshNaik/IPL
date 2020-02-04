package com.example.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Team_details extends AppCompatActivity {

    List<Player> list;
    RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    private row_adapter adapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        String team = Team_details.this.getIntent().getStringExtra("Team Name");

        recyclerView = findViewById(R.id.recyclerView_players);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Team_details.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(Team_details.this);
        progressDialog.setMessage("Loading Products");

        list = new ArrayList<>();

        adapter = new row_adapter(Team_details.this, list);
        recyclerView.setAdapter(adapter);
        progressDialog.show();

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String[] displayName = firebaseUser.getDisplayName().split(" ");

        databaseReference = FirebaseDatabase.getInstance().getReference(displayName[0]).child(team).child("Players");
        databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            Player player_class = snapshot.getValue(Player.class);
                            list.add(player_class);
                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                }
        );


    }
}
