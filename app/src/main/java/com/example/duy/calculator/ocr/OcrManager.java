package com.example.duy.calculator.ocr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.duy.calculator.R;
import com.example.duy.calculator.version_old.activities.abstract_class.AbstractCalculatorActivity;
import com.microblink.activity.SegmentScanActivity;
import com.microblink.activity.ShowOcrResultMode;
import com.microblink.hardware.camera.VideoResolutionPreset;
import com.microblink.ocr.ScanConfiguration;
import com.microblink.recognizers.blinkocr.engine.BlinkOCREngineOptions;
import com.microblink.recognizers.blinkocr.parser.generic.RawParserSettings;
import com.microblink.results.ocr.OcrFont;

/**
 * Created by DUy on 02-Jan-17.
 */

public class OcrManager {
    public static final String LICENSE_KEY =
<<<<<<< HEAD
            "RNBZFAGX-EIC7T267-7J4WY745-24TEPSGX-EZD4RVZG-I7ENOJSH-ZDLSNZ25-QSC3ZLUW";
    public static final int OCR_REQUEST_CODE = 12321;
=======
            "FKKMKKER-NKQZGP7M-VJQAJZDL-3GAC25MO-SM72YWVB-QT46XHYU-XT6EBXEE-6GWYJVZF";

    public static final int OCR_REQUEST_CODE = 2001;
>>>>>>> refs/remotes/origin/master
    private static final String TAG = OcrManager.class.getSimpleName();

    /**
     * setting for scanner, include accept char.
     *
     * @return - config
     */
    public static ScanConfiguration createScanConfigurations() {
<<<<<<< HEAD
=======

>>>>>>> refs/remotes/origin/master
        RawParserSettings parserSettings = new RawParserSettings();
        parserSettings.setUseSieve(true);

        BlinkOCREngineOptions ocrEngineOptions = new BlinkOCREngineOptions();
        ocrEngineOptions.setCutoffCharFilterEnabled(true);
        ocrEngineOptions.setImageProcessingEnabled(true);
        ocrEngineOptions
                .addAllDigitsToWhitelist(OcrFont.OCR_FONT_ANY)
<<<<<<< HEAD
                .addUppercaseCharsToWhitelist(OcrFont.OCR_FONT_ANY)
                .addLowercaseCharsToWhitelist(OcrFont.OCR_FONT_ANY)
=======
>>>>>>> refs/remotes/origin/master
                .addCharToWhitelist('0', OcrFont.OCR_FONT_ANY)
                .addCharToWhitelist('+', OcrFont.OCR_FONT_ANY)
                .addCharToWhitelist('-', OcrFont.OCR_FONT_ANY)
                .addCharToWhitelist('*', OcrFont.OCR_FONT_ANY)
                .addCharToWhitelist('/', OcrFont.OCR_FONT_ANY)
                .addCharToWhitelist('.', OcrFont.OCR_FONT_ANY)
                .addCharToWhitelist('x', OcrFont.OCR_FONT_ANY)
//                .addCharToWhitelist('^', OcrFont.OCR_FONT_ANY)
                .addCharToWhitelist('(', OcrFont.OCR_FONT_ANY)
                .addCharToWhitelist(')', OcrFont.OCR_FONT_ANY)
                //sin, cos, tan
<<<<<<< HEAD
=======
//                .addCharToWhitelist('e', OcrFont.OCR_FONT_ANY)
//                .addCharToWhitelist('c', OcrFont.OCR_FONT_ANY)
//                .addCharToWhitelist('o', OcrFont.OCR_FONT_ANY)
//                .addCharToWhitelist('s', OcrFont.OCR_FONT_ANY)
//                .addCharToWhitelist('i', OcrFont.OCR_FONT_ANY)
//                .addCharToWhitelist('n', OcrFont.OCR_FONT_ANY)
//                .addCharToWhitelist('t', OcrFont.OCR_FONT_ANY)
//                .addCharToWhitelist('a', OcrFont.OCR_FONT_ANY)
>>>>>>> refs/remotes/origin/master
                .addCharToWhitelist('%', OcrFont.OCR_FONT_ANY)
                .addCharToWhitelist('=', OcrFont.OCR_FONT_ANY);
        parserSettings.setOcrEngineOptions(ocrEngineOptions);
        return new ScanConfiguration(R.string.raw_title, R.string.raw_msg, "math", parserSettings);
    }

    public void startOcr(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), SegmentScanActivity.class);
        /** Set the license key */
        intent.putExtra(SegmentScanActivity.EXTRAS_LICENSE_KEY, LICENSE_KEY);
        ScanConfiguration[] configurations = new ScanConfiguration[]{
                createScanConfigurations()
        };
        intent.putExtra(SegmentScanActivity.EXTRAS_SCAN_CONFIGURATION, configurations);
        intent.putExtra(SegmentScanActivity.EXTRAS_CAMERA_VIDEO_PRESET, (Parcelable) VideoResolutionPreset.VIDEO_RESOLUTION_720p);
        intent.putExtra(SegmentScanActivity.EXTRAS_SHOW_OCR_RESULT_MODE, (Parcelable) ShowOcrResultMode.STATIC_CHARS);
        intent.putExtra(SegmentScanActivity.EXTRAS_USE_LEGACY_CAMERA_API, true);
        activity.startActivityForResult(intent, OCR_REQUEST_CODE);
        //  Toast.makeText(this, R.string.not_support_scan, Toast.LENGTH_SHORT).show();
    }

    public void processResult(AbstractCalculatorActivity activity, int resultCode, Intent data) {
        if (resultCode == SegmentScanActivity.RESULT_OK && data != null) {
            // perform processing of the data here

            // for example, obtain parcelable recognition result
            Bundle extras = data.getExtras();
            Bundle results = extras.getBundle(SegmentScanActivity.EXTRAS_SCAN_RESULTS);
            if (results != null) {
                final String res = results.getString("math");
                activity.setTextDisplay(res);
                Log.d(TAG, "processResult: result ok " + res);
            } else {
                Log.e(TAG, "processResult: result nullable");
            }
        }
    }

}
