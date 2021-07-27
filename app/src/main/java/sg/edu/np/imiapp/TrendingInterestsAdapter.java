package sg.edu.np.imiapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrendingInterestsAdapter extends RecyclerView.Adapter<TrendingInterestViewHolder> {
    Context context;
    ArrayList<Interests> interestdata;
    ArrayList<String> threaddata;

    public TrendingInterestsAdapter(Context c, ArrayList<Interests> d){
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
        Interests interest = interestdata.get(position);
        String interestName = interest.getText();
        threaddata = getThreaddata();

        holder.interestName.setText(interestName);

        TrendingThreadsAdapter adapter = new TrendingThreadsAdapter(this.context, threaddata);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);
        holder.trendingThreadsrv.setLayoutManager(layoutManager);
        holder.trendingThreadsrv.setAdapter(adapter);
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
}
