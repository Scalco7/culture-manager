package br.com.culture.manager.cultureManager.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public final class AlertDialogUtils {
    private AlertDialogUtils(){}

    public static void showConfirm(Context context, int titleCode, int messageCode, DialogInterface.OnClickListener listenerOk, DialogInterface.OnClickListener listenerCancel){
        showConfirm(context, context.getString(titleCode), context.getString(messageCode), listenerOk, listenerCancel);
    }

    public static void showConfirm(Context context, String title, int messageCode, DialogInterface.OnClickListener listenerOk, DialogInterface.OnClickListener listenerCancel){
        showConfirm(context, title, context.getString(messageCode), listenerOk, listenerCancel);
    }

    public static void showConfirm(Context context, int titleCode, String message, DialogInterface.OnClickListener listenerOk, DialogInterface.OnClickListener listenerCancel){
        showConfirm(context, context.getString(titleCode), message, listenerOk, listenerCancel);
    }

    public static void showConfirm(Context context, String title, String message, DialogInterface.OnClickListener listenerOk, DialogInterface.OnClickListener listenerCancel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, listenerOk);
        builder.setNegativeButton(android.R.string.cancel, listenerCancel);

        AlertDialog alert = builder.create();
        alert.show();
    }
}
