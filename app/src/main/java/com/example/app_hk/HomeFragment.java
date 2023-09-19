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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {

    private TextView rewardTextView;
    private int reward;
    FirebaseAuth auth;
    Button logoutbtn;
    TextView ctruser;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home1, container, false);

        auth = FirebaseAuth.getInstance();
        logoutbtn = view.findViewById(R.id.logoutbtn);
        ctruser = view.findViewById(R.id.ctruser);
        user = auth.getCurrentUser();

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

            increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Increase the reward and update the TextView
                    reward++;
                    rewardTextView.setText("Reward: " + reward);

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
