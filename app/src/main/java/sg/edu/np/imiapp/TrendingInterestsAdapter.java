package sg.edu.np.imiapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrendingInterestsAdapter extends RecyclerView.Adapter<TrendingInterestViewHolder> {
    Context context;
    String username;
    ArrayList<String> interestdata;
    ArrayList<firebase_Threads> threaddata;
    ArrayList<firebase_Threads> relevantThreads = new ArrayList<>();
    Dialog dialog;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://imi-app-2a3ab-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    public TrendingInterestsAdapter(Context c, ArrayList<String> d, ArrayList<firebase_Threads> td, String u){
        context = c;
        interestdata = d;
        threaddata = td;
        username = u;
    }

    @NonNull
    @Override
    public TrendingInterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.trendinginterestrv_layout, parent, false);

        return new TrendingInterestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingInterestViewHolder holder, int position) {
        String interest = interestdata.get(position);
        relevantThreads = new ArrayList<>();

        holder.interestName.setText(interest);

        for(int i = 0; i < threaddata.size(); i++){
            if(threaddata.get(i).interestName.equals(interest)){
                relevantThreads.add(threaddata.get(i));
            }
        }

        TrendingThreadsAdapter adapter = new TrendingThreadsAdapter(this.context, relevantThreads, interest, username);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);
        holder.trendingThreadsrv.setLayoutManager(layoutManager);
        holder.trendingThreadsrv.setAdapter(adapter);

        dialog = new Dialog(context);

        holder.createThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddThreadDialog(interest);
            }
        });
    }


    @Override
    public int getItemCount() {
        return interestdata.size();
    }

    private void openAddThreadDialog(String interest) {
        dialog.setContentView(R.layout.addthreaddialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView closeBtn = dialog.findViewById(R.id.closeBtn);
        EditText threadName = dialog.findViewById(R.id.addThreadEditText);
        Button addThreadBtn = dialog.findViewById(R.id.confirmBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                firebase_Threads threadToAdd = new firebase_Threads();
                threadToAdd.setInterestName(interest);
                threadToAdd.setThreadName(threadName.getText().toString());
                mDatabase.child("Threads").push().setValue(threadToAdd);
            }
        });
        dialog.show();
    }
}
