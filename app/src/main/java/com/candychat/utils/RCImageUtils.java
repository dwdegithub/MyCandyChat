package com.candychat.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class RCImageUtils {

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        final int height;
        final int width;
        height = options.outHeight;
        width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width < height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        if (inSampleSize != 1 && inSampleSize % 2 > 0) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    public static Options getImageSourceOptions(Options opts, File imageFile) {
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getPath(), opts);
        return opts;
    }

    public static Options getImageSourceOptions(Options opts, InputStream in) {
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, opts);
        return opts;
    }

    public static int getImageAngle(String path) {
        int angle = 0;
        try {
            ExifInterface exif = new ExifInterface(path);
            switch (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return angle;
    }

    public static int getUriImageAngel(Context context, Uri imageUri) {

        Cursor cursor = null;
        int angle = 0;

        try {
            String[] filePathColumn = { Media.DATA, Media.ORIENTATION };
            ContentResolver cr = context.getContentResolver();
            cursor = cr.query(imageUri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();

                // 获取照片的旋转方向
                int orientationIndex = cursor.getColumnIndex(filePathColumn[1]);
                String orientation = cursor.getString(orientationIndex);
                if (orientation != null) {
                    if (orientation.equals("90")) {
                        angle = 90;
                    } else if (orientation.equals("180")) {
                        angle = 180;
                    } else if (orientation.equals("270")) {
                        angle = 270;
                    }
                } else {
                    angle = readBmpDegree(getRealPath(context, imageUri));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 无论什么情况，最终保证cursor得到释放
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        return angle;
    }

    @SuppressLint("NewApi")
    public static String getRealPath(Context context, Uri uri) {
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
                    contentUri = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

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
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     * 
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

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
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static int readBmpDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return degree;
        }

        return degree;
    }

    public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight, int rotateAngel) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        // Calculate inSampleSize
        int inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight, rotateAngel);
        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        System.gc();
        try {
            Bitmap tempBitmap = BitmapFactory.decodeFile(filename, options);
            if (rotateAngel != 0) {
                Matrix matrix = new Matrix();
                matrix.setRotate(rotateAngel, tempBitmap.getWidth() / 2, tempBitmap.getHeight() / 2);
                Bitmap result = Bitmap.createBitmap(tempBitmap, 0, 0, tempBitmap.getWidth(), tempBitmap.getHeight(), matrix, true);
                tempBitmap.recycle();
                tempBitmap = result;
            }
            return tempBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight, int rotateAngel) {
        // Raw height and width of image
        final int height;
        final int width;
        if (rotateAngel == 0 || rotateAngel == 180) {
            height = options.outHeight;
            width = options.outWidth;
        } else {
            height = options.outWidth;
            width = options.outHeight;
        }
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width < height) {
                inSampleSize = (int) Math.ceil((float) height / (float) reqHeight);
            } else {
                inSampleSize = (int) Math.ceil((float) width / (float) reqWidth);
            }
        }
        // if (inSampleSize != 1 && inSampleSize % 2 == 1)
        // inSampleSize++;
        return inSampleSize;
    }

    public static void recyleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public static void notifyAlbumInsertToContentProvider(Context context, File imageFile) {
        try {
            ContentValues localContentValues = new ContentValues();
            localContentValues.put(Media.DATA, imageFile.toString());
            localContentValues.put(Media.DESCRIPTION, "camera image");
            localContentValues.put(Media.MIME_TYPE, "image/png");
            long addTime = System.currentTimeMillis() / 1000;
            localContentValues.put(Media.DATE_ADDED, addTime);
            localContentValues.put(Media.DATE_MODIFIED, addTime);
            ContentResolver localContentResolver = context.getContentResolver();
            Uri localUri = Media.EXTERNAL_CONTENT_URI;
            localContentResolver.insert(localUri, localContentValues);
        } catch (Exception e) {
            try {
                MediaScannerConnection.scanFile(context, new String[] { imageFile.getPath() }, new String[] { "png" }, null);
            } catch (Exception e2) {
                notifyAlbum(context, imageFile);
            }
        }
    }

    public static void notifyAlbum(Context context, File imageFile) {
        Uri localUri = Uri.fromFile(imageFile);
        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
        context.sendBroadcast(localIntent);
    }
}
