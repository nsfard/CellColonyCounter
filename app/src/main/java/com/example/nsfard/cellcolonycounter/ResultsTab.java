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

/**
 * Created by nsfard on 10/27/16.
 */
public class ResultsTab extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<Result> resultList;
    private ResultAdapter resultAdapter;
    private ListView resultLV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_results, container, false);
        resultLV = (ListView) view.findViewById(R.id.resultsListView);
        resultLV.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        resultList = ((MainActivity)getActivity()).getResults();
        resultAdapter = new ResultAdapter(getActivity(), android.R.layout.simple_list_item_1, resultList);
        resultLV.setAdapter(resultAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(MainActivity.IMAGE_PATH_KEY, resultList.get(i).getFilePath());
        intent.putExtra(DetailsActivity.DETAIL_NAME_KEY, resultList.get(i).getName());
        intent.putExtra(DetailsActivity.DETAIL_DATE_KEY, resultList.get(i).getDate());
        intent.putExtra(DetailsActivity.DETAIL_COUNT_KEY, String.valueOf(resultList.get(i).getCount()));
        intent.putExtra(DetailsActivity.DETAIL_INDEX_KEY, i);
        startActivity(intent);
    }
}
