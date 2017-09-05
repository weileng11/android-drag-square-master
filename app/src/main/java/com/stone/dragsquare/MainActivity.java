package com.stone.dragsquare;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.swifty.dragsquareimage.DraggablePresenter;
import com.swifty.dragsquareimage.DraggablePresenterImpl;
import com.swifty.dragsquareimage.DraggableSquareView;

public class MainActivity extends AppCompatActivity {

    private TextView contentText;
    private DraggablePresenter draggablePresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("编辑个人资料");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DraggableSquareView dragSquare = (DraggableSquareView) findViewById(R.id.drag_square);
        contentText = (TextView) findViewById(R.id.contentText);
        draggablePresent = new DraggablePresenterImpl(this, dragSquare);
        draggablePresent.setCustomActionDialog(new MyActionDialog(this));
        draggablePresent.setImages(new String[]{"http://lorempixel.com/400/400?flag=0", "http://lorempixel.com/400/400?flag=1", "http://lorempixel.com/400/400?flag=2", "http://lorempixel.com/400/400?flag=3", "http://lorempixel.com/400/400?flag=4", "http://lorempixel.com/400/400?flag=5"});
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        draggablePresent.onActivityResult(requestCode, resultCode, result);
    }

    /**
     * 根据Uri获取图片文件的绝对路径
     */
    public String getAbsolutePath(final Uri uri) {
        if (null == uri) {
            return null;
        }

        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void showUrls(View view) {
        SparseArray<String> array = draggablePresent.getImageUrls();
        if (array == null) return;
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            String o = array.get(array.keyAt(i));
            stringBuffer.append(i).append(":").append(o).append("\n");
        }
        contentText.setText(stringBuffer.toString());
    }
}
