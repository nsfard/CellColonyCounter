package com.example.nsfard.cellcolonycounter;

/**
 * Created by nsfard on 11/12/16.
 */
public class Result {
    private String name;
    private String date;
    private String filePath;
    private int count;

    public Result(String name, String date, String filePath, int count) {
        this.name = name;
        this.date = date;
        this.filePath = filePath;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getCount() {
        return count;
    }
}
