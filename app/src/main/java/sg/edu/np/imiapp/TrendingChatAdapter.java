package sg.edu.np.imiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class TrendingChatAdapter extends RecyclerView.Adapter<TrendingChatViewHolder> {
    Context context;
    ArrayList<ThreadMessage> messagedata;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public TrendingChatAdapter(Context c, ArrayList<ThreadMessage> d){
        context = c;
        messagedata = d;
    }

    @NonNull
    @Override
    public TrendingChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item;

        if(viewType == 0){
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.threadmessageto_layout, parent, false);
        }
        else{
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.threadmessagefrom_layout, parent, false);
        }

        return new TrendingChatViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingChatViewHolder holder, int position) {
        ThreadMessage message = messagedata.get(position);
        holder.threadMessageText.setText(message.messageContent);
        //change the name of sender
        holder.messagerName.setText(message.sentUserName);
    }

    @Override
    public int getItemCount() {
        return messagedata.size();
    }

    @Override
    public int getItemViewType(int position){
        ThreadMessage message = messagedata.get(position);
        if(message.sentUserID.contentEquals(mAuth.getUid())){
            return 0;
        }
        return 1;
    }
}
