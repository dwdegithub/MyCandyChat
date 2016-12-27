package com.candychat.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candychat.request.DateService;
import com.candychat.request.IDateService;
import com.candychat.ui.WebViewActivity;
import com.candychat.widget.CircleImageView;
import com.candychat.widget.FontTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.candychat.Constants;
import com.candychat.CurrentUser;
import com.candychat.DateSettings;
import com.candychat.R;
import com.candychat.UserManager;
import com.candychat.inter.CurrentUserInfoListener;
import com.candychat.model.CandyModel;
import com.candychat.ui.HomeActivity;
import com.candychat.utils.BitmapUtils;
import com.candychat.utils.Utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dw on 2016/9/6.
 */
public class MeFragment extends BaseImagePickFragment implements View.OnClickListener,CurrentUserInfoListener {

    public static final String TAG = "MeFragment";
    private CurrentUser currentUser;
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private File mIconFile;
    private HomeActivity mController;
    private IDateService dateService;
    private CircleImageView circleImageView;
    private FontTextView tv_target;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mController = (HomeActivity) getActivity();
        UserManager.getInstance().addCurrentUserInfoListener(this);
        currentUser = UserManager.getInstance().getCurrentUser();
        initView(view);
    }

    private void initView(View view) {
        view.findViewById(R.id.rl_target).setOnClickListener(this);
        circleImageView = (CircleImageView) view.findViewById(R.id.circle_portrait);
        circleImageView.setOnClickListener(this);
//        if (currentUser.getUrl_small() != null) {
//            ImageLoader.getInstance().displayImage(currentUser.getUrl_small(),circleImageView,options);
//        }
        view.findViewById(R.id.tv_feed_back).setOnClickListener(this);
        view.findViewById(R.id.tv_term_of_us).setOnClickListener(this);
        view.findViewById(R.id.tv_rate_us).setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circle_portrait:
                showImageSourceChooseDialog();
                break;
            case R.id.tv_feed_back:
//                Utils.feedback(mController, UserManager.getInstance().getCurrentUser().getId()+"", getString(R.string.feedback_title_freezed));
                break;
            case R.id.tv_term_of_us:
                Intent intent = new Intent(mController, WebViewActivity.class);
                intent.putExtra(WebViewActivity.PARAM_KEY_TITLE, getString(R.string.term_of_use));
//                intent.setData(Uri.parse(getString(R.string.url_terms_of_use)));
                startActivity(intent);
                break;
            case R.id.tv_rate_us:
                Utils.searchApplicationInPlayStore(mController, mController.getPackageName());
                break;
            default:
                break;
        }
    }


    private void scaleImageAndRequest(final String imagePath) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                boolean scaleImageComplete = false;
                Bitmap bitmap = BitmapUtils.scaleBitmap(DateSettings.MAX_IMAGE_SIZE, imagePath);
                File tempFile = null;
                if (bitmap != null) {
                    try {
                        tempFile = Utils.createFile(Constants.getInstance().getTEMP_DIR());
//                        tempFile = Utils.createFile(mController.getCacheDir());
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                        scaleImageComplete = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Runnable task = null;
                if (tempFile != null && tempFile.exists() && scaleImageComplete) {
                    final File imageFile = tempFile;
                    task = new Runnable() {
                        @Override
                        public void run() {
                            upLoadImage(imageFile);
                        }
                    };
                } else {
                    task = new Runnable() {
                        @Override
                        public void run() {
//                            ToastUtils.show(mController, R.string.scale_image_failed, Toast.LENGTH_SHORT);
                        }
                    };
                }
                if (task != null) {
                    CandyModel.runOnMainThread(task);
                }

            }
        };
        thread.start();
    }

    private void showUserImage(String url, boolean checkResult) {
        dateService = new DateService(mController);
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).considerExifParams(true).build();
        ImageLoadingListener listener = checkResult ? new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mIconFile = mImageLoader.getDiskCache().get(imageUri);

                scaleImageAndRequest(mIconFile.getPath());


            }
        } : null;
        mImageLoader.displayImage(url, circleImageView, options, listener);
    }

    private void upLoadImage(File mIconFile) {


    }


    @Override
    protected void onImageLoaded(Uri imageUri) {
        showUserImage(imageUri.toString(), true);
    }

    @Override
    protected void onImageLoadFailed() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dateService != null) {
            dateService.release();
        }
    }

    @Override
    public void onCurrentUserInfoChange(CurrentUser currentUser) {

    }

    @Override
    public void onCurrentUserVipStatusChanged(int vipType) {

    }

    @Override
    public void onCurrentUserPortrait(String url_small, String url_big) {

    }
}
