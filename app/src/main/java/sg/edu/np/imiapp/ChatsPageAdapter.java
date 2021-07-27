package sg.edu.np.imiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChatsPageAdapter extends RecyclerView.Adapter<ChatsPageViewHolder> {
    Context context;
    ArrayList<User> userList;
    //FirebaseAuth auth;
    ArrayList<String> user;
    public StorageReference storageReference;
    public StorageReference pathReference;
    public ChatsPageAdapter(Context c, ArrayList<User> d, ArrayList<String> u) {
        context = c;
        userList = d;
        user = u;
    }

    @NonNull
    @Override
    public ChatsPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.chats_page_viewholder, parent, false);
        return new ChatsPageViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsPageViewHolder holder, int position) {
        //get interests list
        ArrayList<String> interestsUser = new ArrayList<>(user);
        //get user object
        User chatUser = userList.get(position);
        //get matched interests and display with profile pic using glide
        chatUser.getInterests().retainAll(interestsUser);
        storageReference = FirebaseStorage.getInstance().getReference();
        pathReference = storageReference.child("Default Images/" + chatUser.getProfilePic());
        GlideApp.with(this.context)
                .load(pathReference)
                .into(holder.usersPFP);
        holder.chatUsername.setText(chatUser.getUsername());
        holder.chatLM.setText("Interests: " + chatUser.getInterests().get(0));

        //click on this and redirects to chat user page
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("Username", chatUser.getUsername());
                extras.putString("UID", chatUser.getUID());
                extras.putString("imagePFP", chatUser.getProfilePic());
                Intent i = new Intent(context, MessagePart.class);
                i.putExtras(extras);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
