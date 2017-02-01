package com.example.nsfard.cellcolonycounter;

/**
 * Created by nsfard on 1/31/17.
 */
public interface AlgorithmCompletedListener {
    // count is colony count, image is path to annotated image
    public void algorithmCompleted(int count, String image);
}
