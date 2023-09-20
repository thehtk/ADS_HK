package com.example.app_hk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LibraryFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get current user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Get TextViews

        TextView userEmailTextView = view.findViewById(R.id.userEmail);

        if (currentUser != null) {
            // User is signed in

            userEmailTextView.setText(currentUser.getEmail());
        } else {
            // No user is signed in

            userEmailTextView.setText("");
        }

        return view;
    }
}
