package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Signup extends AppCompatActivity {
    public EditText newUserEmail;
    public EditText newUserPassword;
    public EditText newUsername;
    //initialise firebase authentication
    public ProgressBar loadingBar;
    private FirebaseAuth mAuth;
    //initialise firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        //find email and password editText in activity_signup.xml
        this.newUserEmail = findViewById(R.id.newUEmail);
        this.newUserPassword = findViewById(R.id.newUPass);
        //this.newUsername = findViewById(R.id.userName);
        loadingBar = findViewById(R.id.progressBar);
        loadingBar.setVisibility(View.GONE);

        //find createAccount button in activity_signup.xml
        Button createAccount = findViewById(R.id.continueCreate);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //set when click on "create account" button, account is created.
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate user input for email, password and email
                String email = String.valueOf(newUserEmail.getText());
                String password = newUserPassword.getText().toString();
                //String username = newUsername.getText().toString();
                loadingBar.setVisibility(View.VISIBLE);

                if(email.equals("")){
                    newUserEmail.setError("can't be blank");
                    //loadingBar.setVisibility(View.GONE);
                }
                else if(password.equals("") || password.length() < 6){
                    newUserPassword.setError("must be more than 6 characters");
                    //loadingBar.setVisibility(View.GONE);
                }

                else{
                    //createAccount(String.valueOf(newUserEmail.getText()), String.valueOf(newUserPassword.getText()),  String.valueOf(newUsername.getText()));
                    //loadingBar.setVisibility(View.GONE);
                    Bundle extras = new Bundle();
                    extras.putString("newEmail", email);
                    extras.putString("newPassword", password);
                    Log.d("email", email);

                    Intent i = new Intent(Signup.this, choosePFP_Username.class);
                    i.putExtras(extras);
                    Signup.this.startActivity(i);
                }
                //create account method with the values obtained from the editText inputted by the user
                //createAccount(String.valueOf(newUserEmail.getText()), String.valueOf(newUserPassword.getText()),  String.valueOf(newUsername.getText()));
            }
        });

    }



    private void createAccount(String email, String password, String username) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, bring user to log in page
                            Log.d("create", "createUserWithEmail:success");
                            //Intent i = new Intent(Signup.this, Signin.class);
                            Intent i = new Intent(Signup.this, choosePFP_Username.class);
                            Signup.this.startActivity(i);
                            //get current user
                            FirebaseUser user = mAuth.getCurrentUser();
                            loadingBar.setVisibility(View.GONE);

                            //get interests
                            ArrayList<String> interests = new ArrayList<>();
                            interests.add("TWICE");
                            interests.add("BTS");
                            interests.add("Anime");
                            interests.add("NCT");
                            interests.add("Ed Sheeran");

                            //set username and save it to firebase
                            saveUsername(user.getUid(), username, interests);

                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(Signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            loadingBar.setVisibility(View.GONE);


                        }
                    }
                });
        // [END create_user_with_email]
    }


    private void saveUsername(String uid, String username, ArrayList<String> interests){
        //add User object with user inputs
        //User newUser = new User(uid, username, interests);
        //add newUser object under user uid in Users table
        //mDatabase.child("Users").child(uid).setValue(newUser);
        Log.d("data", "data added");
    }
}