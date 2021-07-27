package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class changePassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_password2);
        EditText newPass = findViewById(R.id.updatePass);
        EditText reEnteredPass = findViewById(R.id.reUpdatePass);
        TextView update = findViewById(R.id.updateButton);
        mAuth = FirebaseAuth.getInstance();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ask user to reenter aas password does not match
                if (!reEnteredPass.getText().toString().contentEquals(newPass.getText().toString())){
                    reEnteredPass.setError("Password does not match");
                }
                else{
                    //update password using firebase authentication
                    mAuth.getCurrentUser().updatePassword(reEnteredPass.getText().toString());
                    Toast.makeText(changePassword.this, "You have successfully changed password.", Toast.LENGTH_SHORT).show();
                    //sign out user and bring user to sign in page
                    //clear sharedPref login information
                    SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                    loginInfo.edit().clear().commit();
                    Intent i = new Intent(changePassword.this, Signin.class);
                    changePassword.this.startActivity(i);
                    mAuth.signOut();
                }
            }
        });



    }
}