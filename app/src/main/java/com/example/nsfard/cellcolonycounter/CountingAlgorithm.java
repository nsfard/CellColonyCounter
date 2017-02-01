package com.example.nsfard.cellcolonycounter;

/**
 * Created by nsfard on 1/31/17.
 */
public class CountingAlgorithm {
    private static CountingAlgorithm instance = null;
    private static String inputImage;
    private static String outputImage;
    private static int outputCount;
    private static AlgorithmCompletedListener listener = null;

    public static void setListener(AlgorithmCompletedListener listener) {
        instance = new CountingAlgorithm();
        instance.listener = listener;
    }

    public static void runAlgorithm(String inputImage) throws Exception {
        if (instance == null) {
            throw new Exception();
        }

        inputImage = inputImage;

        // do algorithm stuff here

        // report results
        listener.algorithmCompleted(outputCount, outputImage);
    }
}