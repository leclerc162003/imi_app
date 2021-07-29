package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrendingInterests extends AppCompatActivity {

    String username;
    ArrayList<String> interests;
    ArrayList<firebase_Threads> threaddata = new ArrayList<>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    TrendingInterestsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trending_interests);

        Intent receiver = getIntent();
        username = receiver.getStringExtra("username");

        interests = getInterests();

        RecyclerView rv = findViewById(R.id.trendingInterestsrv);
        adapter = new TrendingInterestsAdapter(TrendingInterests.this, interests, threaddata, username);
        LinearLayoutManager layoutManager = new LinearLayoutManager(TrendingInterests.this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        mDatabase.child("Threads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                threaddata.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    firebase_Threads thread = postSnapshot.getValue(firebase_Threads.class);
                    threaddata.add(thread);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<String> getInterests(){
        //Use database information to get interests
        ArrayList<String> interests = new ArrayList<>();

//        interests.add(new Interests("Gaming " + getEmoji(0x1F3AE)));
//        interests.add(new Interests("Singing " + getEmoji(0x1F3A4)));
//        interests.add(new Interests("Dancing " + getEmoji(0x1F483)));
//        interests.add(new Interests("Cooking " + getEmoji(0x1F373)));
//        interests.add(new Interests("K-Pop " + getEmoji(0x1F929)));
//        interests.add(new Interests("K-Drama " + getEmoji(0x1F60F)));
//        interests.add(new Interests("Netflix " + getEmoji(0x1F4FA)));
//        interests.add(new Interests("Sleeping " + getEmoji(0x1F62A)));
//        interests.add(new Interests("Sports " + getEmoji(0x1F93E)));
//        interests.add(new Interests("Anime " + getEmoji(0x1F9DD)));
//        interests.add(new Interests("Music " + getEmoji(0x1F3BC)));
//        interests.add(new Interests("Studying " + getEmoji(0x1F4D6)));
//        interests.add(new Interests("Movies & TV shows " + getEmoji(0x1F4FA)));

//        interests.add("Gaming");
//        interests.add("Singing");
//        interests.add("Dancing");
//        interests.add("Cooking");
//        interests.add("K-Pop");
//        interests.add("K-Drama");
//        interests.add("Netflix");
//        interests.add("Sleeping");
//        interests.add("Sports");
//        interests.add("Anime");
//        interests.add("Music");
//        interests.add("Studying");
//        interests.add("Movies & TV shows");

        mDatabase.child("Interests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    String interest = postSnapshot.getValue(String.class);
                    interests.add(interest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return interests;
    }

    public String getEmoji(int uni){
        return new String(Character.toChars(uni));
    }
}