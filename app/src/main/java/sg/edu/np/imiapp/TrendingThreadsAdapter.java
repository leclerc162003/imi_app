package sg.edu.np.imiapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrendingThreadsAdapter extends RecyclerView.Adapter<TrendingThreadsViewHolder> {
    Context context;
    ArrayList<firebase_Threads> threaddata;
    String interestName;

    public TrendingThreadsAdapter(Context c, ArrayList<firebase_Threads> d, String i){
        context = c;
        threaddata = d;
        interestName = i;
    }

    @NonNull
    @Override
    public TrendingThreadsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.trendingthreadrv_layout, parent, false);

        return new TrendingThreadsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingThreadsViewHolder holder, int position) {
        String threadName = threaddata.get(position).threadName;

        holder.toThreadBtn.setText(threadName);

        holder.toThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMessage = new Intent(holder.toThreadBtn.getContext(), TrendingChat.class);
                toMessage.putExtra("interestName", interestName);
                toMessage.putExtra("threadName", threadName);
                holder.toThreadBtn.getContext().startActivity(toMessage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return threaddata.size();
    }

}
