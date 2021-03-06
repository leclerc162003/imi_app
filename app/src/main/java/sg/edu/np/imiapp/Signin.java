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
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signin extends AppCompatActivity {
    public TextInputEditText userEmail;
    public TextInputEditText userPassword;
    public CheckBox checkBox;
    public ProgressBar loadingBar;
    private FirebaseAuth mAuth;
    public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        //find email and password editText in activity_signin.xml
        this.userEmail = findViewById(R.id.newUEmail1);
        this.userPassword = findViewById(R.id.newUPass1);
        this.checkBox = findViewById(R.id.rememberMe);

        //find createAccount button in activity_signin.xml
        TextView signIn = findViewById(R.id.updateButton);
        TextView forgetPass = findViewById(R.id.forgetPass);
        TextView signUp = findViewById((R.id.SignIn));

        //initialise Firebase auth
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(this, Profilepage.class);
            this.startActivity (intent);
            this.finishActivity (0);
        }


        //directs user to sign up
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bring user to sign up page
                Intent i = new Intent(Signin.this, Signup.class);
                Signin.this.startActivity(i);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBox.setChecked(true);

            }
        });

        //signs user in when click on login
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate user input for email and password
                String email = String.valueOf(userEmail.getText());
                String password = userPassword.getText().toString();
                //check if email is empty
                if(email.equals("")){
                    userEmail.setError("can't be blank.");
                }
                //check if email is a valid email
                else if (!isEmailValid(email)){
                    userEmail.setError("invalid email.");
                }
                //check if password is empty and characters more than 6
                else if(password.equals("") || password.length() < 6){
                    userPassword.setError("must be more than 6 characters.");
                }
                //else sign in user
                else{
                    signIn(String.valueOf(userEmail.getText()), String.valueOf(userPassword.getText()));
                    //if check box is check save login information to shared preferences
                    if (checkBox.isChecked() == true){
                        SharedPreferences.Editor loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                        loginInfo.putString("email", String.valueOf(userEmail.getText()));
                        loginInfo.putString("password", String.valueOf(userPassword.getText()));
                        loginInfo.apply();
                    }
                    userEmail.setText("");
                    userPassword.setText("");
                }



            }
        });

        // redirects user to forget password activity
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signin.this, forgetPassword.class);
                Signin.this.startActivity(i);
            }
        });
    }

    private void signIn(String email, String password){
        //use firebase method to sign in user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, bring user to homepage with the signed-in user's information
                            Log.d("create", "createUserWithEmail:success");
                            Context context = getApplicationContext();
                            Toast.makeText(context, "Log in success.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Signin.this, Profilepage.class);
                            Signin.this.startActivity(i);

                        }
                        else {
                            Context context = getApplicationContext();
                            // If sign in fails, display a message to the user.
                            if(!password.contentEquals("def")){
                                Toast.makeText(context, "Email or Password is incorrect.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //on resume, auto login user with info stored in shared preferences
//        SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        String email = loginInfo.getString("email", "def");
//        String password = loginInfo.getString("password", "def");
//        signIn(email,password);

        //if mAuth does have a user, skip this activity completely
        if (mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(this, Profilepage.class);
            this.startActivity (intent);
            this.finishActivity (0);
        }


    }



}