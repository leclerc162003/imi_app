package sg.edu.np.imiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

public class splashscreen extends AppCompatActivity {
    public CountDownTimer myCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        startTimer(3);


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
}