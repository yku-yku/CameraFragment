package com.github.florent37.camerafragment.internal.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * Created by memfis on 7/6/16.
 */
public class ImageSaver implements Runnable {

    private final static String TAG = "ImageSaver";

    private byte[] bytes;
    private final File file;
    private ImageSaverCallback imageSaverCallback;

    public interface ImageSaverCallback {
        void onSuccessFinish(byte[] bytes);

        void onError();
    }

    public ImageSaver(byte[] bytes, File file, ImageSaverCallback imageSaverCallback) {
        this.bytes = bytes;
        this.file = file;
        this.imageSaverCallback = imageSaverCallback;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(file);
            output.write(bytes);
            imageSaverCallback.onSuccessFinish(bytes);
        } catch (Exception e) {
            Log.e(TAG, "Can't save the image file.", e);
            imageSaverCallback.onError();
        } finally {
            bytes = null;
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    Log.e(TAG, "Can't release image or close the output stream.");
                }
            }
        }
    }

}
