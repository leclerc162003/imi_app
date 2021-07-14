package sg.edu.np.imiapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatsPageViewHolder extends RecyclerView.ViewHolder {
    public TextView chatUsername;
    public TextView chatLM;
    public ChatsPageViewHolder(@NonNull View itemView) {
        super(itemView);
        chatUsername = itemView.findViewById(R.id.chatUsername);
        chatLM = itemView.findViewById(R.id.chatLM);

    }
}