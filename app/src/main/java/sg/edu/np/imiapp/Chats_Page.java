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
        ArrayList<User> userList = getUsers();
        ArrayList<User> matchedUsers = new ArrayList<>();
        for(int i=0; i < userList.size(); i++){
            List<String> compareList =  userList.get(i).getInterests();
            compareList.retainAll(userInterests);
            Log.d("fagaesise",String.valueOf(compareList.size()));
            if(compareList.isEmpty()){
                matchedUsers.add(userList.get(i));
            }
        }
        Log.d("LOOK AT ME",String.valueOf(matchedUsers.size()));
        ChatsPageAdapter adapter = new ChatsPageAdapter(this, userList,userInterests);
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
                    if(user.getUID().contentEquals(mAuth.getUid()) != true){
                        //userList.add(user);
                        Log.d("username from database", user.getUID());
                        Log.d("username from mAuth", mAuth.getUid());
                        userList.add(user);
                    }


                }
//                Log.d("size of list", String.valueOf(userList.size()));
//                ArrayList<User> userList = getUsers();
//                Intent receive = getIntent();
//                List<String> userInterests = receive.getStringArrayListExtra("userInterests");
//                for(int i=0; i < userList.size(); i++){
//                    List<String> compareList =  userList.get(i).getInterests();
//                    compareList.retainAll(userInterests);
//                    Log.d("fagaesise",String.valueOf(compareList.size()));
//                    if(!compareList.isEmpty()){
//                        //userList.remove(userList.get(i));
//                        matchedUsers.add(userList.get(i));
//                    }
//                }

                Log.d("Lize sise",String.valueOf(userList.size()));
//                for(int i=0; i < userList.size(); i++){
//                    Intent receive = getIntent();
//                    //ArrayList<String> userInterests = user.getInterests();
//                    List<String> userInterests = receive.getStringArrayListExtra("userInterests");
//                    List<String> compareList =  userList.get(i).getInterests();
//                    Log.d("current user list", userInterests.toString());
//                    Log.d("compared user list", userList.get(i).getInterests().toString() + userList.get(i).getUsername());
//                    List<String> similarList = new ArrayList<>(compareList);
//
//                    similarList.retainAll(userInterests);
//                    Log.d("after compared", similarList.toString());
//                    if(similarList.size() == 0){
//                        userList.remove(userList.get(i));
//                    }
//
//                }
//                for(int i=0; i < userList.size(); i++){
//                    Intent receive = getIntent();
//                    //ArrayList<String> userInterests = user.getInterests();
//                    List<String> userInterests = receive.getStringArrayListExtra("userInterests");
//                    List<String> compareList =  userList.get(i).getInterests();
//                    Log.d("current user list", userInterests.toString());
//                    Log.d("compared user list", userList.get(i).getInterests().toString() + userList.get(i).getUsername());
//                    List<String> similarList = new ArrayList<>(compareList);
//
//                    similarList.retainAll(userInterests);
//                    Log.d("after compared", similarList.toString());
//                    if(similarList.size() == 0){
//                        userList.remove(userList.get(i));
//
//                    }
//
//                }
                Log.d("size of list", String.valueOf(userList.size()));


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