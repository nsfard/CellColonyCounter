package com.example.nsfard.cellcolonycounter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nsfard on 11/12/16.
 */
public class PreviewActivity extends AppCompatActivity {
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private ZoomImageView previewImage;
    private String imagePath;
    private SharedPreferences.Editor prefsEditor;
    private boolean fromCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefsEditor = getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE).edit();
        previewImage = (ZoomImageView) findViewById(R.id.previewImage);

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString(MainActivity.IMAGE_PATH_KEY) != null) {
            imagePath = bundle.getString(MainActivity.IMAGE_PATH_KEY);
        }

        fromCam = bundle.getBoolean(MainActivity.FROM_CAM_KEY);

        if (imagePath == null || !(new File(imagePath).exists())) {
            Log.e("DEMO","imagePath is null");
        }

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        previewImage.setImageBitmap(bitmap);
        doGrayScale(previewImage);
        previewImage.setInitialScaleFactor(1f);
        previewImage.initImage();


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
    public void onBackPressed() {
        if (fromCam) {
            File file = new File(imagePath);
            file.delete();
        }
        super.onBackPressed();
    }

    public void onRetakeSelected(View v) {
        if (fromCam) {
            prefsEditor.putString(MainActivity.DISPLAY_FRAG, MainActivity.DISPLAY_CAM);
        }
        else {
            prefsEditor.putString(MainActivity.DISPLAY_FRAG, MainActivity.DISPLAY_RESULTS);
        }
        prefsEditor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onSaveSelected(View v) {

        View customView = getLayoutInflater().inflate(R.layout.enter_name_dialog, null);
        final EditText input = (EditText) customView.findViewById(R.id.enterNameInput);
//        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(customView);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String imageName = input.getText().toString();
                String imageNameString = getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE).getString(MainActivity.RESULT_NAMES_KEY, "");
                if (!imageNameString.equals("")) {
                    imageNameString += ",";
                }
                imageNameString += imageName;
                prefsEditor.putString(MainActivity.RESULT_NAMES_KEY, imageNameString);

                String imageDate = sdf.format(new Date(System.currentTimeMillis()));
                String imageDateString = getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE).getString(MainActivity.RESULT_DATES_KEY, "");
                if (!imageDateString.equals("")) {
                    imageDateString += ",";
                }
                imageDateString += imageDate;
                prefsEditor.putString(MainActivity.RESULT_DATES_KEY, imageDateString);

                String path = MainActivity.PARENT_DIR + "/" + imageName + ".png";
                String imagePathString = getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE).getString(MainActivity.RESULT_PATHS_KEY, "");
                if (!imagePathString.equals("")) {
                    imagePathString += ",";
                }
                imagePathString += path;
                prefsEditor.putString(MainActivity.RESULT_PATHS_KEY, imagePathString);

                String imageCountString = getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE).getString(MainActivity.RESULT_COUNTS_KEY, "");
                if (!imageCountString.equals("")) {
                    imageCountString += ",";
                }
                imageCountString += "0";
                prefsEditor.putString(MainActivity.RESULT_COUNTS_KEY, imageCountString);

                prefsEditor.putString(MainActivity.DISPLAY_FRAG, MainActivity.DISPLAY_RESULTS);
                prefsEditor.commit();

                //todo consider picking from gallery
                if (fromCam) {
                    new File(imagePath).renameTo(new File(path));
                }
                else {
                    try {
                        copy(new File(imagePath), new File(path));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                if (fromCam) {
                    onBackPressed();
                }
                else {
                    onRetakeSelected(null);
                }
            }
        });
        builder.setTitle("Enter Sample Name");

        final AlertDialog d = builder.create();
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        d.show();
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void doGrayScale(ImageView v) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

    private void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
