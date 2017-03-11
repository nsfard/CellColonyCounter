package com.example.nsfard.cellcolonycounter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Algorithm;
import org.opencv.utils.*;
import org.opencv.imgcodecs.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;

/**
 * Created by nsfard on 1/31/17.
 */
public class CountingAlgorithm {
    private static CountingAlgorithm instance = null;
    private static String inputImage = null;
    private static String outputImage = null;
    private static int outputCount = 0;
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
        String path = inputImage;
        String newPath = "/storage/emulated/0/DCIM/results.png";
        // Save image as a Mat object
        Mat imgMat = Imgcodecs.imread(path);
        Mat grayMat = new Mat();
        // Grayscale
        Imgproc.cvtColor(imgMat, grayMat, Imgproc.COLOR_RGB2GRAY);

        // Apply CLAHE
        Mat claheMat = new Mat();
        CLAHE clahe = Imgproc.createCLAHE(2.0, new Size(8,8));
        clahe.apply(grayMat, claheMat);

        // Normalize
        Core.normalize(claheMat, claheMat, 0, 100, Core.NORM_MINMAX, CvType.CV_8UC1);
        // Getting the max histogram value
        Vector<Mat> matList = new Vector<Mat>();
        matList.add(claheMat);

        Mat histMat = new Mat();
        Imgproc.calcHist(matList, new MatOfInt(0), new Mat(), histMat, new MatOfInt(256), new MatOfFloat(0, 256));

        // find the max value in the histogram
        histMat.put(0, 0, 0);
        Core.MinMaxLocResult histResults = Core.minMaxLoc(histMat);
        double hist_max = histResults.maxLoc.y;
        int max = (int)hist_max;

        // Threshold based on histogram
        Mat threshMat = new Mat();
        Imgproc.threshold(claheMat, threshMat, max + 20, 100, Imgproc.THRESH_BINARY);
        Imgproc.medianBlur(threshMat, threshMat, 9);

        // Dilate
        Imgproc.dilate(threshMat, threshMat,
                Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3)), new Point(), 4);

        // Find contours
        List<MatOfPoint> contours = new Vector<MatOfPoint>();
        Imgproc.findContours(threshMat, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        // This should be temporary for testing the output image
        Mat res = new Mat();
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(imgMat, contours, i, new Scalar(0, 255, 0), 3);
        }

        // Write back to new image path
        Imgcodecs.imwrite(newPath, imgMat);
        // report results
        listener.algorithmCompleted(max, newPath);
    }
}
