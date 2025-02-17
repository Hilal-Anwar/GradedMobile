package org.helal.gradedclasses.leaderboard;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.helal.gradedclasses.R;

public class LeaderboardView extends RecyclerView.ViewHolder{
    TextView rank, _class, name, marks;
    LinearLayout l;
    View view;
    int color;
    public LeaderboardView(View itemView) {
        super(itemView);
        this.view=itemView;
        l=itemView.findViewById(R.id.num_back);
        rank = itemView.findViewById(R.id.rank);
        _class = itemView.findViewById(R.id.class_name);
        name = itemView.findViewById(R.id.name);
        marks = itemView.findViewById(R.id.score);
    }

    public View getView() {
        return view;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public TextView getRank() {
        return rank;
    }

    public TextView get_class() {
        return _class;
    }

    public TextView getName() {
        return name;
    }

    public TextView getMarks() {
        return marks;
    }

    public LinearLayout getL() {
        return l;
    }
}
