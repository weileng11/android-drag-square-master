package com.swifty.dragsquareimage;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by swifty on 9/12/2016.
 */

public abstract class ActionDialog extends AlertDialog implements DialogInterface.OnShowListener {
    protected boolean showDeleteButton;
    protected ActionDialogClick actionDialogClick;

    protected ActionDialog(@NonNull Context context) {
        super(context);
    }

    protected ActionDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ActionDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnShowListener(this);
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (getDeleteButtonView() == null) return;
        if (showDeleteButton()) {
            getDeleteButtonView().setVisibility(View.VISIBLE);
        } else {
            getDeleteButtonView().setVisibility(View.GONE);
        }
    }

    public abstract View getDeleteButtonView();

    public abstract ActionDialog setActionDialogClick(ActionDialogClick actionDialogClick);

    public boolean showDeleteButton() {
        return showDeleteButton;
    }

    public ActionDialog setShowDeleteButton(boolean showDeleteButton) {
        this.showDeleteButton = showDeleteButton;
        return this;
    }
}
