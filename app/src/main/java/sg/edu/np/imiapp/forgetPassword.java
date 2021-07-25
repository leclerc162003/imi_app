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

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(email.getText().toString());
                Intent i = new Intent(forgetPassword.this, Signin.class);
                forgetPassword.this.startActivity(i);
                Toast.makeText(forgetPassword.this, "Password Reset Email Sent. Please check your email.", Toast.LENGTH_LONG).show();
            }
        });


    }
}