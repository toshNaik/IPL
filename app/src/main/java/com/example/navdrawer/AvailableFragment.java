package com.example.navdrawer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvailableFragment extends Fragment {


    List<Player> list;
    RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    private row_adapter adapter;
    List<Integer> integers;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_available, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_players);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Products");

        list = new ArrayList<>();

        adapter = new row_adapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        progressDialog.show();

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String[] displayName = firebaseUser.getDisplayName().split(" ");
        Log.d("NavDrawer", displayName[0] + displayName[1]);
        //displayName contains the room number and team name at positions 0 and 1
        databaseReference = FirebaseDatabase.getInstance().getReference(displayName[0]).child(displayName[1]).child("Players");
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
        return view;
    }
}
