package sg.edu.np.imiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InterestAdapter extends RecyclerView.Adapter<InterestViewHolder>{

    Context context;
    ArrayList<String> interestList;

    public InterestAdapter(Context c, ArrayList<String> d) {
        context = c;
        interestList = d;
    }

    @NonNull
    @Override
    public InterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.interests_viewholder, parent, false);
        return new InterestViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestViewHolder holder, int position) {

        String d = interestList.get(position);
        holder.interest.setText(d);

    }

    @Override
    public int getItemCount() {
        return interestList.size();
    }



}
