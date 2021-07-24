package sg.edu.np.imiapp;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrendingInterestViewHolder extends RecyclerView.ViewHolder {
    Button interestBtn;
    public TrendingInterestViewHolder(@NonNull View itemView) {
        super(itemView);
        interestBtn = itemView.findViewById(R.id.interestBtn);
    }
}
