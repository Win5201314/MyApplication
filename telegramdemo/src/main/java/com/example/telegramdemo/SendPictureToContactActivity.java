package com.example.telegramdemo;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

import com.example.telegramdemo.util.ToastUtil;

public class SendPictureToContactActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton send;
    private AppCompatImageView choosePicture;

    private Context context;
    private String path = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendpicturecontact);
        send = findViewById(R.id.send);
        send.setOnClickListener(this);
        choosePicture = findViewById(R.id.choosePicture);
        choosePicture.setOnClickListener(this);
        context = this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send: {
                ToastUtil.normalShow(this, "已经发送广播!", true);
                Intent intent = new Intent();
                intent.setAction("sendPictureToContact");
                intent.putExtra("path", path);
                //发送广播
                sendBroadcast(intent);
                break;
            }
            case R.id.choosePicture: {
                openAlbum();
                break;
            }
        }
    }

    private static final int CHOOSE_ALBUM = 132;
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"), CHOOSE_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_ALBUM) {
            if (resultCode == RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) {
                    handleImageOnKitKat(data);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            /*Picasso.with(context).load(imagePath)
                    //.resize(200, 200)
                    .centerCrop()
                    .into(head);*/
            path = imagePath;
            Log.d("path", path);
            if (bitmap != null) {
                ToastUtil.normalShow(context, "图片如果没有显示，请直接点击保存！", true);
                choosePicture.setImageBitmap(bitmap);
            }
        } else {
            ToastUtil.normalShow(context, "获取图片失败!", true);
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
