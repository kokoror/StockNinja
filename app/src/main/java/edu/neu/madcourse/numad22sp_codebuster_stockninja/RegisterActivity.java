package edu.neu.madcourse.numad22sp_codebuster_stockninja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.neu.madcourse.numad22sp_codebuster_stockninja.models.Location;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.models.User;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameR;
    EditText passwordR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerUser(View view) {
        usernameR = findViewById(R.id.textUsernameR_input);
        passwordR = findViewById(R.id.textPasswordR_input);
        String usernameRS = usernameR.getText().toString().trim();
        String passwordRS = passwordR.getText().toString().trim();

        if(usernameRS.length() == 0 || passwordRS.length() ==0) {
            Toast.makeText(RegisterActivity.this, "Please enter a valid username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

        userRef.orderByChild("username").equalTo(usernameRS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            userRef.child(usernameRS).setValue(new User(usernameRS, passwordRS, new Location("defaultCity","defaultState","defaultCountry")));
                            Toast.makeText(RegisterActivity.this, "Register successfully.", Toast.LENGTH_SHORT).show();
                            backToLogin();
                        } else {
                            Toast.makeText(RegisterActivity.this, "This username already exist.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public void backToLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}