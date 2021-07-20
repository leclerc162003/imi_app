package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class chooseInterests extends AppCompatActivity {
    Context context;
    private FirebaseAuth mAuth;
    //initialise firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_interests);
        mAuth = FirebaseAuth.getInstance();

        RecyclerView rv = findViewById(R.id.chooseIntrv);
        chooseInterestsAdapter adapter = new chooseInterestsAdapter(context, getInterests());
        LinearLayoutManager lm = new LinearLayoutManager(context);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        adapter.getSelectedList();

        Button finish = findViewById(R.id.finishButton);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receive = getIntent();
                String email = receive.getStringExtra("newEmail");
                String password = receive.getStringExtra("newPassword");
                String username = receive.getStringExtra("newUsername");
                Log.d("email", email);
                Log.d("password", password);
                Log.d("username", username);
                String pfp = receive.getStringExtra("newpfp");
                createAccount(email, password, username, pfp, selectedInterests(adapter.getSelectedList()));

            }
        });


    }
    public ArrayList<interests> getInterests(){
        ArrayList<interests> interests = new ArrayList<>();
        interests.add(new interests("Gaming"));
        interests.add(new interests("Singing"));
        interests.add(new interests("Dancing"));
        interests.add(new interests("Cooking"));
        interests.add(new interests("K-Pop"));
        interests.add(new interests("K-Drama"));
        interests.add(new interests("Netflix"));
        interests.add(new interests("Sleeping"));
        interests.add(new interests("Sports"));
        interests.add(new interests("Anime"));
        interests.add(new interests("Music"));
        interests.add(new interests("Studying"));
        interests.add(new interests("Movies & TV shows"));



        return interests;
    }
    public ArrayList<interests> selectedInterests(ArrayList<interests> interests){
        for (interests model : interests) {
            if (model.isSelected()) {
                interests.add(model);
            }
        }
        return  interests;

    }


    private void createAccount(String email, String password, String username, String pfp, ArrayList<interests> interests) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, bring user to log in page
                            Log.d("create", "createUserWithEmail:success");
                            //Intent i = new Intent(Signup.this, Signin.class);
                            Intent i = new Intent(chooseInterests.this, Signin.class);
                            chooseInterests.this.startActivity(i);
                            //get current user
                            FirebaseUser user = mAuth.getCurrentUser();
                            //loadingBar.setVisibility(View.GONE);

                            //get interests
//                            ArrayList<String> interests = new ArrayList<>();
//                            interests.add("TWICE");
//                            interests.add("BTS");
//                            interests.add("Anime");
//                            interests.add("NCT");
//                            interests.add("Ed Sheeran");

                            //set username and save it to firebase
                            saveUsername(user.getUid(), username, pfp ,interests);

                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(chooseInterests.this, "Authentication failed.", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
        // [END create_user_with_email]
    }


    private void saveUsername(String uid, String username, String pfp, ArrayList<interests> interests){
        //add User object with user inputs
        User newUser = new User(uid, username, pfp ,interests);
        //add newUser object under user uid in Users table
        mDatabase.child("Users").child(uid).setValue(newUser);
        Log.d("data", "data added");
    }
}