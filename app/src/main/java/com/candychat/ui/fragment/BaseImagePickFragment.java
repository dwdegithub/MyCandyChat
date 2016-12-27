package com.candychat.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;


import com.candychat.Constants;
import com.candychat.utils.LogUtils;
import com.candychat.utils.Utils;

import java.io.File;
import java.io.IOException;

/**
 * 需要獲取圖片的頁面的基類,可以繼承它來實現調用系統相機或調用系統相冊獲取圖片uri的功能.
 * <p/>
 * Copyright: RcPlatform,Inc Copyright (c) 2013-12-25 下午6:44:24
 * <p/>
 * Team:RcPlatform Beijing
 * <p/>
 *
 * @author ZhaoNan
 * @version 1.0.0
 * @date 2013-12-25
 */
public abstract class BaseImagePickFragment extends BaseFragment {


    private static final int REQUEST_CODE_CAMERA = 300;

    private static final int REQUEST_CODE_ALBUM = 301;

    private static final int REQUEST_CODE_HANDLE_IMAGE = 302;

    private static final String INSTANCE_KEY_IMAGE_URI = "image_uri";
    private static final String TAG = "BaseImagePick";

    protected Dialog mSourceChooseDialog;
    private String mImageSources[];

    private Uri mImageUri;
    private File mImageTempFile;

    @Override
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mImageSources = new String[]{"Camera",
               "Photo Library"
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreSavedInstanceState(savedInstanceState);
        initDialog();
    }

    private void restoreSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_KEY_IMAGE_URI)) {
            mImageUri = savedInstanceState.getParcelable(INSTANCE_KEY_IMAGE_URI);
            LogUtils.e(TAG, "restore save instance " + mImageUri);

        }
    }


    private void initDialog() {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startCamera();
                        break;
                    case 1:
                        startAlbum();
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(mImageSources, listener);
        mSourceChooseDialog = builder.create();

    }

    /**
     * 顯示圖片來源選擇對話框,來源包括系統相機和系統相冊.
     *
     * @author ZhaoNan
     * @date 2013-12-25
     */
    protected void showImageSourceChooseDialog() {
        mSourceChooseDialog.show();
    }

    protected void startAlbum() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }

    protected void startCamera() {
        try {
            mImageTempFile = Utils.createFile(Constants.getInstance().getTEMP_DIR());
//            mImageTempFile = Utils.createFile(getActivity().getCacheDir());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // ContentValues values = new ContentValues();
            mImageUri = Uri.fromFile(mImageTempFile);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(INSTANCE_KEY_IMAGE_URI, mImageUri);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e(TAG, "fragment activity result " + requestCode + "...." + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    handleCameraResult(data);
                    break;
                case REQUEST_CODE_ALBUM:
                    handleAlbumResult(data);
                    break;
                case REQUEST_CODE_HANDLE_IMAGE:
                    break;
                default:
                    break;
            }
        }
    }

    private void handleAlbumResult(Intent data) {
        Uri resultData = data.getData();
        if (resultData != null) {
            mImageUri = resultData;
        } else {
            onImageLoadFailed();
        }
        onImageLoaded(mImageUri);
    }

    private void handleCameraResult(Intent data) {
        LogUtils.e(TAG, "camera result");
        if (data != null) {
            Uri resultData = data.getData();
            if (data.getData() != null) {
                mImageUri = resultData;
            }
        }
        LogUtils.e(TAG, "camera image uri " + mImageUri);
        onImageLoaded(mImageUri);
    }

    /**
     * 當圖片獲取成功時會調用該方法.
     *
     * @param imageUri 圖片所在的uri.
     * @author ZhaoNan
     * @date 2013-12-25
     */
    protected abstract void onImageLoaded(Uri imageUri);

    /**
     * 當獲取圖片失敗會調用該方法.
     *
     * @author ZhaoNan
     * @date 2013-12-25
     */
    protected abstract void onImageLoadFailed();


}
