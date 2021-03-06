package com.example.nsfard.cellcolonycounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nsfard on 10/27/16.
 */
public class ResultAdapter extends ArrayAdapter<String> {
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private ArrayList<String> resultList;

    public ResultAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        this.resultList = objects;
    }

    static class StringHolder {
        TextView name;
        TextView date;
        TextView count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StringHolder holder = new StringHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_result, null);
            holder.name = (TextView) row.findViewById(R.id.resultName);
            holder.date = (TextView) row.findViewById(R.id.resultDate);
            holder.count = (TextView) row.findViewById(R.id.resultCount);
            row.setTag(holder);
        } else {
            holder = (StringHolder) row.getTag();
        }

        holder.name.setText(resultList.get(position));
        holder.date.setText(sdf.format(new Date(System.currentTimeMillis())));
        holder.count.setText(String.valueOf(position + 1));

        return row;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void updateList(ArrayList<String> newList) {
        resultList = newList;
        notifyDataSetChanged();
    }
}
