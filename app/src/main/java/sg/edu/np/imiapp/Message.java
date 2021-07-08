package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Message extends AppCompatActivity {

    public EditText messageText;
    public ImageView sendButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        this.sendButton = findViewById(R.id.sendButton);
        this.messageText = findViewById(R.id.messageText);

        mDatabase = FirebaseDatabase.getInstance().getReference();








    }
}