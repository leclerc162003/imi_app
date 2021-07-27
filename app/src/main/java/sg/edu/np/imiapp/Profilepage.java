package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Profilepage extends AppCompatActivity {
    public TextView usernameHome;
    public ImageView profilePic;
    //initialise firebase authentication
    private FirebaseAuth mAuth;
    //initialise firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    //public String username;
    ArrayList<String> interestList;
    Context context;
    public StorageReference storageReference;
    public StorageReference pathReference;
    public String profilePicURL;
    public User username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profilepage);
        //get username textview in activity_homepage.xml
        this.usernameHome = findViewById(R.id.usernameHome);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
        this.usernameHome = findViewById(R.id.usernameHome);
        this.profilePic = findViewById(R.id.profilePic);


        // read from firebase database table "Users"
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //create user object with data obtained from database
                username = dataSnapshot.child(mAuth.getUid()).getValue(User.class);
                profilePicURL = username.getProfilePic();
                Log.d("profile", profilePicURL);
                //get images to display
                storageReference = FirebaseStorage.getInstance().getReference();
                pathReference = storageReference.child("Default Images/" + profilePicURL);
                GlideApp.with(Profilepage.this)
                        .load(pathReference)
                        .into(profilePic);

                //display username and interests in UI
                usernameHome.setText(username.getUsername());
                interestList = username.getInterests();
                RecyclerView rv = findViewById(R.id.interestsrv);
                InterestAdapter adapter = new InterestAdapter(context, interestList);
                LinearLayoutManager lm = new LinearLayoutManager(context);
                rv.setLayoutManager(lm);
                rv.setAdapter(adapter);


                Log.d("Scheduled", username.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        TextView interestText = findViewById(R.id.interestTxt);
        //if clicked directs user to update their interests
        interestText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("userUIDCurrent", username.getUID());
                extras.putString("userNameCurrent", username.getUsername());
                extras.putString("userPFPCurrent", username.getProfilePic());
                extras.putStringArrayList("userINTCurrent", username.getInterests());

                Intent i = new Intent(Profilepage.this, updateInterests.class);
                i.putExtras(extras);
                Profilepage.this.startActivity(i);
            }
        });




        ///Can remove CAN REMOVE AH JUST CODED TO TEST THE APP CAN REMOVE!! NOT RELEVANT CODE
        ImageView chatButton = findViewById(R.id.chatButton);
        //directs user to chat page
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profilepage.this, Chats_Page.class);
                i.putExtra("userInterests", username.getInterests());
                Profilepage.this.startActivity(i);
            }
        });

        //directs user to trending page
        ImageView trendingBtn = findViewById(R.id.trendingBtn);

        trendingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTrending = new Intent(Profilepage.this, TrendingInterests.class);
                Profilepage.this.startActivity(toTrending);
            }
        });

       //directs user to settings
        ImageView settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profilepage.this, Settings.class);
                Profilepage.this.startActivity(i);
            }
        });




    }

}