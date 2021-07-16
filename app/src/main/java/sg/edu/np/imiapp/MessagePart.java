package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    public ScrollView scrollView;
    public LinearLayout linearLayout;
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
        sendToUsername.setText(receive.getStringExtra("Username"));
        String sendToUserID = receive.getStringExtra("UID");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

//    @Override
//    protected void onPause() {
//        super.onPause();
//    }

}