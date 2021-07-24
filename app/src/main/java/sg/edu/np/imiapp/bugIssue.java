package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class bugIssue extends AppCompatActivity {
    //initialise firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_issue);
        EditText reportText = findViewById(R.id.reportText);
        Button reportButton = findViewById(R.id.reportButton);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Report of Bugs and Issues").push().setValue(reportText.getText().toString());
                Toast.makeText(bugIssue.this, "Thank you for reporting the bug", Toast.LENGTH_SHORT).show();
                reportText.setText("");
            }
        });


    }
}