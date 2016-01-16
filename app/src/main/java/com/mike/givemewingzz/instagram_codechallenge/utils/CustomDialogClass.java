package com.mike.givemewingzz.instagram_codechallenge.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import com.mike.givemewingzz.instagram_codechallenge.R;

/**
 * Created by michael.tirkey on 1/8/15.
 */
public class CustomDialogClass extends Dialog {

    public Activity c;
    public Dialog d;

    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdialog);

    }

}
