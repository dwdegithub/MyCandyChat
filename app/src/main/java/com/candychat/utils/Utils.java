package com.candychat.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.candychat.Constants;
import com.candychat.DateSettings;
import com.candychat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ZN_mager on 2016/5/9.
 */
public class Utils {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "dw";

//    public static boolean checkPlayServices(Activity activity) {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
////                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
////                        .show();
//            } else {
//                LogUtils.e(TAG, "This device is not supported.");
//            }
//            return false;
//        }
//        return true;
//    }

    public static String getDeviceId(Context context) {
//        return Settings.Secure.getString(context.getContentResolver(),
//                Settings.Secure.ANDROID_ID);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = DateSettings.APPLICATION_TOKEN;
        }
        return deviceId;
    }

    public static String getTimeZone(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        Calendar calendar = Calendar.getInstance(configuration.locale);
        TimeZone timeZone = calendar.getTimeZone();
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        String name = timeZone.getID() + "/" + timeZone.getDisplayName();
        return name;
    }

    public static String getGooleAccount(Context context) {
        Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
        if (accounts != null && accounts.length != 0) {
            String myEmailid = accounts[0].name;
            return myEmailid;
        }
        return null;
    }

    public static File createFile(File parent) throws IOException {
        File tempFile = new File(parent, System.currentTimeMillis() + "");
        boolean parentExists = parent.exists() && parent.isDirectory();
        if (!parentExists) {
            parentExists = parent.mkdirs();
        }
        if (parentExists) {
            return tempFile.createNewFile() ? tempFile : null;
        }
        return null;
    }

    public static void copyToFile(InputStream in, File target) throws IOException {
        FileOutputStream fos = new FileOutputStream(target);
        try {
            copy(in, fos);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * will not close stream
     *
     * @param in
     * @param fos
     * @throws IOException
     */
    public static void copy(InputStream in, FileOutputStream fos) throws IOException {
        byte buffer[] = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.flush();
    }

    private static DecimalFormat mDistanceFormat = new DecimalFormat("0.0");
    private static DecimalFormat mPriceFormat = new DecimalFormat("0.00");
    private static DecimalFormat mPercent = new DecimalFormat("0%");

    public static String formatDistance(float distance) {
        return mDistanceFormat.format(distance);
    }

    public static String formatPrice(float price) {
        return mPriceFormat.format(price);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static String formatPrice(String price){
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("es"));
        Number parsedNumber = null;
        float parsedValueFloat = 0;
        try {
            parsedNumber = nf.parse(price);
            parsedValueFloat = parsedNumber.floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedValueFloat + "";
    }

    public static String formatPercent(float percent) {
        return mPercent.format(percent);
    }

    public static String formatTime(long time) {
        Date d = new Date(time);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return s.format(d);
    }

    public static String formatTimeYMD(long time) {
        Date d = new Date(time);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        return s.format(d);
    }

    public static String formatTimeMD(long time) {
        Date d = new Date(time);
        SimpleDateFormat s = new SimpleDateFormat("MM-dd");
        return s.format(d);
    }

    public static String formatTimeH(long time) {
        Date d = new Date(time);
        SimpleDateFormat s = new SimpleDateFormat("HH:mm");
        return s.format(d);
    }

    public static int getMonthInterval(long date1, long dateNow) {

        return getMonthInterval(new Date(date1), new Date(dateNow));

    }

    public static int getMonthInterval(String date1, String currentDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return getMonthInterval(sdf.parse(date1), sdf.parse(currentDate));
    }

    public static int getMonthInterval(Date date1, Date currentDate) {

        int result = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(date1);
        c2.setTime(currentDate);

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return Math.abs(result);
    }

    public static int getDayInterval(long currentTime, long time2) {

        int result = 0;
        Date date1 = new Date(currentTime);
        Date date2 = new Date(time2);

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(date1);
        c2.setTime(date2);

        result = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

        return result;

    }

    public static String timeCustomFormat(long targetTime, long currentTime) {
        String result = null;
        long s = currentTime;
        int dayInterval = Utils.getDayInterval(currentTime, targetTime);
        if (dayInterval < 1) {
            float hour = (currentTime - targetTime) / 1000 / 60 / 60;
            result = Utils.formatDistance(hour < 0.5f ? 0.5f : hour) + "hours";
        } else if (dayInterval > 0 && dayInterval < 7) {
            result = dayInterval + 1 + "days";
        } else if (dayInterval >= 7 && dayInterval <= 28) {
            result = dayInterval / 7 + 1 + "week";
        } else if (dayInterval > 28) {
            result = Utils.getMonthInterval(targetTime, currentTime) + 1 + "month";
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getContentUriRealDiskPath(Context context, Uri uri) {
        if (uri.toString().startsWith("content://com.google.android.apps.photos.content")) {
            return null;
        }
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static void searchApplicationInPlayStore(Context context, String packageName) {
        StringBuilder sb = new StringBuilder();
        sb.append("market://details?id=").append(packageName);
        Uri u = Uri.parse(sb.toString());
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, u));
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + packageName)));
        }
    }

    public static void showAlertDialog(Context context, int positiveButtonResId, int negativeButtnResId, int messageResId, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setPositiveButton(positiveButtonResId, listener)
                .setNegativeButton(negativeButtnResId, listener)
                .setMessage(messageResId)
                .create();
        dialog.show();
    }


    public static void feedback(Context context, String userId, String title) {
        try {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", context.getString(R.string.feedback_email), null));
            sendIntent.putExtra(Intent.EXTRA_TEXT, buildFeedbackText(context, userId));
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);
            context.startActivity(sendIntent);
        } catch (Exception e) {
        }
    }

    private static String buildFeedbackText(Context context, String userId) {
        return context.getString(R.string.feedback_message, userId, DateSettings.COUNTRY, getVersion(context), Constants.SDK_VERSION);
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasSoftKeys(Activity context) {
        WindowManager windowManager = context.getWindowManager();
        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }


    public static int getVer(Context context) {
        PackageInfo info = null;
        PackageManager manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return info.versionCode;
    }

    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
