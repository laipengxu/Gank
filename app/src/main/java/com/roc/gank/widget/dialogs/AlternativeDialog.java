package com.roc.gank.widget.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.roc.gank.R;


/**
 * 二选一 dialog
 */

public class AlternativeDialog extends ProgressDialog implements View.OnClickListener {

    private String mFirstItemContent;
    private String mSecondItemContent;

    private TextView mFirstItemTv;
    private TextView mSecondItemTv;

    public AlternativeDialog(Context context,
                             String firstItemContent, String secondItemContent,
                             boolean canceledOnTouchOutside) {
        super(context, R.style.Theme_Light_LoadingDialog);
        this.mFirstItemContent = firstItemContent;
        this.mSecondItemContent = secondItemContent;
        setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alternative);
        initViews();
        setupEvents();
    }

    private void initViews() {
        mFirstItemTv = (TextView) findViewById(R.id.btn_first);
        mSecondItemTv = (TextView) findViewById(R.id.btn_second);

        mFirstItemTv.setText(mFirstItemContent);
        mSecondItemTv.setText(mSecondItemContent);
    }

    private void setupEvents() {
        mFirstItemTv.setOnClickListener(this);
        mSecondItemTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_first:
                if (mCallback != null) {
                    mCallback.clickFirstItem();
                }
                break;
            case R.id.btn_second:
                if (mCallback != null) {
                    mCallback.clickSecondItem();
                }
                break;
        }
        dismiss();
    }

    private Callback mCallback;

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public interface Callback {

        void clickFirstItem();

        void clickSecondItem();

    }
}

