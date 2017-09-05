package com.stone.dragsquare;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.swifty.dragsquareimage.ActionDialog;
import com.swifty.dragsquareimage.ActionDialogClick;

/**
 * Created by Administrator on 2016/5/27.
 */
public class MyActionDialog extends ActionDialog {

    protected MyActionDialog(Context context) {
        super(context);
        init();
    }


    public MyActionDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public MyActionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_action_dialog);

        findViewById(com.swifty.dragsquareimage.R.id.pick_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionDialogClick != null) actionDialogClick.onPickImageClick(v);
                dismiss();
            }
        });
        findViewById(com.swifty.dragsquareimage.R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionDialogClick != null) actionDialogClick.onDeleteClick(v);
                dismiss();
            }
        });
        findViewById(com.swifty.dragsquareimage.R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionDialogClick != null) actionDialogClick.onTakePhotoClick(v);
                dismiss();
            }
        });
    }

    @Override
    public View getDeleteButtonView() {
        return findViewById(R.id.delete);
    }

    public ActionDialog setActionDialogClick(ActionDialogClick actionDialogClick) {
        this.actionDialogClick = actionDialogClick;
        return this;
    }

}
