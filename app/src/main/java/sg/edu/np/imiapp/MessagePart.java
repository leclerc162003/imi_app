package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
//                DatabaseReference postsRef = ref.child("posts");
                DatabaseReference sentmRef = mDatabase.child("SentMessages");
//
//                DatabaseReference newPostRef = postsRef.push();
                DatabaseReference newMessageRef = sentmRef.push();
//                newPostRef.setValueAsync(new Post("gracehop", "Announcing COBOL, a New Programming Language"));
                SentMessages sentMessage = new SentMessages(sendToUserID, mAuth.getUid(), messageText.getText().toString());
                newMessageRef.setValue(sentMessage);

                //SentMessages sentMessage = new SentMessages(sendToUserID, mAuth.getUid(), messageText.getText().toString());
                //mDatabase.child("SentMessages").child(sentMessage.toUIDUser).setValue(sentMessage);

                messageText.setText("");
            }
        });


        ArrayList<SentMessages> messagesList = new ArrayList<>();
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

                        messagesList.add(message);
                    }

                }

                //create user object with data obtained from database
                //User username = dataSnapshot.getValue(User.class);
                //display username and interests in UI

                //Log.d("Scheduled", username.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed");
                // Failed to read value
            }
        });

        RecyclerView rv = findViewById(R.id.rvMessage);
        MessagesChatAdapter adapter = new MessagesChatAdapter(this, messagesList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        //for horizontal
        //LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);



        //mDatabase.child("Messages").child("ReceivedMessages").setValue();








    }
}