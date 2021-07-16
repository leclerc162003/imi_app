package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
    public TextInputEditText userEmail;
    public TextInputEditText userPassword;
    public CheckBox checkBox;
    public ProgressBar loadingBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        //find email and password editText in activity_signin.xml
        this.userEmail = findViewById(R.id.userEmailSignIn);
        this.userPassword = findViewById(R.id.userPasswordSignIn);
        this.checkBox = findViewById(R.id.rememberMe);
//        loadingBar = findViewById(R.id.loadingBar2);
//        loadingBar.setVisibility(View.GONE);

        TextView signUp = findViewById((R.id.signUpNow));

        //find createAccount button in activity_signin.xml
        TextView signIn = findViewById(R.id.userLogin);

        //initialise Firebase auth
        mAuth = FirebaseAuth.getInstance();

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                loginInfo.putString("email", String.valueOf(userEmail.getText()));
                loginInfo.putString("password", String.valueOf(userPassword.getText()));
                loginInfo.apply();

            }
        });


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
                //loadingBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //loadingBar.setVisibility(View.GONE);
                            // Sign in success, bring user to homepage with the signed-in user's information
                            Log.d("create", "createUserWithEmail:success");
                            Context context = getApplicationContext();
                            Toast.makeText(context, "Log in success.", Toast.LENGTH_SHORT).show();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(Signin.this, Profilepage.class);
                            Signin.this.startActivity(i);

                        }
                        else {
                            //loadingBar.setVisibility(View.GONE);
                            Context context = getApplicationContext();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String email = loginInfo.getString("email", "default value");
        String password = loginInfo.getString("password", "default value");
        signIn(email,password);
    }
}