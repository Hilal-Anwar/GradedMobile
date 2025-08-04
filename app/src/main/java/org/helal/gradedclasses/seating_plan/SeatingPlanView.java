
package org.helal.gradedclasses.seating_plan;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.helal.gradedclasses.R;


public class SeatingPlanView extends RecyclerView.ViewHolder {

    TextView time, _class, subject, room_no;
    ConstraintLayout back;

    public SeatingPlanView(View itemView) {
        super(itemView);
        time = itemView.findViewById(R.id.time);
        _class = itemView.findViewById(R.id.classes);
        subject = itemView.findViewById(R.id.subject);
        room_no = itemView.findViewById(R.id.room_no);
        back = itemView.findViewById(R.id.table_back);
    }

    public TextView getTime() {
        return time;
    }

    public TextView get_class() {
        return _class;
    }

    public TextView getSubject() {
        return subject;
    }

    public TextView getRoom_no() {
        return room_no;
    }

    public ConstraintLayout getBack() {
        return back;
    }
}

