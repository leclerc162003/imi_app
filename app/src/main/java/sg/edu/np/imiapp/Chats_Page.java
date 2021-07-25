package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        LinearLayout linearLayout = new LinearLayout(this);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
        currentuserID = mAuth.getUid();
        Log.d("Added user to list", mAuth.getUid());
        Intent receive = getIntent();
        ArrayList<String> userInterests = receive.getStringArrayListExtra("userInterests");
        RecyclerView rv = findViewById(R.id.userRV);
        if (getUsers().size() == 0){

        }
        ChatsPageAdapter adapter = new ChatsPageAdapter(this, getUsers(),userInterests);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);




    }

    public ArrayList<User> getUsers(){
        ArrayList<User> userList = new ArrayList<>();
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //userList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren() ) {
                    User user = postSnapshot.getValue(User.class);
                    if (user.getUID().contentEquals(mAuth.getUid()) != true) {
                        //userList.add(user);
                        Log.d("username from database", user.getUID());
                        Log.d("username from mAuth", mAuth.getUid());
                        Intent receive = getIntent();
                        List<String> userInterests = receive.getStringArrayListExtra("userInterests");
                        List<String> compareList = user.getInterests();
                        compareList.retainAll(userInterests);
                        if (compareList.size() != 0) {
                            userList.add(user);
                        }
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed");
                // Failed to read value
            }
            });

        return userList;
    }
    public ArrayList<User> checkSimilarInterests(){
        ArrayList<User> userList = getUsers();
        ArrayList<User> matchedUsers = new ArrayList<>();
        Intent receive = getIntent();
        List<String> userInterests = receive.getStringArrayListExtra("userInterests");
        for(int i=0; i < userList.size(); i++){
            List<String> compareList =  userList.get(i).getInterests();
            compareList.retainAll(userInterests);
            if(compareList.isEmpty()){
                matchedUsers.add(userList.get(i));
            }
        }
        Log.d("Lize sise",String.valueOf(userList.size()));
        return  userList;
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