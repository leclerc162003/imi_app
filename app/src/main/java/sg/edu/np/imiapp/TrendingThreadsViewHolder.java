package sg.edu.np.imiapp;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrendingThreadsViewHolder extends RecyclerView.ViewHolder {
    Button toThreadBtn;

    public TrendingThreadsViewHolder(@NonNull View itemView) {
        super(itemView);
        toThreadBtn = itemView.findViewById(R.id.toThreadBtn);
    }
}
