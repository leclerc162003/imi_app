package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    public TextInputEditText newUserEmail;
    public TextInputEditText newUserPassword;
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
        this.newUserEmail = findViewById(R.id.newUEmail1);
        this.newUserPassword = findViewById(R.id.newUPass1);


        TextView SignIn = findViewById(R.id.SignIn);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bring user to sign up page
                Intent i = new Intent(Signup.this, Signin.class);
                Signup.this.startActivity(i);
            }
        });
        //find createAccount button in activity_signup.xml
        TextView createAccount = findViewById(R.id.updateButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //set when click on "create account" button, account is created.
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate user input for email, password and email
                String email = String.valueOf(newUserEmail.getText());
                String password = newUserPassword.getText().toString();

                //check if email is blank
                if(email.equals("")){
                    newUserEmail.setError("can't be blank");
                }
                //check if email is valid email
                else if (!isEmailValid(email)){
                    newUserEmail.setError("invalid email.");
                }
                //check if password is more than 6 characters or blank
                else if(password.equals("") || password.length() < 6){
                    newUserPassword.setError("must be more than 6 characters");
                }
                // create bundle and intent to choosePFP_Username activity
                else{
                    Bundle extras = new Bundle();
                    extras.putString("newEmail", email);
                    extras.putString("newPassword", password);
                    Log.d("email", email);

                    Intent i = new Intent(Signup.this, choosePFP_Username.class);
                    i.putExtras(extras);
                    Signup.this.startActivity(i);
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

}