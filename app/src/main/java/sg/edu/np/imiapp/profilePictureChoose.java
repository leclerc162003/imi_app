package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class profilePictureChoose extends AppCompatActivity {
    public ImageView imageView;
    StorageReference storageReference;
    StorageReference pathReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture_choose);
        storageReference = FirebaseStorage.getInstance().getReference("gs://imi-app-2a3ab.appspot.com/");
        pathReference = storageReference.child("Default Images/dpfp1.png");

        // ImageView in your Activity
        imageView = findViewById(R.id.setPic);

       // Download directly from StorageReference using Glide
       // (See MyAppGlideModule for Loader registration)
//        Glide.with(this /* context */)
//                .load(pathReference)
//                .into(imageView);
    }
}