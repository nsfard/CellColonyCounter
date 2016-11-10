package com.example.nsfard.cellcolonycounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nsfard on 10/27/16.
 */
public class ResultsTab extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<String> resultList;
    private ResultAdapter resultAdapter;
    private String[] results = {
            "Bacteria Test 1",
            "Bacteria Test 2",
            "Bacteria Test 3",
            "Bacteria Test 4",
            "Bacteria Test 5",
            "Bacteria Test 6",
            "Bacteria Test 7",
            "Bacteria Test 8"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_results, container, false);
        ListView resultLV = (ListView) view.findViewById(R.id.resultsListView);
        resultList = new ArrayList<>();
        resultAdapter = new ResultAdapter(getActivity(), android.R.layout.simple_list_item_1, resultList);
        resultLV.setAdapter(resultAdapter);
        resultLV.setOnItemClickListener(this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultList.addAll(Arrays.asList(results));
                resultAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        startActivity(intent);
    }
}
