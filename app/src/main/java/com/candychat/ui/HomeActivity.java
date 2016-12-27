package com.candychat.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.candychat.R;
import com.candychat.request.DateService;
import com.candychat.request.IDateService;
import com.candychat.ui.fragment.DiscoverFragment;
import com.candychat.ui.fragment.HistoryFragment;
import com.candychat.ui.fragment.MeFragment;
import com.candychat.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Hashtable;
import java.util.Map;


/**
 * Created by dw on 2016/8/26.
 */
public class HomeActivity extends BaseIMActivity implements RadioGroup.OnCheckedChangeListener {

    public static final int REQUST_CODE_FILTER = 7001;
    private IDateService mDateService;
    private Map<String, Fragment> mFragments = new Hashtable<>();
    private RadioGroup rg_homebar;
    private ImageView rb_discover;
    private ImageView rb_history;
    private ImageView rb_me;
    private ImageView iv_unread_point;

    private FragmentManager mFragmentManager;

    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mIconOptions = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.RGB_565)
            .cacheOnDisk(true)
//            .showImageForEmptyUri(R.mipmap.ic_loading_icon)
//            .showImageOnLoading(R.mipmap.ic_loading_icon)
//            .showImageOnFail(R.mipmap.ic_loading_icon)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        Log.e("dw", " w " + display.getWidth() +" h "+display.getHeight());
//        Log.e("dw", " density " + getResources().getDisplayMetrics().density);
    }

    private void init() {
        initView();
        initData();
        LogUtils.e("dw", " start HomeActivity !");
    }


    private void initData() {
        mDateService = new DateService(this);


    }

    private void initView() {
        mFragmentManager = getSupportFragmentManager();
        mFragments.put(MeFragment.TAG, new MeFragment());
        mFragments.put(HistoryFragment.TAG, new HistoryFragment());
        mFragments.put(DiscoverFragment.TAG, new DiscoverFragment());

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (String key : mFragments.keySet()) {
            fragmentTransaction.add(R.id.home_fragment_container, mFragments.get(key), key);
            fragmentTransaction.hide(mFragments.get(key));
        }
        fragmentTransaction.commit();
        showFragment(DiscoverFragment.TAG);
        rg_homebar = (RadioGroup) findViewById(R.id.rg_homebar);
        rg_homebar.setOnCheckedChangeListener(this);
//        iv_discover = (RadioButton) findViewById(R.id.iv_discover);
//        rb_me = (RadioButton) findViewById(R.id.iv_me);
//        rb_history = (RadioButton) findViewById(R.id.iv_history);
//        rb_unread_point = (ImageView) findViewById(R.id.iv_unread_point);

    }

    private int selectedFragment = R.id.rb_discover;

    private void showFragment(String targetKey) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (String key : mFragments.keySet()) {
            if (key.equals(targetKey)) {
                fragmentTransaction.show(mFragments.get(key));
            } else {
                fragmentTransaction.hide(mFragments.get(key));
            }
        }
        fragmentTransaction.commit();
    }

    public static void startHomeActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (selectedFragment == checkedId) {
            return;
        }
        selectedFragment = checkedId;
        switch (checkedId) {
            case R.id.rb_discover:
                showFragment(DiscoverFragment.TAG);
                break;
            case R.id.rb_history:
                showFragment(HistoryFragment.TAG);
                break;
            case R.id.rb_me:
                showFragment(MeFragment.TAG);
                break;
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PayActivity.REQUEST_PAY_CODE:
//                    UserManager.getInstance().setCurrentUserVipType(CurrentUser.PAY_VIP);
//                    break;
//                case ChoicePayActivity.REQUEST_CHOICE_PAY_CODE:
//                    int actionCode = data.getIntExtra("ActionCode", 0);
//                    if (actionCode == PayActivity.REQUEST_PAY_CODE) {
//                        UserManager.getInstance().setCurrentUserVipType(CurrentUser.PAY_VIP);
//                        break;
//                    }
//
//                    if (actionCode == ChoicePayActivity.REQUEST_CHOICE_PAY_CODE) {
//                        ((NearbyFragment) mFragments.get(NearbyFragment.TAG)).onTryOnceSuccess();
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDateService.release();
        Log.e("dw", " homeactivity ondestroy");
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
