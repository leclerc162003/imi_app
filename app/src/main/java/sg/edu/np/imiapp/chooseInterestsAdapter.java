package sg.edu.np.imiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;

public class chooseInterestsAdapter extends RecyclerView.Adapter<chooseInterestsViewHolder> {
    Context context;
    ArrayList<Interests> interestsList = new ArrayList<>();
    ArrayList<Integer> selectedList = new ArrayList<>();
    public chooseInterestsAdapter(Context c, ArrayList<Interests> d) {
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

        Interests interest = interestsList.get(position);
        holder.check.setText(interest.getText());
        //if checkbox is check is selected and remove from selected list if unselected
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                interest.setSelected(!interest.isSelected());
                if (isChecked){
                    selectedList.add(position);
                }
                else {
                    selectedList.remove(selectedList.size()-1);

                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return interestsList.size();
    }

    public ArrayList<Integer> getSelectedList(){
        //removes any duplicates in the list (if there is any)
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.addAll(selectedList);
        selectedList.clear();
        selectedList.addAll(hashSet);
        return selectedList;
    }
}
