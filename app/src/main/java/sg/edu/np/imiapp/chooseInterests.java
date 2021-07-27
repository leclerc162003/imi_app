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
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
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
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_interests);
        mAuth = FirebaseAuth.getInstance();
        //display recycler view of interests
        RecyclerView rv = findViewById(R.id.chooseIntrv);
        chooseInterestsAdapter adapter = new chooseInterestsAdapter(context, getInterests());
        LinearLayoutManager lm = new LinearLayoutManager(context);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        Log.d("error", "here");

        TextView finish = findViewById(R.id.sendEmailButton);

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
                //create account and post data to database
                //if selected list is 0, ask user to pick interests
                if (adapter.getSelectedList().size() == 0){
                    finish.setError("please choose an interest");
                }
                else{
                    createAccount(email, password, username, pfp,  adapter.getSelectedList());
                    Log.d("pfp", pfp);
                    Log.d("interests", String.valueOf(selectedInterests(getInterestsNAemoji(), adapter.getSelectedList())));
                }
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
        return interests;
    }
    public ArrayList<Interests> getInterestsNAemoji(){
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

    public String getEmoji(int uni){
        return new String(Character.toChars(uni));
    }
    //get selected interests
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
                            saveUsername(user.getUid(), username, pfp ,selectedInterests(getInterestsNAemoji(), selected));
                            //directs user to sign in page
                            Intent i = new Intent(chooseInterests.this, Signin.class);
                            chooseInterests.this.startActivity(i);

                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(chooseInterests.this, "Create Account Failed. Please Try Again.", Toast.LENGTH_SHORT).show();
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