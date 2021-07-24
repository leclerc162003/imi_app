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
    FirebaseUser user;

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
        Log.d("error", "here");

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
                //createAccount(email, password, username, pfp, selectedInterests(getInterests(), adapter.getSelectedList()));
                createAccount(email, password, username, pfp,  adapter.getSelectedList());
                Log.d("pfp", pfp);
                Log.d("interests", String.valueOf(selectedInterests(getInterests(), adapter.getSelectedList())));
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign up success, bring user to log in page
//                                    Log.d("create", "createUserWithEmail:success");
//                                    //Intent i = new Intent(Signup.this, Signin.class);
//                                    //get current user
//                                    user = mAuth.getCurrentUser();
//                                    //loadingBar.setVisibility(View.GONE);
//                                    //set username and save it to firebase
//                                    ArrayList<interests> iint = getInterests();
//                                    ArrayList<Integer> inttt= adapter.getSelectedList();
//                                    ArrayList<String> ag = selectedInterests(iint, inttt);
//                                    saveUsername(user.getUid(), username, pfp ,ag );
//
//                                    Intent i = new Intent(chooseInterests.this, Signin.class);
//                                    chooseInterests.this.startActivity(i);
//
//                                } else {
//                                    // If sign up fails, display a message to the user.
//                                    Toast.makeText(chooseInterests.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                    Log.d("createAccount", "failed");
//
//
//                                }
//                            }
//                        });


//                Intent i = new Intent(chooseInterests.this, Signin.class);
//                chooseInterests.this.startActivity(i);

            }
        });


    }
    public ArrayList<Interests> getInterests(){
        ArrayList<Interests> interests = new ArrayList<>();
        interests.add(new Interests("Gaming"));
        interests.add(new Interests("Singing"));
        interests.add(new Interests("Dancing"));
        interests.add(new Interests("Cooking"));
        interests.add(new Interests("K-Pop"));
        interests.add(new Interests("K-Drama"));
        interests.add(new Interests("Netflix"));
        interests.add(new Interests("Sleeping"));
        interests.add(new Interests("Sports"));
        interests.add(new Interests("Anime"));
        interests.add(new Interests("Music"));
        interests.add(new Interests("Studying"));
        interests.add(new Interests("Movies & TV shows"));



        return interests;
    }
    public ArrayList<String> selectedInterests(ArrayList<Interests> interests, ArrayList<Integer> selectedList){
        ArrayList<String> selectedInterests = new ArrayList<>();
        for (int i = 0; i < selectedList.size() ; i++){
            selectedInterests.add(interests.get(selectedList.get(i)).getText());
        }
        return selectedInterests;

    }


    private void createAccount(String email, String password, String username, String pfp, ArrayList<Integer> selected) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(chooseInterests.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, bring user to log in page
                            Log.d("create", "createUserWithEmail:success");
                            //Intent i = new Intent(Signup.this, Signin.class);
                            //get current user
                            user = mAuth.getCurrentUser();
                            //loadingBar.setVisibility(View.GONE);
                            //set username and save it to firebase
                            saveUsername(user.getUid(), username, pfp ,selectedInterests(getInterests(), selected));

                            Intent i = new Intent(chooseInterests.this, Signin.class);
                            chooseInterests.this.startActivity(i);

                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(chooseInterests.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            Log.d("createAccount", "failed");


                        }
                    }
                });
        // [END create_user_with_email]
    }


    public void saveUsername(String uid, String username, String pfp, ArrayList<String> interests){
        //add User object with user inputs
        User newUser = new User(uid, username, pfp ,interests);
        //add newUser object under user uid in Users table
        mDatabase.child("Users").child(uid).setValue(newUser);
        Log.d("data", "data added");
    }
}