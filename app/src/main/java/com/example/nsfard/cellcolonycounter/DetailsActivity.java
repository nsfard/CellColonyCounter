package com.example.nsfard.cellcolonycounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by nsfard on 10/27/16.
 */
public class DetailsActivity extends AppCompatActivity implements View.OnLongClickListener {
    protected static final String DETAIL_NAME_KEY = "detail_name_key";
    protected static final String DETAIL_DATE_KEY = "detail_date_key";
    protected static final String DETAIL_COUNT_KEY = "detail_count_key";
    private TextView nameTV;
    private TextView countTV;
    private TextView dateTV;
    private ZoomImageView detailImage;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private Matrix matrix = new Matrix();
    private float scale = 1f;
    private ScaleGestureDetector SGD;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefsEditor = getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE).edit();

        detailImage = (ZoomImageView) findViewById(R.id.detailPic);
        nameTV = (TextView) findViewById(R.id.resultDetailName);
        dateTV = (TextView) findViewById(R.id.resultDetailDate);
        countTV = (TextView) findViewById(R.id.resultDetailColonySize);

        //SGD = new ScaleGestureDetector(this, new ScaleListener());

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString(MainActivity.IMAGE_PATH_KEY) != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(bundle.getString(MainActivity.IMAGE_PATH_KEY));
            detailImage.setImageBitmap(bitmap);
            doGrayScale(detailImage);
        }

        if (bundle.getString(DETAIL_NAME_KEY) != null) {
            nameTV.setText(bundle.getString(DETAIL_NAME_KEY));
        }

        if (bundle.getString(DETAIL_DATE_KEY) != null) {
            dateTV.setText(bundle.getString(DETAIL_DATE_KEY));
        }

        if (bundle.getString(DETAIL_COUNT_KEY) != null) {
            countTV.setText(bundle.getString(DETAIL_COUNT_KEY));
        }

        detailImage.setInitialScaleFactor(.3f);
        detailImage.setOnLongClickListener(this);
        detailImage.initImage();

//        matrix.setScale(.3f, .3f);
//        detailImage.setImageMatrix(matrix);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.shareOption:
                return true;
            case R.id.deleteOption:
                showDeleteDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        prefsEditor.putString(MainActivity.DISPLAY_FRAG, MainActivity.DISPLAY_RESULTS);
        prefsEditor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.DELETE_KEY, false);
        startActivity(intent);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        SGD.onTouchEvent(event);
//        return true;
//    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                prefsEditor.putString(MainActivity.DISPLAY_FRAG, MainActivity.DISPLAY_RESULTS);
                prefsEditor.commit();

                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.DELETE_KEY, true);
                intent.putExtra(MainActivity.NAME_TO_DELETE_KEY, nameTV.getText());
                startActivity(intent);
            }
        });

        AlertDialog d = builder.create();
        d.show();
    }

    private void doGrayScale(ImageView v) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(colorMatrix);
        v.setColorFilter(cf);
    }

    @Override
    public boolean onLongClick(View view) {
        detailImage.initImage();
        return true;
    }

//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            scale = scale * detector.getScaleFactor();
//            scale = Math.max(.3f,Math.min(scale,5f));
//            matrix.setScale(scale, scale);
//            detailImage.setImageMatrix(matrix);
//            return true;
//        }
//    }
}
