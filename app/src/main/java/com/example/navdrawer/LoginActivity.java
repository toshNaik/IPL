package com.example.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginActivity extends AppCompatActivity {

    private EditText roomKey,team,email,password, roomNumber;
    private Button Login;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        roomKey = findViewById(R.id.login_roomkey);
        Login = findViewById(R.id.login_button);
        roomNumber = findViewById(R.id.login_roomno);
        team=findViewById(R.id.team_name);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        mAuth=FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email1=email.getText().toString();
                String password1=password.getText().toString();
                String roomkey = roomKey.getText().toString();
                final String team_name = team.getText().toString();
                final String room_no = roomNumber.getText().toString();
                Log.e("NavDrawer", email1 +" " + password1);
                mAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                                        .setDisplayName(room_no + " " + team_name).build();

                                user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Profile updated", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }

                );

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("RoomKey", roomkey);
                startActivity(intent);

            }
        });
    }
}
