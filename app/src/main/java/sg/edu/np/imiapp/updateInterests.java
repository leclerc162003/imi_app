package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class updateInterests extends AppCompatActivity {
    Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_interests);
        Intent receive = getIntent();
        String userUID = receive.getStringExtra("userUIDCurrent");
        ArrayList<String> userInt = receive.getStringArrayListExtra("userINTCurrent");

        Button updateButton = findViewById(R.id.updateIntButton);
        RecyclerView rv = findViewById(R.id.intRV);
        updateInterestsAdapter adapter = new updateInterestsAdapter(context,getInterestsNAemoji(),userInt);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if selected list is 0, ask user to pick interests
                if (adapter.getSelectedList().size() == 0){
                    updateButton.setError("please choose an interest");
                }
                else{
                    mDatabase.child("Users").child(userUID).child("interests").setValue(selectedInterests(getInterestsNAemoji(), adapter.getSelectedList()));
                    Intent i = new Intent(updateInterests.this, Profilepage.class);
                    updateInterests.this.startActivity(i);
                }

            }
        });

    }
    public ArrayList<Interests> getInterestsNAemoji(){
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
    public ArrayList<String> selectedInterests(ArrayList<Interests> interests, ArrayList<Integer> selectedList){
        ArrayList<String> selectedInterests = new ArrayList<>();
        for (int i = 0; i < selectedList.size() ; i++){
            selectedInterests.add(interests.get(selectedList.get(i)).getText());
        }
        return selectedInterests;

    }
}