package com.example.duy.calculator.hand_write;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

class PictUtil {
    public static File getSavePath() {
        File path;
        if (hasSDCard()) { // SD card
            path = new File(getSDCardPath() + "/Ncalc");
            path.mkdir();
        } else {
            path = Environment.getDataDirectory();
        }
        return path;
    }

    public static String getCacheFilename() {
        File f = getSavePath();
        return f.getAbsolutePath() + "/cache.png";
    }

    public static Bitmap loadFromFile(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) {
                return null;
            }
            Bitmap tmp = BitmapFactory.decodeFile(filename);
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap loadFromCacheFile() {
        return loadFromFile(getCacheFilename());
    }

    public static void saveToCacheFile(Bitmap bmp) {
        saveToFile(getCacheFilename(), bmp);
    }

    public static boolean hasSDCard() { // SD????????
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    public static String getSDCardPath() {
        File path = Environment.getExternalStorageDirectory();
        return path.getAbsolutePath();
    }

    public static boolean saveToFile(String filename, Bitmap bmp) {
        FileOutputStream out = null;
        try {
            filename = getSavePath() + "/" + filename;
            Log.d(TAG, "saveToFile: " + filename);
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}