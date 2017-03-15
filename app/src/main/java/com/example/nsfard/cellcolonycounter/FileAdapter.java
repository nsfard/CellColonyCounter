package com.example.nsfard.cellcolonycounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nsfard on 12/6/16.
 */
public class FileAdapter extends ArrayAdapter<String> {
    private ArrayList<String> fileList;

    public FileAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        this.fileList = objects;
    }

    static class FileHolder {
        ImageView image;
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FileHolder holder = new FileHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_file, null);
            holder.image = (ImageView) row.findViewById(R.id.fileThumb);
            holder.name = (TextView) row.findViewById(R.id.fileName);
            row.setTag(holder);
        } else {
            holder = (FileHolder) row.getTag();
        }

        File file = new File(fileList.get(position));
        if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG") || file.getName().endsWith(".PNG")) {
            holder.image.setImageResource(R.drawable.ic_photo_black_24dp);
        }
        else if (file.isDirectory()) {
            holder.image.setImageResource(R.drawable.icon_dir_bigger);
        }

        holder.image.requestLayout();
        holder.image.getLayoutParams().height = 100;
        holder.image.getLayoutParams().width = 100;
        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);

        holder.name.setText(file.getName());

        return row;
    }

    @Override
    public int getCount() {
        return fileList.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void updateList(ArrayList<String> newList) {
        fileList = newList;
        notifyDataSetChanged();
    }
}
