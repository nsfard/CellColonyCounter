package com.example.nsfard.cellcolonycounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    protected static final String PREF_FILE = "preferences";
    protected static final String RESULT_NAMES_KEY = "result_names";
    protected static final String RESULT_DATES_KEY = "result_dates";
    protected static final String RESULT_PATHS_KEY = "result_paths";
    protected static final String RESULT_COUNTS_KEY = "result_counts";
    protected static final String IMAGE_PATH_KEY = "image_path";
    protected static final String RESULT_LIST_KEY = "result_list";
    protected static final String RESULT_ADAPTER_KEY = "result_adapter";
    protected static final String DELETE_KEY = "delete_key";
    protected static final String NAME_TO_DELETE_KEY = "name_to_delete";
    protected static final String DISPLAY_FRAG = "fragment_to_display";
    protected static final String DISPLAY_RESULTS = "display_results";
    protected static final String DISPLAY_CAM = "display_camera";
    protected static final String FROM_CAM_KEY = "img_from_camera";
    protected static final String PARENT_DIR = Environment.getExternalStorageDirectory()+"/CellColonyCounter/";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CameraTab cameraTab;
    private ResultsTab resultsTab;
    private ArrayList<String> resultNames;
    private ArrayList<String> resultDates;
    private ArrayList<String> resultPaths;
    private ArrayList<String> resultCounts;
    private ArrayList<Result> results;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefsEditor = getSharedPreferences(PREF_FILE, MODE_PRIVATE).edit();

        cameraTab = new CameraTab();
        resultsTab = new ResultsTab();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.slidingTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();

        resultNames = new ArrayList<>();
        resultDates = new ArrayList<>();
        resultCounts = new ArrayList<>();
        resultPaths = new ArrayList<>();
        results = new ArrayList<>();

        String resultNameString = getSharedPreferences(PREF_FILE, MODE_PRIVATE).getString(RESULT_NAMES_KEY, "");
        String resultDateString = getSharedPreferences(PREF_FILE, MODE_PRIVATE).getString(RESULT_DATES_KEY, "");
        String resultPathString = getSharedPreferences(PREF_FILE, MODE_PRIVATE).getString(RESULT_PATHS_KEY, "");
        String resultCountString = getSharedPreferences(PREF_FILE, MODE_PRIVATE).getString(RESULT_COUNTS_KEY, "");
        String displayFragString = getSharedPreferences(PREF_FILE, MODE_PRIVATE).getString(DISPLAY_FRAG, "");

        if (!resultNameString.equals("")) {
            resultNames.addAll(Arrays.asList(resultNameString.split(",")));
            resultDates.addAll(Arrays.asList(resultDateString.split(",")));
            resultPaths.addAll(Arrays.asList(resultPathString.split(",")));
            resultCounts.addAll(Arrays.asList(resultCountString.split(",")));
            for (int i=0; i<resultNames.size(); i++) {
                results.add(new Result(resultNames.get(i), resultDates.get(i), resultPaths.get(i), Integer.parseInt(resultCounts.get(i))));
            }
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getBoolean(DELETE_KEY)) {
            int i = resultNames.indexOf(bundle.getString(NAME_TO_DELETE_KEY));
            if (i >= 0) {
                new File(resultPaths.get(i)).delete();

                resultNames.remove(i);
                resultDates.remove(i);
                resultCounts.remove(i);
                resultPaths.remove(i);
                results.remove(i);
                prefsEditor.putString(MainActivity.RESULT_NAMES_KEY, TextUtils.join(",", resultNames));
                prefsEditor.putString(MainActivity.RESULT_DATES_KEY, TextUtils.join(",", resultDates));
                prefsEditor.putString(MainActivity.RESULT_COUNTS_KEY, TextUtils.join(",", resultCounts));
                prefsEditor.putString(MainActivity.RESULT_PATHS_KEY, TextUtils.join(",", resultPaths));
                prefsEditor.commit();
            }
        }

        if (!displayFragString.equals("")) {
            if (displayFragString.equals(DISPLAY_RESULTS)) {
                viewPager.setCurrentItem(1, false);
            }
            if (displayFragString.equals(DISPLAY_CAM)) {
                viewPager.setCurrentItem(0, false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addImage:
//                selectPicture();
                Intent intent = new Intent(this, GalleryActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    protected ArrayList<Result> getResults() {
        return results;
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Intent intent;
//        String selectedImagePath = "";
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//
//                //OI FILE Manager
//                selectedImagePath = getRealPathFromURI(selectedImageUri);
//
//                //NOW WE HAVE OUR WANTED STRING
////                if(selectedImagePath!=null) {
////                    System.out.println("selectedImagePath is the right one for you!");
////                }
////                else if (filemanagerstring!=null){
////                    System.out.println("filemanagerstring is the right one for you!");
////                    selectedImagePath = filemanagerstring;
////                }
////                else {
////                    System.out.println("Both strings are NULL you're screwed");
////                }
//
//            }
//
//            intent = new Intent(this, PreviewActivity.class);
//            intent.putExtra(IMAGE_PATH_KEY, selectedImagePath);
//            intent.putExtra(FROM_CAM_KEY, false);
//            startActivity(intent);
//        }
//    }

    private void setupViewPager(ViewPager vp) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(cameraTab, "Camera");
        adapter.addFragment(resultsTab, "Results");
        vp.setAdapter(adapter);
    }

    private void selectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}