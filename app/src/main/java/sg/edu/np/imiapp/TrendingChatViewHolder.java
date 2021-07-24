package sg.edu.np.imiapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrendingChatViewHolder extends RecyclerView.ViewHolder {
    public TextView threadMessageText;

    public TrendingChatViewHolder(@NonNull View itemView) {
        super(itemView);
        threadMessageText = itemView.findViewById(R.id.threadMessageText);
    }
}
