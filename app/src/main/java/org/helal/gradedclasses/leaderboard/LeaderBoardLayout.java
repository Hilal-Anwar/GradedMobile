package org.helal.gradedclasses.leaderboard;

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
import org.helal.gradedclasses.StudentDataLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class LeaderBoardLayout extends Fragment {
    RecyclerView recyclerView;
    File file;
    ListOfLeaderBoardView listOfLeaderBoardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.leaderboard_layout, container, false);
        View late_download = v.findViewById(R.id.error_layer);
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "/.app/time_table_leader.xlsx");
        recyclerView = v.findViewById(R.id.recyclerView);
        if (MainActivity.studentDataLoader!=null) {
            this.requireActivity().runOnUiThread(() -> {
                late_download.setVisibility(View.INVISIBLE);
                inflateRecycleView();
            });
        }
        else {
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
                        MainActivity.studentDataLoader = new StudentDataLoader(file);
                        inflateRecycleView();
                    }
                });
            });

        }


        return v;
    }


    private void inflateRecycleView() {
        List<Student> l = MainActivity.studentDataLoader.getStudentStream();
        ArrayList<Student> linkedHashMap = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            linkedHashMap.add(l.get(i));
        }
        listOfLeaderBoardView = new ListOfLeaderBoardView(linkedHashMap);
        recyclerView.setAdapter(listOfLeaderBoardView);
    }

}
