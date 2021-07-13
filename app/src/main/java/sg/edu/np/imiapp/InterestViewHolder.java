package sg.edu.np.imiapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class InterestViewHolder extends RecyclerView.ViewHolder {
    public TextView interest;


    public InterestViewHolder(@NonNull View itemView) {
        super(itemView);
        interest = itemView.findViewById(R.id.interestText);

    }
}
