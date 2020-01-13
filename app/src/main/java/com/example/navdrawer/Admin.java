package com.example.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

interface FirebaseCallback {
    void OnCallback(int value);
}

public class Admin extends AppCompatActivity {

    EditText keys, team_budget, player_price, roomkey;
    public int key;
    private Button change_btn, apply_btn;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private int budget,final_price,d;
    String assign;
    String room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        roomkey = findViewById(R.id.roomkey);
        change_btn =findViewById(R.id.change_btn);
        keys=findViewById(R.id.keys);
        apply_btn =findViewById(R.id.apply_btn);
        radioGroup=findViewById(R.id.radio_group);
        team_budget =findViewById(R.id.team_budget);
        player_price =findViewById(R.id.player_price);

        room="room1";


        change_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key = Integer.parseInt(keys.getText().toString());
                        String roomKey = roomkey.getText().toString();
                        display();
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference(roomKey);
                        reference.setValue(key);
                    }
                }
        );

        apply_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int radioId = radioGroup.getCheckedRadioButtonId();
                        radioButton = findViewById(radioId);
                        assign = radioButton.getText().toString();
                        assign_player();
                        //change_budget();
                    }
                }
        );
    }

    private void change_budget() {
        /*DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Players").child(Integer.toString(key)).child("price");
        databaseReference1.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        d = dataSnapshot.getValue(Integer.class);
                        Log.d("NavDrawer", Integer.toString(d));
                        player_price.setText(Integer.toString(d));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );*/

        if(player_price.getText() != null)
            d = Integer.parseInt(player_price.getText().toString());

        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Teams").child(assign).child("Budget");
        databaseReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        budget = dataSnapshot.getValue(Integer.class);
                        Log.d("NavDrawer budget", Integer.toString(budget));
                        team_budget.setText(Integer.toString(budget));
                        databaseReference.setValue(budget-d);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    private void assign_player() {
        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference(room).child(assign);
        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Players").child(Integer.toString(key));
        databaseReference3.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Player player_class = dataSnapshot.getValue(Player.class);
                        databaseReference1.child("Players").child(Integer.toString(key)).setValue(player_class).addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //Toast.makeText(Admin.this, "Player Added", Toast.LENGTH_SHORT).show();

                                    }
                                }
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    private void display() {
        Toast.makeText(this, "Key is : "+key, Toast.LENGTH_SHORT).show();
    }
}
