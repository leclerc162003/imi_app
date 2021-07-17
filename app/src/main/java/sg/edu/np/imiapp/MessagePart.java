package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagePart extends AppCompatActivity {

    public EditText messageText;
    public TextView sendToUsername;
    public ImageView sendButton;
    public String sendToUserID;
    private FirebaseAuth mAuth;
    //private DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_message);
        this.sendButton = findViewById(R.id.sendButton);
        this.messageText = findViewById(R.id.messageText);
        this.sendToUsername = findViewById(R.id.sendToUsername);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();

        Intent receive = getIntent();
//        if (receive.getStringExtra("toUID") != "nouser"){
//            sendToUsername.setText(receive.getStringExtra("toUsername"));
//            sendToUserID = receive.getStringExtra("toUID");
//            //inputLastKeyedText(sendToUserID);
//        }
//        else{
        //set username to username received from intent in Chats Page Adapter
        sendToUsername.setText(receive.getStringExtra("Username"));
        //Get UID of the receiver (the person the user is messaging)
        sendToUserID = receive.getStringExtra("UID");
        //input the last unsent keyed message by user *if any
        inputLastKeyedText(sendToUserID);

       // }
//        sendToUsername.setText(receive.getStringExtra("Username"));
//        String sendToUserID = receive.getStringExtra("UID");
//        inputLastKeyedText(sendToUserID);
        ArrayList<String> BadWords = new ArrayList<>();
        //send message
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageText.equals("")){
                    messageText.setHint("no message sent");
                }

                DatabaseReference sentmRef = mDatabase.child("SentMessages");
                DatabaseReference newMessageRef = sentmRef.push();
                SentMessages sentMessage = new SentMessages(sendToUserID, mAuth.getUid(), messageText.getText().toString());
                newMessageRef.setValue(sentMessage);

                messageText.setText("");
            }
        });

        ArrayList<SentMessages> messagesList = new ArrayList<>();

        RecyclerView rv = findViewById(R.id.rvMessage);
        MessagesChatAdapter adapter = new MessagesChatAdapter(this, messagesList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        //for horizontal
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);



        mDatabase.child("SentMessages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messagesList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren() ) {
                    SentMessages message = postSnapshot.getValue(SentMessages.class);
                    if(message.toUIDUser.contentEquals(sendToUserID) == true && message.UIDcurrentuser.contentEquals(mAuth.getUid()) == true || message.toUIDUser.contentEquals(mAuth.getUid()) == true && message.UIDcurrentuser.contentEquals(sendToUserID) == true ){
                        //userList.add(user);
                        Log.d("message from database", message.Message);
                        Log.d("sent by from database", message.UIDcurrentuser);
                        Log.d("sent to from database", message.toUIDUser);

                        //add message to list for the recyclerview
                        messagesList.add(message);
                        //notify adapter that message has been added to list
                        adapter.notifyDataSetChanged();
                        //make sure recyclerview is scrolled to latest message when message is sent
                        rv.smoothScrollToPosition(rv.getAdapter().getItemCount());
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed");
                // Failed to read value
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLastKeyedText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent receive = getIntent();
        sendToUsername.setText(receive.getStringExtra("Username"));
        String sendToUserID = receive.getStringExtra("UID");

        inputLastKeyedText(sendToUserID);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        SharedPreferences.Editor last_key = getSharedPreferences("lastkey", MODE_PRIVATE).edit();
//        Intent receive = getIntent();
//        last_key.putString("toID", receive.getStringExtra("UID"));
//        last_key.putString("lastmessage", messageText.getText().toString());
//        last_key.apply();
        saveLastKeyedText();
        saveLastUserChatted();
        Log.d("paused", "i got paused");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("destroyed", "i got destroyed");

        saveLastKeyedText();
        saveLastUserChatted();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Intent receive = getIntent();
        super.onSaveInstanceState(savedInstanceState);
        //TODO: ON DESTORY RETURN TO CHAT ACTIVITY
        // on destroy do sharedpreferences
        //in mainactivity check for that specific sharedpreferences
        //intent to this particular activity and clear shared preferences

        //TODO: tell user that no internet connection is there
        //if can download from firebase to local DB and load from localDB
    }

    public void saveLastKeyedText(){
        Intent receive = getIntent();
        SharedPreferences.Editor last_key = getSharedPreferences(receive.getStringExtra("UID"), MODE_PRIVATE).edit();
        last_key.putString(receive.getStringExtra("UID"), messageText.getText().toString());
        last_key.apply();
    }

    public void inputLastKeyedText(String uidToUser){
        SharedPreferences lastkey = getSharedPreferences(uidToUser, MODE_PRIVATE);
        String UID = lastkey.getString(uidToUser, "defaultvalue");
        if(!UID.contentEquals("defaultvalue")){
            messageText.setText(UID);
            Log.d("UID", UID);
            Log.d("uidtouser", uidToUser);
            lastkey.edit().clear().commit();
            //clears whole file
        }


    }

    public void saveLastUserChatted(){
        SharedPreferences.Editor lastUserChatted = getSharedPreferences("lastUserChatted", MODE_PRIVATE).edit();
        Intent receive = getIntent();
        lastUserChatted.putString("toID", receive.getStringExtra("UID"));
        lastUserChatted.putString("toNAME", receive.getStringExtra("Username"));
        lastUserChatted.apply();
    }


}