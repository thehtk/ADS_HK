package com.example.app_hk;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {
    FirebaseAuth mAuth;
    /*
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), home1.class);
            startActivity(intent);
            finish();
        }
    }*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();


        TextView username =(TextView) findViewById(R.id.email);
        TextView password =(TextView) findViewById(R.id.password);


        MaterialButton signupbtn = (MaterialButton) findViewById(R.id.signupbtn);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        //admin and admin


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,passw;
                email=String.valueOf(username.getText());
                passw=String.valueOf(password.getText());

                mAuth.createUserWithEmailAndPassword(email, passw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(signup.this, "Authentication pass.",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d("Signup", "Authentication successful: " );

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(signup.this, signin.class); // Replace with the actual signup page activity name

                                    // Start the signup page activity
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(signup.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    Log.w("Signup", "Authentication failed", task.getException());

                                }
                            }
                        });

                /*
                if(email=="admin" && passw=="admin")
                { //correct
                    Toast.makeText(signup.this,"SignUp Successful",Toast.LENGTH_SHORT).show();
                }
                else
                {  //incorrect
                    Toast.makeText(signup.this,"SignUp Failed !!!",Toast.LENGTH_SHORT).show();
                }
                 */
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define an Intent to navigate to the signup page
                Intent intent = new Intent(signup.this, signin.class); // Replace with the actual signup page activity name

                // Start the signup page activity
                startActivity(intent);
            }
        });


    }
}
