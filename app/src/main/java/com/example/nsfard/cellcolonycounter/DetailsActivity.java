package com.example.nsfard.cellcolonycounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nsfard on 10/27/16.
 */
public class DetailsActivity extends AppCompatActivity implements View.OnLongClickListener {
    protected static final String DETAIL_NAME_KEY = "detail_name_key";
    protected static final String DETAIL_DATE_KEY = "detail_date_key";
    protected static final String DETAIL_COUNT_KEY = "detail_count_key";
    protected static final String DETAIL_INDEX_KEY = "detail_index_key";
    private TextView nameTV;
    private TextView countTV;
    private TextView dateTV;
    private ZoomImageView detailImage;
    private SharedPreferences.Editor prefsEditor;
    private String count = "-1";
    private int index = -1;

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

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString(MainActivity.IMAGE_PATH_KEY) != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(bundle.getString(MainActivity.IMAGE_PATH_KEY));
            detailImage.setImageBitmap(bitmap);
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

        index = bundle.getInt(DETAIL_INDEX_KEY);

        detailImage.setInitialScaleFactor(.9f);
        detailImage.setOnLongClickListener(this);
        detailImage.initImage();
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
            case R.id.editOption:
                showEditDialog();
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
        intent.putExtra(MainActivity.NEW_COUNT_KEY, count);
        intent.putExtra(MainActivity.NEW_COUNT_INDEX_KEY, index);
        startActivity(intent);
    }

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

    private void showEditDialog() {
        View customView = getLayoutInflater().inflate(R.layout.enter_name_dialog, null);
        final EditText input = (EditText) customView.findViewById(R.id.enterNameInput);

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
                try {
                    int newCount = Integer.parseInt(input.getText().toString());
                    if (index == -1) {
                        throw new Exception();
                    }

                    count = String.valueOf(newCount);
                    countTV.setText(String.valueOf(newCount));
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid integer", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setTitle("Enter New Count");

        final AlertDialog d = builder.create();
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        d.show();
    }

    @Override
    public boolean onLongClick(View view) {
        detailImage.initImage();
        return true;
    }
}
