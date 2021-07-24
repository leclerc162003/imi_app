package sg.edu.np.imiapp;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrendingChatAdapter extends RecyclerView.Adapter<TrendingChatViewHolder> {
    Context context;
    ArrayList<ThreadMessage> messagedata;

    public TrendingChatAdapter(Context c, ArrayList<ThreadMessage> d){
        context = c;
        messagedata = d;
    }

    @NonNull
    @Override
    public TrendingChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //make the code here
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingChatViewHolder holder, int position) {
        //create code here
    }

    @Override
    public int getItemCount() {
        return messagedata.size();
    }
}
