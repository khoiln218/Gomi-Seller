package vn.gomisellers.apps.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import androidx.annotation.IntDef;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.gomisellers.apps.BuildConfig;
import vn.gomisellers.apps.EappsApplication;

public class MediaHelper {


    public static void dispatchTakePictureIntent(Context context, File imageFile) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null && imageFile != null) {

            Uri photoURI = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", imageFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            ((Activity) context).startActivityForResult(takePictureIntent, GomiConstants.REQUEST_CAMERA);
        }
    }

    public static void dispatchPickPictureIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        ((Activity) context).startActivityForResult(intent, GomiConstants.REQUEST_GALLERY);
    }

    public static boolean getExternalStorageState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getBase64FromBytes(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static String getBase64FromImageUri(Context context, Uri uri, int width, int height) {

        String path = getRealPathFormURI(context, uri);

        if (!Strings.isNullOrEmpty(path)) {
            byte[] bytes = getBytesImagePath(path, width, height);
            return getBase64FromBytes(bytes);
        }

        return null;
    }

    public static String getBase64FromBitmap(Bitmap bitmap) {
        if (bitmap == null)
            return "";

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static byte[] getBytesImagePath(String imagePath, int width, int height) {
        try {
            File file = new File(imagePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, options);

            options.inSampleSize = calculateInSize(options, width, height);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            return stream.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRealPathFormURI(Context context, Uri contentURI) {
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null)
            return contentURI.getPath();
        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private static int calculateInSize(BitmapFactory.Options options, int width, int height) {
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int inSize = 1;

        if (outHeight > height || outWidth > width) {
            int halfHeight = outHeight / 2;
            int halfWidth = outWidth / 2;

            while ((halfHeight / inSize) > height && (halfWidth / inSize) > width)
                inSize *= 2;
        }

        return inSize;
    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = EappsApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public static Uri uriFromFile(File imageFile) {
        return FileProvider.getUriForFile(EappsApplication.getInstance().getAppContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile);
    }

//    public static ArrayList<MediaGallery> getMediaData(@Type.Value int type) {
//        ArrayList<MediaGallery> mediaList = new ArrayList<>();
//
//        ArrayList<String> projectionList = new ArrayList<>();
//        Uri uriExternal, uriInternal;
//
//        switch (type) {
//            case Type.IMAGE:
//                projectionList.add(MediaStore.Images.Media.DATA);
//                projectionList.add(MediaStore.Images.Media._ID);
//
//                uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
//                break;
//
//            case Type.VIDEO:
//                projectionList.add(MediaStore.MediaColumns.DATA);
//                projectionList.add(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
//                projectionList.add(MediaStore.Video.Thumbnails.DATA);
//                projectionList.add(MediaStore.Video.Media.SIZE);
//
//                uriExternal = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                uriInternal = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
//                break;
//
//            default:
//                return mediaList;
//        }
//
//        String[] projection = projectionList.toArray(new String[projectionList.size()]);
//
//        String orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC";
//
//        Context context = AppController.instance().getAppContext();
//        Cursor cursorExternal = context.getContentResolver().query(uriExternal, projection, null, null, orderBy);
//        Cursor cursorInternal = context.getContentResolver().query(uriInternal, projection, null, null, orderBy);
//
//        MergeCursor cursor = new MergeCursor(new Cursor[]{cursorInternal, cursorExternal});
//        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//
//        int colThumb = 0, colSize = 0;
//        if (type == Type.VIDEO) {
//            colThumb = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
//            colSize = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
//        }
//
//        while (cursor.moveToNext()) {
//            MediaGallery media = new MediaGallery();
//            media.setPath(cursor.getString(colIndex));
//            media.setThumbnail(type == Type.VIDEO ? cursor.getString(colThumb) : null);
//            media.setSize(type == Type.VIDEO ? cursor.getLong(colSize) : 0);
//            media.setType(type);
//
//            mediaList.add(media);
//        }
//
//        cursor.close();
//
//        return mediaList;
//    }

    public static long getDurationFilePath(String filePath) {

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filePath);

        long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

        retriever.release();

        return duration;
    }

    public static String convertDuration(long duration) {

        String out = "00:00";

        long hours;
        try {
            hours = (duration / 3600000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return out;
        }

        long remaining_minutes = (duration - (hours * 3600000)) / 60000;
        String minutes = String.valueOf(remaining_minutes);
        if (minutes.equals(0)) {
            minutes = "00";
        }

        long remaining_seconds = (duration - (hours * 3600000) - (remaining_minutes * 60000));
        String seconds = String.valueOf(remaining_seconds);
        if (seconds.length() < 2) {
            seconds = "00";
        } else {
            seconds = seconds.substring(0, 2);
        }

        if (hours > 0) {
            out = hours + ":" + minutes + ":" + seconds;
        } else {
            out = minutes + ":" + seconds;
        }

        return out;
    }


    public static class Type {

        public static final int IMAGE = 0;
        public static final int VIDEO = 1;

        @Retention(RetentionPolicy.SOURCE)
        @IntDef(value = {IMAGE, VIDEO})
        public @interface Value {
        }

        public Type() {
        }
    }
}
