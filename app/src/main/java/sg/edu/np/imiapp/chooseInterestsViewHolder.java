package sg.edu.np.imiapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class chooseInterestsViewHolder extends RecyclerView.ViewHolder {
    public CheckBox check;
    public chooseInterestsViewHolder(@NonNull View itemView) {
        super(itemView);
        check = itemView.findViewById(R.id.interestCheck);
    }
}
