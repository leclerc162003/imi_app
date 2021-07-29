 package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrendingChat extends AppCompatActivity {

    ArrayList<ThreadMessage> messages = new ArrayList<>();
    String threadName;
    String interestName;
    String username;
    TextView threadNameView;
    EditText messageText;
    ImageView sendMessageBtn;
    User currentUser;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trending_chat);

        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();

        Intent receiver = getIntent();
        threadName = receiver.getStringExtra("threadName");
        interestName = receiver.getStringExtra("interestName");
        username = receiver.getStringExtra("username");
        threadNameView = findViewById(R.id.threadName);
        messageText = findViewById(R.id.sendThreadMessage);
        sendMessageBtn = findViewById(R.id.sendThreadMessageBtn);

        threadNameView.setText(threadName);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageText.getText().toString().equals("")){
                    messageText.setError("No Message Sent");
                }
                else{
                    ThreadMessage message = new ThreadMessage(threadName, interestName, username,mAuth.getUid(),messageText.getText().toString());
                    mDatabase.child("ThreadMessages").push().setValue(message);
                    messageText.setText("");
                }
            }
        });

        RecyclerView rv = findViewById(R.id.threadChatrv);
        TrendingChatAdapter adapter = new TrendingChatAdapter(this, messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        mDatabase.child("ThreadMessages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    ThreadMessage message = postSnapshot.getValue(ThreadMessage.class);
                    if(message.interestName.contentEquals(interestName) && message.threadName.contentEquals(threadName)){
                        messages.add(message);
                        adapter.notifyDataSetChanged();
                        rv.smoothScrollToPosition(rv.getAdapter().getItemCount());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}