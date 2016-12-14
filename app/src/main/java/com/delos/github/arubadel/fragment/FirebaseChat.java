package com.delos.github.arubadel.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.util.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sumit on 14/12/16.
 */

public class FirebaseChat extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_firebase_chat, container, false);
        FloatingActionButton sendButton =
                (FloatingActionButton)rootView.findViewById(R.id.fab);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)rootView.findViewById(R.id.input);

                /* Read the input field and push a new instance
                  of ChatMessage to the Firebase database */
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                /* Clear the input */
                input.setText("");
            }
        });
        return rootView;

    }

}
