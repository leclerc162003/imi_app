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
//                SharedPreferences lastUserChatted = getSharedPreferences("lastUserChatted", MODE_PRIVATE);
//                String UID = lastUserChatted.getString("toID", "nouser");
//                String Name = lastUserChatted.getString("toNAME", "nouser");
//                if (UID != "nouser"){
//                    // Context in current activity and the class the data to be transferred to
//                    Intent i = new Intent(splashscreen.this, MessagePart.class);
//                    i.putExtra("toUID", UID);
//                    i.putExtra("toUsername", Name);
//                    splashscreen.this.startActivity(i);
//                    lastUserChatted.edit().clear().commit();
//                }

                    Intent i = new Intent(splashscreen.this, Signin.class);
                    splashscreen.this.startActivity(i);





            }

        };
        myCountDown.start();
    }

//    public boolean isOnline() {
//        ConnectivityManager cm =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }
//
//    public AlertDialog displayMobileDataSettingsDialog(final Context context){
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("No Internet");
//        builder.setMessage("Please connect to your internet");
//
//        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$DataUsageSummaryActivity"));
//                dialog.cancel();
//                startActivity(intent);
//
//            }
//        });
//        builder.show();
//
//        return builder.create();
//    }
}