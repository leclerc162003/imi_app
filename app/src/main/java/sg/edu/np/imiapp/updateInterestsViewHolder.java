package sg.edu.np.imiapp;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class updateInterestsViewHolder extends RecyclerView.ViewHolder {
    CheckBox updateIntCheck;
    public updateInterestsViewHolder(@NonNull View itemView) {
        super(itemView);
        updateIntCheck = itemView.findViewById(R.id.updateInterestsCheck);
    }
}
