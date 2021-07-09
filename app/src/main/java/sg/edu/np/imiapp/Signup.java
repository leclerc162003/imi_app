package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Signup extends AppCompatActivity {
    public EditText newUserEmail;
    public EditText newUserPassword;
    public EditText newUsername;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //rDatabaseReference addData = firebase.child("Users");


        //find email and password editText in activity_signup.xml
        this.newUserEmail = findViewById(R.id.newUEmail);
        this.newUserPassword = findViewById(R.id.newUPass);
        this.newUsername = findViewById(R.id.userName);



        //find createAccount button in activity_signup.xml
        Button createAccount =(Button) findViewById(R.id.createAccount);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //set when click on "create account" button, account is created.
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(String.valueOf(newUserEmail.getText()), String.valueOf(newUserPassword.getText()),  String.valueOf(newUsername.getText()));
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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("create", "createUserWithEmail:success");
                            Intent i = new Intent(Signup.this, Signin.class);
                            Signup.this.startActivity(i);
                            FirebaseUser user = mAuth.getCurrentUser();
                            ArrayList<String> interests = new ArrayList<>();
                            interests.add("Formula 1");
                            interests.add("Basketball");
                            interests.add("Football");
                            interests.add("Ice Hockey");
                            interests.add("Mcdonald's");

                            ////set username
                            //saveUsername(user.getUid(), username, interests);

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void updateUI(FirebaseUser User){

    }

    private void saveUsername(String uid, String username, ArrayList<String> interests){

        User newUser = new User(uid, username, interests);
        //mDatabase.child("Users").child(Id).setValue(username);
        mDatabase.child("Users").child(uid).setValue(newUser);


//        addData.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                addData.child("Users").child(Id).setValue(username);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("dataerror", "error");
//
//            }
//        });



        Log.d("data", "data added");
    }
}