package vn.gomisellers.apps.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import vn.gomisellers.apps.R;

public class AlertDialogs {

    public static void show(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.title_oops);
        builder.setMessage(Utils.fromHtml(msg));

        builder.setNegativeButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void show(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(Utils.fromHtml(msg));

        builder.setNegativeButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void show(Context context, String title, String msg, String textNegative, String textPositive,
                            final OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(Utils.fromHtml(msg));

        builder.setNegativeButton(textNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onNegativeButtonClick(dialog, which);
            }
        });

        builder.setPositiveButton(textPositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onPositiveButtonClick(dialog, which);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    /**
     *
     */
    public interface OnClickListener {

        void onNegativeButtonClick(DialogInterface dialog, int which);

        void onPositiveButtonClick(DialogInterface dialog, int which);
    }
}
