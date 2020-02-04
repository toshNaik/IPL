package com.example.navdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    private Button admin;
    private Button player;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        admin=findViewById(R.id.admin);
        player=findViewById(R.id.player);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,Admin.class));

            }
        });

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser == null) {
                    startActivity(new Intent(StartActivity.this,LoginActivity.class));


                }

                else
                    intent = new Intent(StartActivity.this, MainActivity.class);
                    intent.putExtra("RoomKey", "room1key");
                    startActivity(intent);
            }
        });
    }
}
