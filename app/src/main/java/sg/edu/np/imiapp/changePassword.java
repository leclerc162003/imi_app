package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        setContentView(R.layout.activity_change_password2);
        EditText newPass = findViewById(R.id.updatePass);
        EditText reEnteredPass = findViewById(R.id.reUpdatePass);
        ImageView update = findViewById(R.id.updateButton);
        mAuth = FirebaseAuth.getInstance();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!reEnteredPass.getText().toString().contentEquals(newPass.getText().toString())){
                    reEnteredPass.setError("Password does not match");
                }
                else{
                    mAuth.getCurrentUser().updatePassword(reEnteredPass.getText().toString());
                    Toast.makeText(changePassword.this, "You have successfully changed password.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(changePassword.this, Signin.class);
                    changePassword.this.startActivity(i);
                    mAuth.signOut();
                }
            }
        });



    }
}