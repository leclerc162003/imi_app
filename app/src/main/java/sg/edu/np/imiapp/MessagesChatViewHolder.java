package sg.edu.np.imiapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessagesChatViewHolder extends RecyclerView.ViewHolder {
    public TextView messageTo;
    public  TextView time;
    public MessagesChatViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTo = itemView.findViewById(R.id.messageTo);
        time = itemView.findViewById(R.id.time);
    }
}
