package com.example.nsfard.cellcolonycounter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nsfard on 12/5/16.
 */
public class GalleryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String ROOT = Environment.getExternalStorageDirectory().toString();
    private ArrayList<String> currentFileList = new ArrayList<>();
    private ListView fileLV;
    private FileAdapter fileAdapter;
    private String currentDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fileLV = (ListView) findViewById(R.id.galleryList);
        File[] files = new File(ROOT).listFiles();
        for (File file : files) {
            currentFileList.add(file.getAbsolutePath());
        }
        currentDir = ROOT;

        fileAdapter = new FileAdapter(this, android.R.layout.simple_list_item_1, currentFileList);
        fileLV.setAdapter(fileAdapter);
        fileLV.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dirUp();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dirUp() {
        if (currentDir.equals(ROOT)) {
            onBackPressed();
        }
        else {
            currentDir = new File(currentDir).getParentFile().getAbsolutePath();
            invalidateList();
        }
    }

    private void invalidateList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentFileList = new ArrayList<>();

                File[] files = new File(currentDir).listFiles();
                for (File file : files) {
                    currentFileList.add(file.getAbsolutePath());
                }

                fileAdapter.updateList(currentFileList);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (new File(currentFileList.get(i)).isDirectory()) {
            currentDir = currentFileList.get(i);
            invalidateList();
        }
        else if (currentFileList.get(i).endsWith(".png") || currentFileList.get(i).endsWith(".jpg")) {
            Intent intent = new Intent(this, DevPreviewActivity.class);
            intent.putExtra(MainActivity.IMAGE_PATH_KEY, currentFileList.get(i));
            intent.putExtra(MainActivity.FROM_CAM_KEY, false);
            startActivity(intent);
        }
    }
}
