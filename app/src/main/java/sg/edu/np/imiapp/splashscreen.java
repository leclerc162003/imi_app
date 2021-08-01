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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class splashscreen extends AppCompatActivity {
    public CountDownTimer myCountDown;
    private FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile_picture_choose);
        setContentView(R.layout.activity_splashscreen);
        mAuth = FirebaseAuth.getInstance();
        startTimer(3);

    }

    public String getEmoji(int uni){
        return new String(Character.toChars(uni));
    }

    private void startTimer(int time){
        myCountDown = new CountDownTimer(time*1000, 1000 ){
            public void onTick(long millisUntilFinished){



            }

            public void onFinish(){
                myCountDown.cancel();

                SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                String email = loginInfo.getString("email", "def");
                String password = loginInfo.getString("password", "def");
                signIn(email,password);

                //SharedPreferences login = getSharedPreferences("logininfo", MODE_PRIVATE);
                //String password = login.getString("password", "nopass");
                SharedPreferences lastUserChatted = getSharedPreferences("lastUserChatted", MODE_PRIVATE);
                String UID = lastUserChatted.getString("toID", "nouser");
                String Name = lastUserChatted.getString("toNAME", "nouser");
                String pfp = lastUserChatted.getString("toPFP", "nouser");
                Log.d("ID of last user chatted", UID);

                if (isOnline() == false){
                    displayMobileDataSettingsDialog( splashscreen.this);
                }
                else if (!UID.contentEquals("nouser")){
                    Log.d("IS IT SENDING IT", "SEND ITT");
                    Bundle extras = new Bundle();
                    // Context in current activity and the class the data to be transferred to
                    Intent i = new Intent(splashscreen.this, MessagePart.class);
                    extras.putString("toUID", UID);
                    extras.putString("toUsername", Name);
                    extras.putString("toPFP", pfp);
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
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
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

    private void signIn(String email, String password){
        //use firebase method to sign in user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, bring user to homepage with the signed-in user's information
                            Log.d("create", "createUserWithEmail:success");
                            Context context = getApplicationContext();
                            Toast.makeText(context, "Log in success.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(splashscreen.this, Signin.class);
                            splashscreen.this.startActivity(i);

                        }
                        else {
                            Context context = getApplicationContext();
                            // If sign in fails, display a message to the user.
                            if(!password.contentEquals("def")){
                                Toast.makeText(context, "Email or Password is incorrect.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
}