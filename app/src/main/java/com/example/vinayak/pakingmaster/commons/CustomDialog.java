package com.example.vinayak.pakingmaster.commons;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.example.vinayak.pakingmaster.R;


/**
 * Created by rajk on 24/10/17.
 */

public class CustomDialog extends Dialog {

    Context context;

    public CustomDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
    }

    @Override
    public void onAttachedToWindow() {

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        super.onAttachedToWindow();
    }

    public void showMe() {
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        this.show();
    }
}
