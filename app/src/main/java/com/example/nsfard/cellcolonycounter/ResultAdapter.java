package com.example.nsfard.cellcolonycounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by nsfard on 10/27/16.
 */
public class ResultAdapter extends ArrayAdapter<Result> {
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private ArrayList<Result> resultList;

    public ResultAdapter(Context context, int textViewResourceId, ArrayList<Result> objects) {
        super(context, textViewResourceId, objects);
        this.resultList = objects;
    }

    static class ResultHolder {
        TextView name;
        TextView date;
        ImageView image;
        TextView count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultHolder holder = new ResultHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_result, null);
            holder.name = (TextView) row.findViewById(R.id.resultName);
            holder.date = (TextView) row.findViewById(R.id.resultDate);
            //holder.image = (ImageView) row.findViewById(R.id.resultImage);
            holder.count = (TextView) row.findViewById(R.id.resultCount);
            row.setTag(holder);
        } else {
            holder = (ResultHolder) row.getTag();
        }

        holder.name.setText(resultList.get(position).getName());
        holder.date.setText(resultList.get(position).getDate());
        //holder.image.setImageResource();
        holder.count.setText(String.valueOf(resultList.get(position).getCount()));

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
}
