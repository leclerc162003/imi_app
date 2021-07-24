package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TrendingThreads extends AppCompatActivity {
    TextView interestThreadName;
    FloatingActionButton addThreadBtn;
    Dialog addThreadDialog;
    ArrayList<String> threads;
    //populate list from database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trending_threads);

        interestThreadName = findViewById(R.id.trendingTitle);
        addThreadBtn = findViewById(R.id.addThreadBtn);
        addThreadDialog = new Dialog(this);

        Intent receive = getIntent();
        String interestName = receive.getStringExtra("interestName");
        interestThreadName.setText(interestName);

        RecyclerView rv = findViewById(R.id.threadrv);
        TrendingThreadsAdapter adapter = new TrendingThreadsAdapter(this, threads);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        addThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddThreadDialog();
            }
        });
    }

    private void openAddThreadDialog() {
        addThreadDialog.setContentView(R.layout.addthreaddialog_layout);
        addThreadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView closeBtn = addThreadDialog.findViewById(R.id.closeBtn);
        Button confirmThreadBtn = addThreadDialog.findViewById(R.id.addThreadBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addThreadDialog.dismiss();
                Toast.makeText(TrendingThreads.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        confirmThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Complete intent to next activity
            }
        });

        addThreadDialog.show();
    }
}