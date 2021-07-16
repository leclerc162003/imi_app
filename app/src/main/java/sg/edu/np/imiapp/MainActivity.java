package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Button signUp = findViewById(R.id.signUp);

        Button signIn = findViewById(R.id.signIn);

        SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String email = loginInfo.getString("email", "default value");
        String password = loginInfo.getString("password", "default value");
        if (email != null || password != null){
            Intent i = new Intent(MainActivity.this, Signin.class);
            MainActivity.this.startActivity(i);
            signIn.setVisibility(View.GONE);
            signUp.setVisibility(View.GONE);

        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Signin.class);
                MainActivity.this.startActivity(i);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Signup.class);
                MainActivity.this.startActivity(i);
            }
        });
    }
}