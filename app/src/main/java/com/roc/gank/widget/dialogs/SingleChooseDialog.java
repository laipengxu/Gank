package com.roc.gank.widget.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.roc.gank.R;


/**
 * 删除 dialog
 */

public class SingleChooseDialog<T> extends ProgressDialog {

    private OnDialogActionListener<T> mDialogActionListener;
    private String mActionStr;
    private T mExtra;

    public SingleChooseDialog(Context context, String actionStr, boolean canceledOnTouchOutside) {
        super(context, R.style.Theme_Light_LoadingDialog);
        mActionStr = actionStr;
        setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_single_choose);
        setupEvents();
    }

    private void setupEvents() {
        findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDialogActionListener != null) {
                    mDialogActionListener.onConfirmed();
                    mDialogActionListener.onConfirmed(mExtra);
                }
                dismiss();
            }
        });
        if (TextUtils.isEmpty(mActionStr)) {
            ((TextView) findViewById(R.id.btn_action)).setText("行为一");
        } else {
            ((TextView) findViewById(R.id.btn_action)).setText(mActionStr);
        }
    }

    public void setOnDialogActionListener(OnDialogActionListener<T> onDialogActionListener) {
        this.mDialogActionListener = onDialogActionListener;
    }

    public T getExtra() {
        return mExtra;
    }

    public void setExtra(T extra) {
        mExtra = extra;
    }
}

