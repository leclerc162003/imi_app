package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        EditText email = findViewById(R.id.enterEmail);
        ImageView sendButton = findViewById(R.id.sendEmailButton);
        mAuth = FirebaseAuth.getInstance();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(email.getText().toString());
                Intent i = new Intent(forgetPassword.this, Signin.class);
                forgetPassword.this.startActivity(i);
                Toast.makeText(forgetPassword.this, "Password Reset Email Sent.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}