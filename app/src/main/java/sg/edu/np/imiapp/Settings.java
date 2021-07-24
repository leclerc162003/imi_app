package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ImageView changePass = findViewById(R.id.changePassImage);
        ImageView privacyPolicy = findViewById(R.id.privacyPolicy);
        ImageView bugReport = findViewById(R.id.bugIssue);
        ImageView signOut = findViewById(R.id.signOut);
        mAuth = FirebaseAuth.getInstance();
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, changePassword.class);
                Settings.this.startActivity(i);
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://westwq.github.io/MADPrivacy/"));
                startActivity(browserIntent);
            }
        });

//        bugReport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Settings.this, bugIssue.class);
//                Settings.this.startActivity(i);
//            }
//        });
//
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                Intent i = new Intent(Settings.this, Signin.class);
//                Settings.this.startActivity(i);
//            }
//        });
    }
}