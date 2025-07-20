package org.helal.gradedclasses.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.helal.gradedclasses.R;
import org.helal.gradedclasses.Student;

import java.util.ArrayList;

public class ListOfLeaderBoardView extends RecyclerView.Adapter<LeaderboardView> {
    int[] col = {R.color.golden,R.color.silver, R.color.bronze, R.color._80_grade_blue};

    ArrayList<Student> listOfLeadersArrayList;


    public ListOfLeaderBoardView(ArrayList<Student> listOfLeadersArrayList) {
        this.listOfLeadersArrayList = listOfLeadersArrayList;
    }

    @NonNull
    @Override
    public LeaderboardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.leaders_views, parent, false);
        return new LeaderboardView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardView holder, int position) {
        holder.getRank().setText(String.valueOf(position + 1));
        holder.getL().getBackground().setTint(ContextCompat.getColor(holder.getView().getContext(), col[position < 4 ? position : 3]));
        holder.getView().getBackground().setTint(ContextCompat.getColor(holder.getView().getContext(), col[position < 4 ? position : 3]));
        holder.getName().setText(listOfLeadersArrayList.get(position).name());
        holder.get_class().setText(listOfLeadersArrayList.get(position).grade());
        holder.getMarks().setText(String.valueOf((int) listOfLeadersArrayList.get(position).points()));
    }

    @Override
    public int getItemCount() {
        return listOfLeadersArrayList.size();
    }

}
