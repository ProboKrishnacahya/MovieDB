package com.cahyaa.moviedb.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.cahyaa.moviedb.R;

public class LoadingDialog {

    public static final Dialog loadingDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.layout_loading_dialog, null);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        return dialog;
    }

}
