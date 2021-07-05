package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity {
    public EditText UserEmail;
    public EditText UserPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //find email and password editText in activity_signin.xml
        this.UserEmail = findViewById(R.id.userEmailSignIn);
        this.UserPassword = findViewById(R.id.userPasswordSignIn);

        //find createAccount button in activity_signin.xml
        Button signIn =(Button) findViewById(R.id.userLogin);

        //initialise Firebase auth
        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(String.valueOf(UserEmail.getText()), String.valueOf(UserPassword.getText()));
            }
        });
    }

//    private void signIn(String email, String password){
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("create", "createUserWithEmail:success");
//                            Toast.makeText(Signin.this, "Log in success.",
//                                    Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            //updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//
//                            Toast.makeText(Signin.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            //updateUI(null);
//                        }
//                    }
//                });
//    }
}