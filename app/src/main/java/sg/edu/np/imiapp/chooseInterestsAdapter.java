package sg.edu.np.imiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chooseInterestsAdapter extends RecyclerView.Adapter<chooseInterestsViewHolder> {
    Context context;
    ArrayList<interests> interestsList = new ArrayList<>();
    ArrayList<Integer> selectedList = new ArrayList<>();
    int mCheckedPostion;
    public chooseInterestsAdapter(Context c, ArrayList<interests> d) {
        context = c;
        interestsList = d;

    }

    @NonNull
    @Override
    public chooseInterestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.interests_choose_viewholder, parent, false);
        return new chooseInterestsViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull chooseInterestsViewHolder holder, int position) {
        interests interest = interestsList.get(position);
        holder.check.setText(interest.getText());
//        holder.check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                interest.setSelected(!interest.isSelected());
//                selectedList.add(position);
//            }
//        });
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                interest.setSelected(!interest.isSelected());

                selectedList.add(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return interestsList.size();
    }

    public ArrayList<Integer> getSelectedList(){
//        for (interests model : selectedList) {
//            if (model.isSelected()) {
//                selectedList.add(model);
//            }
//        }
        return selectedList;
    }
}
