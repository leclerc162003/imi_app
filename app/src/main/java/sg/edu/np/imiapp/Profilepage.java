package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profilepage extends AppCompatActivity {
    public TextView usernameHome;
    //initialise firebase authentication
    private FirebaseAuth mAuth;
    //initialise firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    //public String username;
    ArrayList<String> interestList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        //get username textview in activity_homepage.xml
        this.usernameHome = findViewById(R.id.usernameHome);

        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();


        // read from firebase database table "Users"
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //create user object with data obtained from database
                User username = dataSnapshot.child(mAuth.getUid()).getValue(User.class);
                //display username and interests in UI
                usernameHome.setText(username.getUsername() + "'s Profile");
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




    }
}