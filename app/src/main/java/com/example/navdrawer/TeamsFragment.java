package com.example.navdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamsFragment extends Fragment {
    FirebaseListAdapter adapter;
    ListView listView;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teams, container, false);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String[] displayName = current_user.getDisplayName().split(" ");
        Log.d("NavDrawer", displayName[0]);



        Query query = FirebaseDatabase.getInstance().getReference().child(displayName[0]);
        listView = view.findViewById(R.id.listview_teams);
        FirebaseListOptions<Team> options =  new FirebaseListOptions.Builder<Team>()
                .setLayout(R.layout.listview_teams_item)
                .setQuery(query, Team.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView team_name, budget;
                Team team = (Team)model;
                team_name = v.findViewById(R.id.lv_team_name);
                budget = v.findViewById(R.id.lv_team_budget);
                team_name.setText(team.getName());
                budget.setText("₹" + String.valueOf(team.getBudget()));
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                //intent.putExtra("Team",)
                startActivity(intent);
            }
        });
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
