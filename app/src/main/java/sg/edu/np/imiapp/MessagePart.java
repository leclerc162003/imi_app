package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MessagePart extends AppCompatActivity {

    public EditText messageText;
    public TextView sendToUsername;
    public ImageView sendButton;
    public String sendToUserID;
    private FirebaseAuth mAuth;
    public StorageReference storageReference;
    public StorageReference pathReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_message);
        this.sendButton = findViewById(R.id.sendButton);
        this.messageText = findViewById(R.id.messageText);
        this.sendToUsername = findViewById(R.id.sendToUsername);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
        ImageView imagePFP = findViewById(R.id.pfpChat);


        Intent receive = getIntent();
        String UsernameReceiver;
        String pfp;
        //get user UID from login if shared pref is not null, if null get string from chatspage adapter
        if (receive.getStringExtra("toUID")!= null){
            Log.d("username NNOWNNWONW", receive.getStringExtra("toUsername"));
            UsernameReceiver = receive.getStringExtra("toUsername");
            sendToUserID = receive.getStringExtra("toUID");
            pfp = receive.getStringExtra("toPFP");
        }
        else{
            UsernameReceiver = receive.getStringExtra("Username");
            sendToUserID = receive.getStringExtra("UID");
            pfp = receive.getStringExtra("imagePFP");
        }
        //input reciever UID and input last keyed text (if any**)
        sendToUsername.setText(UsernameReceiver);
        inputLastKeyedText(sendToUserID);
        storageReference = FirebaseStorage.getInstance().getReference();
        pathReference = storageReference.child("Default Images/" + pfp);
        GlideApp.with(MessagePart.this)
                .load(pathReference)
                .into(imagePFP);
        //send message
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if message is empty cannot send
                if(messageText.getText().toString().equals("")) {
                    messageText.setError("no message sent");
                }
                //check for unfriendly messages
                else if (checkUnfriendlyMessages(messageText.getText().toString()) == true){
                    messageText.setError("unfriendly messages not allowed.");
                }

                else{
                    //reference to database "sentmessages"
                    DatabaseReference sentmRef = mDatabase.child("SentMessages");
                    DatabaseReference newMessageRef = sentmRef.push();
                    //create message object with sendToUserID (the person receiving the message), mAuth.getUid() (Current User) and the message
                    SentMessages sentMessage = new SentMessages(sendToUserID, mAuth.getUid(), messageText.getText().toString());
                    //post message object to database
                    newMessageRef.setValue(sentMessage);
                    //set edittext back to empty
                    messageText.setText("");
                }

            }
        });
        //create array list to store messages from database for the recycler view
        ArrayList<SentMessages> messagesList = new ArrayList<>();
        //find recyclerview and give messagesList to adapter to display
        RecyclerView rv = findViewById(R.id.rvMessage);
        MessagesChatAdapter adapter = new MessagesChatAdapter(this, messagesList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        //for horizontal
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);


        //if, or when a message is send, a message will be collected from the database to add to the list
        mDatabase.child("SentMessages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messagesList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren() ) {
                    //convert data got from database to a message object
                    SentMessages message = postSnapshot.getValue(SentMessages.class);
                    Log.d("AM I NULL", sendToUserID);
                    Log.d("AM I NULL", message.UIDcurrentuser);
                    //if the toUIDUser (person receiving the message) and the current userID matches OR
                    //toUIDuser matches the current user and currentuserID matches the person receiving the message add message to list
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
    protected void onStop() {
        super.onStop();
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


    public void saveLastKeyedText(){
        //save the last keyed message to a sharedpreferences file named after the UID and put the message in the file
        Intent receive = getIntent();
        SharedPreferences.Editor last_key = getSharedPreferences(receive.getStringExtra("UID"), MODE_PRIVATE).edit();
        last_key.putString(receive.getStringExtra("UID"), messageText.getText().toString());
        last_key.apply();
    }

    public void inputLastKeyedText(String uidToUser){
        //get the message from the shared preferences file, if the UID does not contain defaultvalue, set message to string and clear file.
        SharedPreferences lastkey = getSharedPreferences(uidToUser, MODE_PRIVATE);
        String UID = lastkey.getString(uidToUser, "defaultvalue");
        if(!UID.contentEquals("defaultvalue")){
            messageText.setText(UID);
            Log.d("UID", UID);
            Log.d("uidtouser", uidToUser);
            lastkey.edit().clear().commit();
        }


    }

    public void saveLastUserChatted(){
        SharedPreferences.Editor lastUserChatted = getSharedPreferences("lastUserChatted", MODE_PRIVATE).edit();
        Intent receive = getIntent();
        lastUserChatted.putString("toID", receive.getStringExtra("UID"));
        lastUserChatted.putString("toNAME", receive.getStringExtra("Username"));
        lastUserChatted.putString("toPFP", receive.getStringExtra("imagePFP"));
        lastUserChatted.apply();
    }

    public boolean checkUnfriendlyMessages(String message){
        ArrayList<String> BadWords = new ArrayList<>();
        BadWords.add("fuck");
        BadWords.add("sex");
        BadWords.add("horny");
        BadWords.add("69");
        BadWords.add("die");
        BadWords.add("faggot");
        BadWords.add("pussy");
        BadWords.add("dick");
        BadWords.add("send nudes");
        message.toLowerCase();
        for (int i = 0; i < BadWords.size() ; i++){
            if (message.contains(BadWords.get(i))){
                Log.d(message, BadWords.get(i));
                return true;
            }

        }
        return false;

    }
}