package sg.edu.np.imiapp;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChooseProfilePicAdapter extends RecyclerView.Adapter<ChooseProfilePicViewHolder> {
    //initialise firebase authentication
    private FirebaseAuth mAuth;
    //initialise firebase database
    public StorageReference storageReference;
    public StorageReference pathReference;
    Context context;
    ArrayList<String> pfpOptions;
    private int mCheckedPosition;
    public ChooseProfilePicAdapter(Context c, ArrayList<String> d){
        context = c;
        pfpOptions = d;

    }
    @NonNull
    @Override
    public ChooseProfilePicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_profile_pic_viewholder, parent, false);
        return new ChooseProfilePicViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseProfilePicViewHolder holder, int position) {
        String pic = pfpOptions.get(position);
        storageReference = FirebaseStorage.getInstance().getReference();
        pathReference = storageReference.child("Default Images/" + pic);
        GlideApp.with(this.context)
                .load(pathReference)
                .into(holder.optionProfilePic);

        holder.radioButton.setOnCheckedChangeListener(null);
        holder.radioButton.setChecked(position == mCheckedPosition);
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mCheckedPosition = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pfpOptions.size();
    }

    public String getselectedChoice(){
        return  pfpOptions.get(mCheckedPosition);
    }

}
