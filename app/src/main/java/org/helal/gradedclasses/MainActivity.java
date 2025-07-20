package org.helal.gradedclasses;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {
    enum NetworkState {
        NOT_PRESENT, PRESENT, LOST;
    }

    public static StudentDataLoader studentDataLoader;
    public static TimeTableLoader seating;
    private NetworkState NETWORK_STATE = NetworkState.NOT_PRESENT;
    DownloadData downloadData = new DownloadData(this, "/.app/LeaderBoard.db");
    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "/.app/LeaderBoard.db");
    File key = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "/.app/id.txt");
    public static boolean isDownloadDone = false;
    private AppBarConfiguration mAppBarConfiguration;
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), MainActivity::onActivityResult);

    private static void onActivityResult(Boolean isGranted) {
    }

    NetworkRequest networkRequest = new NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build();
    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            NETWORK_STATE = NetworkState.PRESENT;
            super.onAvailable(network);
        }

        @Override
        public void onLost(@NonNull Network network) {
            NETWORK_STATE = NetworkState.LOST;
            super.onLost(network);
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            final boolean unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ConnectivityManager connectivityManager =
                getSystemService(ConnectivityManager.class);
        connectivityManager.requestNetwork(networkRequest, networkCallback);

        storage_permission();
        if (file.exists()) {
            try {
                //seating = new TimeTableLoader(file);
                studentDataLoader = new StudentDataLoader();
                new ScheduledThreadPoolExecutor(1).execute(() -> {
                    if (NETWORK_STATE.equals(NetworkState.PRESENT)) {
                        System.out.println(file.delete());
                        if (!key.exists())
                            extracted();
                        downloadData.downloadFile();
                    }
                });
            } catch (RuntimeException runtimeException) {
                if (NETWORK_STATE.equals(NetworkState.PRESENT)) {
                    System.out.println(key.delete());
                    System.out.println(file.delete());
                    new ScheduledThreadPoolExecutor(1).execute(() -> {
                        extracted();
                        downloadData.downloadFile();
                    });
                }
            }

        } else {
            new ScheduledThreadPoolExecutor(1).execute(() -> {
                if (NETWORK_STATE.equals(NetworkState.PRESENT)) {
                    extracted();
                    downloadData.downloadFile();
                }
            });
        }

        setContentView(R.layout.activity_main);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        MenuItem item = Objects.requireNonNull(navigationView.getMenu().getItem(0).
                getSubMenu()).getItem(0);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        BottomNavigationView bottom_nav = findViewById(R.id.bottom_navigation);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.leaders_item, R.id.seating_item)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottom_nav, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            System.out.println(item.getTitle());
            if (navDestination.getId() == R.id.seating_item && item.isChecked()) {
                item.setChecked(false);
            }
            TextView textView = findViewById(R.id.sub_heading);
            ImageView sub_icon = findViewById(R.id.sub_icon);
            if (navDestination.getId() == R.id.leaders_item)
                sub_icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.leaders));
            if (navDestination.getId() == R.id.seating_item)
                sub_icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.seat));
            textView.setText(navDestination.getLabel());
        });
    }

    private void extracted() {
        downloadKey();
        while (!key.exists()) {
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void storage_permission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


        } else {

            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void downloadKey() {
        DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse("https://drive.google.com/uc?export=download&id=1-FEGMl84MVW3A4cYUvvsvIAPW2wIKqVO");
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/.app/id.txt");
        manager.enqueue(request);
    }

}