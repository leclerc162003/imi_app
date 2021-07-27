package sg.edu.np.imiapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrendingInterestViewHolder extends RecyclerView.ViewHolder {
    TextView interestName;
    RecyclerView trendingThreadsrv;

    public TrendingInterestViewHolder(@NonNull View itemView) {
        super(itemView);
        interestName = itemView.findViewById(R.id.interestName);
        trendingThreadsrv = itemView.findViewById(R.id.trendingThreadsrv);
    }
}
