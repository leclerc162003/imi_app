package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Signin extends AppCompatActivity {
    public EditText UserEmail;
    public EditText UserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //find email and password editText in activity_signin.xml
        this.UserEmail = findViewById(R.id.userEmailSignIn);
        this.UserPassword = findViewById(R.id.userPasswordSignIn);

        //find createAccount button in activity_signin.xml
        Button signIn =(Button) findViewById(R.id.userLogin);
    }
}