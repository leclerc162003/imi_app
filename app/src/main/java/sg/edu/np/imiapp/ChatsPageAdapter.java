package sg.edu.np.imiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatsPageAdapter extends RecyclerView.Adapter<ChatsPageViewHolder> {
    Context context;
    ArrayList<User> userList;
    public ChatsPageAdapter(Context c, ArrayList<User> d) {
        context = c;
        userList = d;
    }

    @NonNull
    @Override
    public ChatsPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.chats_page_viewholder, parent, false);
        return new ChatsPageViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsPageViewHolder holder, int position) {
        User chatUser = userList.get(position);
        holder.chatUsername.setText(chatUser.getUsername());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
