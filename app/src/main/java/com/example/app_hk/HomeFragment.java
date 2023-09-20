package com.example.app_hk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_hk.R;
import com.example.app_hk.signin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private TextView rewardTextView;
    private int reward;
    FirebaseAuth auth;
    Button logoutbtn;
    TextView ctruser;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home1, container, false);

        auth = FirebaseAuth.getInstance();
        logoutbtn = view.findViewById(R.id.logoutbtn);
        ctruser = view.findViewById(R.id.ctruser);
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getEmail().replace(".", "_"));

        if (user == null) {
            Intent intent = new Intent(requireContext(), signin.class);
            startActivity(intent);
            requireActivity().finish();
        } else {
            rewardTextView = view.findViewById(R.id.rewardTextView);
            Button increaseButton = view.findViewById(R.id.increaseButton);

            // Load the reward from SharedPreferences
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            reward = sharedPreferences.getInt("reward", 0);
            rewardTextView.setText("Reward: " + reward);

            // Fetch the score from Firebase Realtime Database and update the TextView
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        int score = dataSnapshot.child("score").getValue(Integer.class);
                        rewardTextView.setText("Reward: " + score);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors here
                }
            });

            increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Increment the score in Firebase Realtime Database and update the TextView
                    reward++;
                    rewardTextView.setText("Reward: " + reward);

                    // Save the updated score to Firebase Realtime Database
                    databaseReference.child("score").setValue(reward);

                    // Save the reward to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("reward", reward);
                    editor.apply();
                }
            });
        }

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(requireContext(), signin.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        return view;
    }
}
