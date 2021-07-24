package sg.edu.np.imiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class splashscreen extends AppCompatActivity {
    public CountDownTimer myCountDown;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        startTimer(3);

//        if (isOnline() == true){
//            displayMobileDataSettingsDialog( this);
//        }


        DatabaseReference interestRef = mDatabase.child("Interests");
        interestRef.child("Gaming").setValue("");
        interestRef.child("Singing").setValue("");
        interestRef.child("Dancing").setValue("");
        interestRef.child("Cooking").setValue("");
        interestRef.child("K-Pop").setValue("");
        interestRef.child("K-Drama").setValue("");
        interestRef.child("Netflix").setValue("");
        interestRef.child("Sleeping").setValue("");
        interestRef.child("Sports").setValue("");
        interestRef.child("Anime").setValue("");
        interestRef.child("Music").setValue("");
        interestRef.child("Studying").setValue("");
        interestRef.child("Movies & TV shows").setValue("");
    }

    private void startTimer(int time){
        myCountDown = new CountDownTimer(time*1000, 1000 ){
            public void onTick(long millisUntilFinished){



            }

            public void onFinish(){
                myCountDown.cancel();
                SharedPreferences login = getSharedPreferences("logininfo", MODE_PRIVATE);
                String password = login.getString("password", "nopass");

//                SharedPreferences lastUserChatted = getSharedPreferences("lastUserChatted", MODE_PRIVATE);
//                String UID = lastUserChatted.getString("toID", "nouser");
//                String Name = lastUserChatted.getString("toNAME", "nouser");
//                Log.d("ID of last user chatted", UID);
//                if (!UID.contentEquals("nouser")){
//                    Log.d("IS IT SENDING IT", "SEND ITT");
//                    Bundle extras = new Bundle();
//                    // Context in current activity and the class the data to be transferred to
//                    Intent i = new Intent(splashscreen.this, MessagePart.class);
//                    extras.putString("toUID", UID);
//                    extras.putString("toUsername", Name);
//                    i.putExtras(extras);
//                    splashscreen.this.startActivity(i);
//                    lastUserChatted.edit().clear().commit();
//                }
                SharedPreferences lastUserChatted = getSharedPreferences("lastUserChatted", MODE_PRIVATE);
                String UID = lastUserChatted.getString("toID", "nouser");
                String Name = lastUserChatted.getString("toNAME", "nouser");
                Log.d("ID of last user chatted", UID);
                if (isOnline() != true){
                    displayMobileDataSettingsDialog( splashscreen.this);
                }
                else if (!UID.contentEquals("nouser")){
                    Log.d("IS IT SENDING IT", "SEND ITT");
                    Bundle extras = new Bundle();
                    // Context in current activity and the class the data to be transferred to
                    Intent i = new Intent(splashscreen.this, MessagePart.class);
                    extras.putString("toUID", UID);
                    extras.putString("toUsername", Name);
                    i.putExtras(extras);
                    splashscreen.this.startActivity(i);
                    lastUserChatted.edit().clear().commit();
                }
                else {
                    Intent i = new Intent(splashscreen.this, Signin.class);
                    splashscreen.this.startActivity(i);
                }





            }

        };
        myCountDown.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer(3);
    }

    public boolean isOnline() {
        //check if internet is available, return true if user not connected to internet
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public AlertDialog displayMobileDataSettingsDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet");
        builder.setMessage("Please connect to your internet");

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //redirects user to WIFI in settings when click close
                Intent intent = new Intent();
                //intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$DataUsageSummaryActivity"));
                context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                dialog.cancel();
                startActivity(intent);

            }
        });
        builder.show();

        return builder.create();
    }
}