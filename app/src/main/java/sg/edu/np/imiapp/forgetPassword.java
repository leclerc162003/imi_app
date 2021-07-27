package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forgetPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_picture_choose);
        setContentView(R.layout.activity_forget_password);
        EditText email = findViewById(R.id.enterEmail);
        TextView sendButton = findViewById(R.id.sendEmailButton);
        mAuth = FirebaseAuth.getInstance();

        //sends verification email to user's email through firebase
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmailValid(email.getText().toString())){
                    email.setError("invalid email.");
                }
                mAuth.sendPasswordResetEmail(email.getText().toString());
                Intent i = new Intent(forgetPassword.this, Signin.class);
                forgetPassword.this.startActivity(i);
                Toast.makeText(forgetPassword.this, "Password Reset Email Sent. Please check your email.", Toast.LENGTH_LONG).show();
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