package org.helal.gradedclasses;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class DownloadData {
    Activity activity;
    String path;

    public DownloadData(Activity activity, String path) {
        this.activity = activity;
        this.path = path;
    }


    public void downloadFile() {
        DownloadManager manager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse("https://drive.google.com/uc?export=download&id="+getId1());
        System.out.println(uri);
        System.out.println(getId1());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, path);
        manager.enqueue(request);
    }

    public String getId1() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "/.app/id.txt");
        System.out.println("File:" + readFile(file));
        return readFile(file);
    }

    private String readFile(File file) {
        System.out.println("Inside file reader");
        StringBuilder s = new StringBuilder();
        if (file.exists()) {
            try {
                Scanner in = new Scanner(file);
                System.out.println(in);
                while (in.hasNextLine()) {
                    s.append(in.nextLine());
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else readFile(file);
        return s.toString();
    }

}
