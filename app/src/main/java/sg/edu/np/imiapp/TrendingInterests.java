package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TrendingInterests extends AppCompatActivity {

    ArrayList<Interests> interests;
    DisplayMetrics displayMetrics;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trending_interests);

        interests = getInterests();

        RecyclerView rv = findViewById(R.id.TrendingInterests);
        TrendingInterestsAdapter adapter = new TrendingInterestsAdapter(this, interests);
        displayMetrics = this.getResources().getDisplayMetrics();
        int noOfColumns = displayMetrics.widthPixels / 182;
        GridLayoutManager layoutManager = new GridLayoutManager(this, noOfColumns);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

    }

    public ArrayList<Interests> getInterests(){
        //Use database information to get interests
        ArrayList<Interests> interests = new ArrayList<>();
        interests.add(new Interests("Gaming"));
        interests.add(new Interests("Singing"));
        interests.add(new Interests("Dancing"));
        interests.add(new Interests("Cooking"));
        interests.add(new Interests("K-Pop"));
        interests.add(new Interests("K-Drama"));
        interests.add(new Interests("Netflix"));
        interests.add(new Interests("Sleeping"));
        interests.add(new Interests("Sports"));
        interests.add(new Interests("Anime"));
        interests.add(new Interests("Music"));
        interests.add(new Interests("Studying"));
        interests.add(new Interests("Movies & TV shows"));

        return interests;
    }
}