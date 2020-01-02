package com.example.navdrawer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UnsoldFragment extends Fragment {

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
        View view =inflater.inflate(R.layout.fragment_unsold, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_players);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        //Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Products");

        list = new ArrayList<>();

        adapter = new row_adapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Unsold Players");
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
