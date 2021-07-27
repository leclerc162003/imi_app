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

import java.util.ArrayList;

public class TrendingInterestsAdapter extends RecyclerView.Adapter<TrendingInterestViewHolder> {
    Context context;
    ArrayList<String> interestdata;
    ArrayList<String> threaddata;
    Dialog dialog;

    public TrendingInterestsAdapter(Context c, ArrayList<String> d){
        context = c;
        interestdata = d;
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
        threaddata = getThreaddata();

        holder.interestName.setText(interest);

        TrendingThreadsAdapter adapter = new TrendingThreadsAdapter(this.context, threaddata);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);
        holder.trendingThreadsrv.setLayoutManager(layoutManager);
        holder.trendingThreadsrv.setAdapter(adapter);

        dialog = new Dialog(context);

        holder.createThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddThreadDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return interestdata.size();
    }

    public ArrayList<String> getThreaddata(){
        ArrayList<String> threads = new ArrayList<>();
        //get information from firebase
        threads.add("1");
        threads.add("2");
        threads.add("3");
        threads.add("4");
        threads.add("5");

        return threads;
    }

    private void openAddThreadDialog() {
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
            }
        });
        dialog.show();
    }
}
