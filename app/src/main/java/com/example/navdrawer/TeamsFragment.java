package com.example.navdrawer;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private AnimationDrawable animationDrawable;
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teams, container, false);
        linearLayout = view.findViewById(R.id.linearlayoutteams);
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String[] displayName = current_user.getDisplayName().split(" ");
        Log.d("NavDrawer", displayName[0]);
        animationDrawable = (AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


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
                TextView teamClickedOn = view.findViewById(R.id.lv_team_name);
                String teamName = teamClickedOn.getText().toString();
                Intent intent = new Intent(getActivity(), Team_details.class);
                intent.putExtra("Team Name",teamName);
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
