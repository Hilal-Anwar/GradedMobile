package org.helal.gradedclasses.seating_plan;




import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.helal.gradedclasses.MainActivity;
import org.helal.gradedclasses.R;
import org.helal.gradedclasses.TimeTableLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SeatingPlanLayout extends Fragment {
    RecyclerView recyclerView;
    ListOfSeatingPlan listOfSeatingPlan;
    TimeTableLoader seating;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.seating_plan_list, container, false);
        View late_download = v.findViewById(R.id.error_layer);
        recyclerView = v.findViewById(R.id.recycleView2);
       /* File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "/.app/time_table_leader.xlsx");
        if (MainActivity.seating!=null) {
            this.requireActivity().runOnUiThread(() -> {
                late_download.setVisibility(View.INVISIBLE);
                inflateRecycleView();
            });
        } else {
            new ScheduledThreadPoolExecutor(1).execute(() -> {
                while (!file.exists()) {
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.requireActivity().runOnUiThread(() -> {
                    if (file.exists()) {
                        late_download.setVisibility(View.INVISIBLE);
                        MainActivity.seating = new TimeTableLoader(file);
                        inflateRecycleView();
                    }
                });
            });

        }
*/
        return v;
    }


    private void inflateRecycleView() {
        List<SeatingPlanInfo> l = MainActivity.seating.getTimeTableLinkedList();
        ArrayList<SeatingPlanInfo> linkedHashMap = new ArrayList<>(l);
        listOfSeatingPlan = new ListOfSeatingPlan(linkedHashMap);
        recyclerView.setAdapter(listOfSeatingPlan);
    }

    @Override
    public void onDestroyView() {
        System.out.println("App closed");
        super.onDestroyView();
    }
}
