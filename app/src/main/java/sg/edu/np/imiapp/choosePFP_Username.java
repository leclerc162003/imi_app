package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class choosePFP_Username extends AppCompatActivity {
    public ImageView imageView;
    StorageReference storageReference;
    StorageReference pathReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_picture_choose);
//        storageReference = FirebaseStorage.getInstance().getReference("gs://imi-app-2a3ab.appspot.com/");
//        pathReference = storageReference.child("Default Images/dpfp1.png");
        EditText userNameEnter = findViewById(R.id.userNameEnter);
        TextView continue1 = findViewById(R.id.finishButton);

        RecyclerView rv = findViewById(R.id.rvPfp);
        ChooseProfilePicAdapter adapter = new ChooseProfilePicAdapter(this, populateList());
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //for horizontal
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        //adapter.getselectedChoice();
        //userNameEnter.getText();

        continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameEnter.getText().toString();
                if(username.equals("")){
                    userNameEnter.setError("can't be blank");
                    //loadingBar.setVisibility(View.GONE);
                }
                else{
                    Intent receive = getIntent();
                    String email = receive.getStringExtra("newEmail");
                    String password = receive.getStringExtra("newPassword");
                    String newUsername = username;
                    String pfp = adapter.getselectedChoice();
                    Log.d("email", email);

                    Bundle extras = new Bundle();
                    extras.putString("newEmail", email);
                    extras.putString("newPassword", password);
                    extras.putString("newUsername", newUsername);
                    extras.putString("newpfp", pfp);

                    Intent i = new Intent(choosePFP_Username.this, chooseInterests.class);
                    i.putExtras(extras);
                    choosePFP_Username.this.startActivity(i);


                }
            }
        });

        // ImageView in your Activity
        //imageView = findViewById(R.id.setPic);

       // Download directly from StorageReference using Glide
       // (See MyAppGlideModule for Loader registration)
//        Glide.with(this /* context */)
//                .load(pathReference)
//                .into(imageView);
    }

    public ArrayList<String> populateList(){
        ArrayList<String> pfpOptions = new ArrayList<>();
        pfpOptions.add("dpfp1.png");
        pfpOptions.add("dpfp2.png");
        pfpOptions.add("dpfp3.png");
        pfpOptions.add("dpfp4.png");
        pfpOptions.add("dpfp5.png");
        pfpOptions.add("dpfp6.png");
        pfpOptions.add("dpfp7.png");
        pfpOptions.add("dpfp8.png");
        pfpOptions.add("dpfp9.png");
        pfpOptions.add("dpfp10.png");
        pfpOptions.add("dpfp11.png");
        return pfpOptions;
    }
}