package sg.edu.np.imiapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;

public class updateInterestsAdapter extends RecyclerView.Adapter<updateInterestsViewHolder> {
    Context context;
    ArrayList<Interests> interestsList;
    ArrayList<Integer> selectedList = new ArrayList<>();
    ArrayList<String> userInterests;
    public updateInterestsAdapter(Context c, ArrayList<Interests> d, ArrayList<String> e) {
        context = c;
        interestsList = d;
        userInterests = e;

    }

    @NonNull
    @Override
    public updateInterestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.updateinterestsviewholder, parent, false);
        return new updateInterestsViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull updateInterestsViewHolder holder, int position) {
        Interests interest = interestsList.get(position);
        holder.updateIntCheck.setText(interest.getText());
        for (int i = 0; i < userInterests.size();i++){
            if (userInterests.get(i).contentEquals(interest.getText())){
                selectedList.add(position);
                holder.updateIntCheck.setChecked(true);
                Log.d("size", String.valueOf(selectedList.size()));
            }
        }
        holder.updateIntCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.addAll(selectedList);
        selectedList.clear();
        selectedList.addAll(hashSet);
//        for (interests model : selectedList) {
//            if (model.isSelected()) {
//                selectedList.add(model);
//            }
//        }
        return selectedList;
    }
}
