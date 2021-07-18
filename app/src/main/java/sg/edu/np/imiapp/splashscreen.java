package sg.edu.np.imiapp;

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

public class splashscreen extends AppCompatActivity {
    public CountDownTimer myCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        startTimer(3);

//        if (isOnline() == true){
//            displayMobileDataSettingsDialog( this);
//        }


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
                if (!UID.contentEquals("nouser")){
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
}