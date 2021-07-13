package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
    public EditText userEmail;
    public EditText userPassword;
    public ProgressBar loadingBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //find email and password editText in activity_signin.xml
        this.userEmail = findViewById(R.id.userEmailSignIn);
        this.userPassword = findViewById(R.id.userPasswordSignIn);
        loadingBar = findViewById(R.id.loadingBar);
        loadingBar.setVisibility(View.GONE);

        TextView signUp = findViewById((R.id.signUpNow));

        //find createAccount button in activity_signin.xml
        Button signIn =(Button) findViewById(R.id.userLogin);

        //initialise Firebase auth
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bring user to sign up page
                Intent i = new Intent(Signin.this, Signup.class);
                Signin.this.startActivity(i);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate user input for email and password
                String email = String.valueOf(userEmail.getText());
                String password = userPassword.getText().toString();
                if(email.equals("")){
                    userEmail.setError("can't be blank");
                }
                else if(password.equals("") || password.length() < 6){
                    userPassword.setError("must be more than 6 characters");
                }

                else{
                    signIn(String.valueOf(userEmail.getText()), String.valueOf(userPassword.getText()));
                }
                //bring user to sign in page
                //signIn(String.valueOf(userEmail.getText()), String.valueOf(userPassword.getText()));
                loadingBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.setVisibility(View.GONE);
                            // Sign in success, bring user to homepage with the signed-in user's information
                            Log.d("create", "createUserWithEmail:success");
                            Context context = getApplicationContext();
                            Toast.makeText(context, "Log in success.", Toast.LENGTH_SHORT).show();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(Signin.this, Profilepage.class);
                            Signin.this.startActivity(i);

                        }
                        else {
                            loadingBar.setVisibility(View.GONE);
                            Context context = getApplicationContext();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}