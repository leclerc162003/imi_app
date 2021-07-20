package sg.edu.np.imiapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseProfilePicViewHolder extends RecyclerView.ViewHolder {
    public ImageView optionProfilePic;
    public RadioButton radioButton;
    public ChooseProfilePicViewHolder(@NonNull View itemView) {
        super(itemView);
        optionProfilePic = itemView.findViewById(R.id.optionPFP);
        radioButton = itemView.findViewById(R.id.radioButton);

    }
}
