package com.example.duy.calculator.version_old.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.duy.calculator.R;
import com.example.duy.calculator.item_math_type.DerivativeItem;
import com.example.duy.calculator.item_math_type.IExprInput;
import com.example.duy.calculator.item_math_type.ItemResult;
import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.LogicEvaluator;
import com.example.duy.calculator.utils.ConfigApp;
import com.example.duy.calculator.version_old.activities.abstract_class.AbstractEvaluatorActivity;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.ArrayList;

import static com.example.duy.calculator.R.string.derivative;


/**
 * Đạo hàm cấp n
 * Created by Duy on 19/7/2016
 */
public class DerivativeActivity extends AbstractEvaluatorActivity {
    private static final String STARTED
            = DerivativeActivity.class.getName() + "started";
    private boolean isDataNull = true;

    private ArrayList<String> mLevel = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.derivative));
        mHint1.setHint(getString(R.string.enter_function));
        btnSolve.setText(derivative);

        //add data
        for (int i = 1; i < 21; i++) {
            mLevel.add(String.valueOf(i));
        }

        //set adapter data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DerivativeActivity.this,
                android.R.layout.simple_list_item_single_choice, mLevel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setVisibility(View.VISIBLE);
        mSpinner.setAdapter(adapter);

        getIntentData();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isStarted = mPreferences.getBoolean(STARTED, false);
        if ((!isStarted) || ConfigApp.DEBUG) {
            if (isDataNull) mInputDisplay.setText("sqrt(x) + ln(x)");
            showHelp();
        }
    }

    /**
     * get data from activity start it
     */
    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BasicCalculatorActivity.DATA);
        if (bundle != null) {
            String data = bundle.getString(BasicCalculatorActivity.DATA);
            if (data != null) {
                mInputDisplay.setText(data);
                isDataNull = false;
                doEval();
            }
        }
    }

    @Override
    public void showHelp() {
        Log.d(TAG, "showHelp: ");
        final SharedPreferences.Editor editor = mPreferences.edit();
        TapTarget target0 = TapTarget.forView(mInputDisplay,
                getString(R.string.enter_function),
                getString(R.string.input_der_here))
                .drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);

        TapTarget target1 = TapTarget.forView(mSpinner,
                getString(R.string.derivative_level),
                getString(R.string.der_level_desc))
                .drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);
        //if is not start
        TapTarget target = TapTarget.forView(btnSolve,
                getString(derivative),
                getString(R.string.push_der_button))
                .drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);

        TapTargetSequence sequence = new TapTargetSequence(DerivativeActivity.this);
        sequence.targets(target0, target1, target);
        sequence.listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                editor.putBoolean(STARTED, true);
                editor.apply();
                doEval();
            }


            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                editor.putBoolean(STARTED, true);
                editor.apply();
                doEval();
            }
        });
        sequence.start();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_solve:
                doEval();
                break;
            case R.id.btn_clear:
                super.onClear();
                break;
        }
    }


    @Override
    public void doEval() {
        String inp = mInputDisplay.getCleanText();

        //if input empty, do not evaluate
        if (inp.isEmpty()) {
            mInputDisplay.requestFocus();
            mInputDisplay.setError(getString(R.string.enter_expression));
            return;
        }
        DerivativeItem item = new DerivativeItem(
                mInputDisplay.getCleanText(),
                "x",
                mSpinner.getSelectedItem().toString()
        );
        new TaskDerivative().execute(item);
    }


    @Override
    public int getIdStringHelp() {
        return R.string.help_derivative;
    }

    /**
     * task for evaluate derivative
     */
    protected class TaskDerivative extends ATaskEval {

        @Override
        protected ItemResult doInBackground(IExprInput... iExprInputs) {
            DerivativeItem item = (DerivativeItem) iExprInputs[0];
            if (BigEvaluator.getInstance(getApplicationContext()).isSyntaxError(item.getInput())) {
                return new ItemResult(item.getInput(), BigEvaluator.getInstance(getApplicationContext()).getError(item.getInput()),
                        LogicEvaluator.RESULT_ERROR);
            }
            final ItemResult[] res = new ItemResult[1];
            BigEvaluator.getInstance(getApplicationContext()).derivativeFunction(item.getInput(), new LogicEvaluator.EvaluateCallback() {
                @Override
                public void onEvaluate(String expr, String result, int errorResourceId) {
                    res[0] = new ItemResult(expr, result, errorResourceId);
                }
            });
            return res[0];
        }
    }
}
