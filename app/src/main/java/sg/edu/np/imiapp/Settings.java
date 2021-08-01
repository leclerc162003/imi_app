package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);
        TextView changePass = findViewById(R.id.changePass);
        TextView privacyPolicy = findViewById(R.id.PrivacyPolicy);
        TextView bugReport = findViewById(R.id.BugIssue);
        TextView signOut = findViewById(R.id.SignOut);
        mAuth = FirebaseAuth.getInstance();

        //directs user to change password activity
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, changePassword.class);
                Settings.this.startActivity(i);
            }
        });

        //directs user to browser to the privacy policy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://westwq.github.io/MADPrivacy/"));
                startActivity(browserIntent);
            }
        });

        //directs user to bug report page
        bugReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, bugIssue.class);
                Settings.this.startActivity(i);
            }
        });

        //directs user to sign out and clear shared pref
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                loginInfo.edit().clear().commit();
                Intent i = new Intent(Settings.this, Signin.class);
                Settings.this.startActivity(i);
            }
        });
    }
}