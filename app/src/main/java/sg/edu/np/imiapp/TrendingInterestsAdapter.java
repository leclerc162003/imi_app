package sg.edu.np.imiapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrendingInterestsAdapter extends RecyclerView.Adapter<TrendingInterestViewHolder> {
    Context context;
    ArrayList<Interests> interestdata;

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
        holder.interestBtn.setText(interestName);

        holder.interestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTrendingThreads = new Intent(holder.interestBtn.getContext(), TrendingThreads.class);
                toTrendingThreads.putExtra("interestName", interestName);
                holder.interestBtn.getContext().startActivity(toTrendingThreads);
            }
        });
    }

    @Override
    public int getItemCount() {
        return interestdata.size();
    }
}
