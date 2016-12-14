package com.delos.github.arubadel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.util.ChatMessage;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sumit on 14/12/16.
 */

public class FirebaseChat extends Fragment {
    private FirebaseListAdapter<ChatMessage> adapter;
    private ListView listOfMessages;
    private InputMethodManager imm;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_firebase_chat, container, false);
        listOfMessages = (ListView)rootView.findViewById(R.id.list_of_messages);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

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
                        .child("Chats")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getEmail())
                        );

                /* Clear the input */
                input.setText("");
                /*hide text*/
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }

        });

        displayChatMessages();
        return rootView;

    }
    private void displayChatMessages(){
        adapter = new FirebaseListAdapter<ChatMessage>(getActivity(), ChatMessage.class,R.layout.fragment_firebase_chat_textview, FirebaseDatabase.getInstance().getReference().child("Chats")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml

                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

    listOfMessages.setAdapter(adapter);
    }

}
