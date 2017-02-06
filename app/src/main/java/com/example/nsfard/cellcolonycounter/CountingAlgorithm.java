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
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.*;
import org.opencv.imgcodecs.*;

import java.io.ByteArrayOutputStream;
import java.io.File;

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
        String newPath = "/storage/emulated/0/DCIM/results.jpg";
        // Save image as a Mat object
        Mat imgMat = Imgcodecs.imread(path);
        Mat grayMat = new Mat();
        // Grayscale
        Imgproc.cvtColor(imgMat, grayMat, Imgproc.COLOR_RGB2GRAY);

        // Hough Circle Detection
        int iMinRadius = 30;
        int iMaxRadius = 70;

        Mat circles = new Mat();
        Imgproc.HoughCircles(grayMat, circles, Imgproc.CV_HOUGH_GRADIENT,
                1.2, 30, 50, 30, iMinRadius, iMaxRadius);

        if (circles.cols() > 0)
            for (int x = 0; x < circles.cols(); x++)
            {
                double vCircle[] = circles.get(0,x);

                if (vCircle == null)
                    break;

                Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
                int radius = (int)Math.round(vCircle[2]);

                // draw the found circle
                Imgproc.circle(imgMat, pt, 3, new Scalar(0, 0, 255));
                Imgproc.circle(imgMat, pt, radius, new Scalar(0, 255, 0));
            }

        // Write back to new image path
        Imgcodecs.imwrite(newPath, imgMat);
        // report results
        listener.algorithmCompleted(circles.cols() , newPath);
    }
}
