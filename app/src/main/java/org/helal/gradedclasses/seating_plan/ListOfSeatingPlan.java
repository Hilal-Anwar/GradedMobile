
package org.helal.gradedclasses.seating_plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.helal.gradedclasses.R;

import java.util.ArrayList;
import java.util.Map;

public class ListOfSeatingPlan extends RecyclerView.Adapter<SeatingPlanView> {
    Map<String, Integer> color_ = Map.of("03:00", R.color.three,
            "04:00", R.color.four, "05:00",
            R.color.five, "06:00", R.color.six, "07:00", R.color.seven);
    ArrayList<SeatingPlanInfo> seatingPlanInfoArrayList;


    public ListOfSeatingPlan(ArrayList<SeatingPlanInfo> seatingPlanInfoArrayList) {
        this.seatingPlanInfoArrayList = seatingPlanInfoArrayList;
    }

    @NonNull
    @Override
    public SeatingPlanView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.seating_view, parent, false);
        return new SeatingPlanView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatingPlanView holder, int position) {
        String t = seatingPlanInfoArrayList.get(position).getTime();
        String y = t.substring(0, t.indexOf(' '));
        int x = color_.get(y) == null ? R.color.seven : color_.get(y);
        holder.getTime().setText(seatingPlanInfoArrayList.get(position).getTime());
        holder.getBack().getBackground().setTint(ContextCompat.getColor(holder.getBack().getContext(), x));
        holder.get_class().setText(seatingPlanInfoArrayList.get(position).get_class());
        holder.getSubject().setText(seatingPlanInfoArrayList.get(position).getSubject());
        holder.getRoom_no().setText(seatingPlanInfoArrayList.get(position).getRoom_no());
    }

    @Override
    public int getItemCount() {
        return seatingPlanInfoArrayList.size();
    }

}

