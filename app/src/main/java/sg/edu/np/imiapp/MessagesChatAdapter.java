package sg.edu.np.imiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MessagesChatAdapter extends RecyclerView.Adapter<MessagesChatViewHolder> {
    Context context;
    ArrayList<SentMessages> messagesList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public MessagesChatAdapter(Context c, ArrayList<SentMessages> d) {
        context = c;
        messagesList = d;
    }

    @NonNull
    @Override
    public MessagesChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //if return 0 show chat on the right, else on the left
        View item = null;
        if(viewType == 0)
        {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.messageto_viewholder, parent, false);
        }
        else{
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagefrom_viewholder, parent, false);
        }
        return new MessagesChatViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesChatViewHolder holder, int position) {
        //get messages from list and set the text
        SentMessages message = messagesList.get(position);
        holder.messageTo.setText(message.Message);
        Calendar cal = Calendar.getInstance();



    }


    @Override
    public int getItemViewType(int position) {
        SentMessages message = messagesList.get(position);
        if(message.toUIDUser.contentEquals(mAuth.getUid())){
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}
