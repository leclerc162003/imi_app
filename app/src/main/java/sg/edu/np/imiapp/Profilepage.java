package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profilepage);
        //get username textview in activity_homepage.xml
        this.usernameHome = findViewById(R.id.usernameHome);
        storageReference = FirebaseStorage.getInstance().getReference();
        pathReference = storageReference.child("Default Images/dpfp3.png");
//        SharedPreferences lastUserChatted = getSharedPreferences("lastUserChatted", MODE_PRIVATE);
//        String UID = lastUserChatted.getString("toID", "nouser");
//        String Name = lastUserChatted.getString("toNAME", "nouser");
//        Log.d("ID of last user chatted", UID);
//        if (!UID.contentEquals("nouser")){
//            Log.d("IS IT SENDING IT", "SEND ITT");
//            Bundle extras = new Bundle();
//            // Context in current activity and the class the data to be transferred to
//            Intent i = new Intent(Profilepage.this, MessagePart.class);
//            extras.putString("toUID", UID);
//            extras.putString("toUsername", Name);
//            i.putExtras(extras);
//            Profilepage.this.startActivity(i);
//            lastUserChatted.edit().clear().commit();
//        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
        this.usernameHome = findViewById(R.id.usernameHome);
        this.profilePic = findViewById(R.id.profilePic);
        GlideApp.with(this)
                .load(pathReference)
                .into(profilePic);


        // read from firebase database table "Users"
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //create user object with data obtained from database
                User username = dataSnapshot.child(mAuth.getUid()).getValue(User.class);
                //display username and interests in UI
                usernameHome.setText(username.getUsername() + "'s Profile");
                //interestList = username.getInterests();
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



        ///Can remove CAN REMOVE AH JUST CODED TO TEST THE APP CAN REMOVE!! NOT RELEVANT CODE
        ImageView chatButton = findViewById(R.id.chatButton);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profilepage.this, Chats_Page.class);
                i.putExtra("userInterests", interestList);
                Profilepage.this.startActivity(i);
            }
        });




    }


    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences lastUserChatted = getSharedPreferences("lastUserChatted", MODE_PRIVATE);
//                String UID = lastUserChatted.getString("toID", "nouser");
//                String Name = lastUserChatted.getString("toNAME", "nouser");
//                if (UID != "nouser"){
//                    // Context in current activity and the class the data to be transferred to
//                    Intent i = new Intent(Profilepage.this, MessagePart.class);
//                    i.putExtra("toUID", UID);
//                    i.putExtra("toUsername", Name);
//                    Profilepage.this.startActivity(i);
//                    lastUserChatted.edit().clear().commit();
//                }
    }
}