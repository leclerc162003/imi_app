package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chats_Page extends AppCompatActivity {

    //initialise firebase authentication
    private FirebaseAuth mAuth;
    //initialise firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    String currentuserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);

        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
        currentuserID = mAuth.getUid();
        Log.d("Added user to list", mAuth.getUid());

        RecyclerView rv = findViewById(R.id.userRV);
        ChatsPageAdapter adapter = new ChatsPageAdapter(this, findSimilarInterests());
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);


    }

    public ArrayList<User> findSimilarInterests(){
        ArrayList<User> userList = new ArrayList<>();
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren() ) {
                    User user = postSnapshot.getValue(User.class);
                    User currentUser = new User();
                    if(user.getUID().contentEquals(mAuth.getUid()) != true){
                        //userList.add(user);
                        Log.d("username from database", user.getUID());
                        Log.d("username from mAuth", mAuth.getUid());
                        userList.add(user);
                    }


                }
                for(int i=0; i < userList.size(); i++){
                    Intent receive = getIntent();
                    //ArrayList<String> userInterests = user.getInterests();
                    ArrayList<String> userInterests = receive.getStringArrayListExtra("userInterests");
                    userList.get(i).getInterests().retainAll(userInterests);
                    if(userList.get(i).getInterests().size() == 0){
                        userList.remove(userList.get(i));
                    }

                }

//                for(int i=0; i < userList.size(); i++){
//                    Intent receive = getIntent();
//                    ArrayList<String> userInterests = receive.getStringArrayListExtra("userInterests");
//                    Log.d("interests kl", String.valueOf(userInterests.size()));
//                    userList.get(i).getInterests().retainAll(userInterests);
//                    if(userList.get(i).getInterests().size() == 0){
//                        userList.remove(userList.get(i));
//                        Log.d("number of items", String.valueOf(userList.get(i).getInterests().size()));
//                    }
//
//                }
                //create user object with data obtained from database
                //User username = dataSnapshot.getValue(User.class);
                //display username and interests in UI

                //Log.d("Scheduled", username.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed");
                // Failed to read value
            }
            });

        return userList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences lastUserChatted = getSharedPreferences("lastUserChatted", MODE_PRIVATE);
        String UID = lastUserChatted.getString("toID", "nouser");
        String Name = lastUserChatted.getString("toNAME", "nouser");
        lastUserChatted.edit().clear().commit();
    }
}