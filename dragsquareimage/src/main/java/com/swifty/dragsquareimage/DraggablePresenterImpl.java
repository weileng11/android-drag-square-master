package com.swifty.dragsquareimage;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by swifty on 29/11/2016.
 */

public class DraggablePresenterImpl implements DraggablePresenter, DraggableSquareView.Listener {
    private final DraggableSquareView dragSquare;
    private int imageStatus;
    private boolean isModify;
    private Activity activity;
    private Fragment fragment;

    public DraggablePresenterImpl(@NonNull Activity activity, @NonNull DraggableSquareView dragSquare) {
        this.activity = activity;
        this.dragSquare = dragSquare;
        this.dragSquare.post(new Runnable() {
            @Override
            public void run() {
                DraggablePresenterImpl.this.dragSquare.requestLayout();
            }
        });
        dragSquare.setListener(this);
    }

    public DraggablePresenterImpl(@NonNull Fragment fragment, @NonNull DraggableSquareView dragSquare) {
        this.fragment = fragment;
        this.dragSquare = dragSquare;
        this.dragSquare.post(new Runnable() {
            @Override
            public void run() {
                DraggablePresenterImpl.this.dragSquare.requestLayout();
            }
        });
        dragSquare.setListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PHOTO && resultCode == Activity.RESULT_OK) {
            beginCrop(Crop.getOutputFileUri());
        } else if (requestCode == Crop.REQUEST_PICK && resultCode == Activity.RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            Crop.clearCacheFile();
            handleCrop(resultCode, result);
        }
    }

    @Override
    public void beginCrop(Uri source) {
        if (activity != null) {
            Uri destination = Uri.fromFile(new File(activity.getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
            Crop.of(source, destination).asSquare().start(activity);
        } else if (fragment != null) {
            Uri destination = Uri.fromFile(new File(fragment.getActivity().getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
            Crop.of(source, destination).asSquare().start(fragment.getActivity(), fragment);
        }
    }

    @Override
    public void handleCrop(int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            String imagePath = uri.toString();
            dragSquare.fillItemImage(imageStatus, imagePath, isModify);

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(dragSquare.getContext(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void pickImage(int imageStatus, boolean isModify) {
        this.imageStatus = imageStatus;
        this.isModify = isModify;
        if (activity != null) {
            Crop.pickImage(activity);
        } else if (fragment != null) {
            Crop.pickImage(fragment);
        }
    }

    @Override
    public void takePhoto(int imageStatus, boolean isModify) {
        this.imageStatus = imageStatus;
        this.isModify = isModify;
        if (activity != null) {
            Crop.takePhoto(activity);
        } else if (fragment != null) {
            Crop.takePhoto(fragment);
        }
    }

    @Override
    public SparseArray<String> getImageUrls() {
        return dragSquare.getImageUrls();
    }

    @Override
    public void setImages(String... imageUrls) {
        if (imageUrls == null) return;
        for (int i = 0; i < (imageUrls.length > dragSquare.getImageSetSize() ? dragSquare.getImageSetSize() : imageUrls.length); i++) {
            dragSquare.fillItemImage(imageStatus, imageUrls[i], false);
        }
    }

    @Override
    public void setCustomActionDialog(ActionDialog actionDialog) {
        dragSquare.setCustomActionDialog(actionDialog);
    }
}
