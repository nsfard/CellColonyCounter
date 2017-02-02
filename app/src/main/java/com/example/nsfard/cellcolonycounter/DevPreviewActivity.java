package com.example.nsfard.cellcolonycounter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by nsfard on 2/1/17.
 */
public class DevPreviewActivity extends AppCompatActivity implements AlgorithmCompletedListener {
    private String inputImagePath;
    private ImageView inputImageView;
    private ImageView outputImageView;
    private TextView countTV;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_preview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefsEditor = getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE).edit();
        inputImageView = (ImageView) findViewById(R.id.inputImage);
        outputImageView = (ImageView) findViewById(R.id.outputImage);
        countTV = (TextView) findViewById(R.id.countTV);

        CountingAlgorithm.setListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString(MainActivity.IMAGE_PATH_KEY) != null) {
            inputImagePath = bundle.getString(MainActivity.IMAGE_PATH_KEY);
        }

        if (inputImagePath == null || !(new File(inputImagePath).exists())) {
            Log.e("DEMO","inputImagePath is null");
        }

        Bitmap inputBitmap = BitmapFactory.decodeFile(inputImagePath);
        inputImageView.setImageBitmap(inputBitmap);

        try {
            CountingAlgorithm.runAlgorithm(inputImagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void algorithmCompleted(int count, String image) {
        countTV.setText(String.valueOf(count));

        Bitmap outputBitmap = BitmapFactory.decodeFile(image);
        outputImageView.setImageBitmap(outputBitmap);
    }
}
