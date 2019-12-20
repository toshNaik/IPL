package com.example.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class Admin extends AppCompatActivity {

    EditText keys, budget1, price1,roomkey;
    public int key;
    private Button button,apply;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private int budget,final_price,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        roomkey = findViewById(R.id.roomkey);
        button=findViewById(R.id.change);
        keys=findViewById(R.id.keys);
        apply=findViewById(R.id.apply);
        radioGroup=findViewById(R.id.radio_group);
        budget1=findViewById(R.id.budget);
        price1=findViewById(R.id.price);

        apply.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int radioId=radioGroup.getCheckedRadioButtonId();
                        radioButton=findViewById(radioId);
                        String assign=radioButton.getText().toString();
                        assign_player(assign);
                        change_budget(assign);
                    }
                }
        );

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key=Integer.parseInt(keys.getText().toString());
                        String roomKey = roomkey.getText().toString();
                        display();
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference(roomKey);
                        reference.setValue(key);
                    }
                }
        );
    }

    private void change_budget(String a) {
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Teams").child(a).child("Budget");
        databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        budget=dataSnapshot.getValue(Integer.class);
                        budget1.setText(Integer.toString(budget));
                        //Toast.makeText(Admin.this, "Budget is: "+budget, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Players").child(Integer.toString(key)).child("Price");
        databaseReference1.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        d=dataSnapshot.getValue(Integer.class);
                        price1.setText(Integer.toString(d));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        String c=budget1.getText().toString();
        //int m=Integer.parseInt(price1.getText().toString());
        Toast.makeText(this, "Price is : "+c, Toast.LENGTH_SHORT).show();
    }

    private void assign_player(String assign) {
        final String a = assign;
        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Teams").child(assign);
        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Players").child(Integer.toString(key));
        databaseReference3.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Player player_class = dataSnapshot.getValue(Player.class);
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
