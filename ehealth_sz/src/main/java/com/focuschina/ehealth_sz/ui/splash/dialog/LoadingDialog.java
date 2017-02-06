package com.focuschina.ehealth_sz.ui.splash.dialog;

import android.os.Bundle;
import android.widget.ImageView;

import com.focuschina.ehealth_lib.base.BaseDialog;
import com.focuschina.ehealth_lib.util.BmpUtil;
import com.focuschina.ehealth_sz.R;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: LoadingDialog
 * @Description: TODO: (加载弹窗)
 * @date 2017/1/3 下午5:23
 */
public class LoadingDialog extends BaseDialog {

    private static final String TAG = "LoadingDialog";

    private BmpUtil bmpUtil;

    public static LoadingDialog newInstance(BmpUtil bmpUtil) {
        LoadingDialog dialog = new LoadingDialog();
        dialog.bmpUtil = bmpUtil;
        return dialog;
    }

    @Override
    protected String getDialogTag() {
        return TAG;
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_loading;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.LoadingDialogStyle;
    }

    @Override
    protected void bindView(Bundle bundle) {
        super.bindView(bundle);
        getDialog().setCanceledOnTouchOutside(false);
        ImageView loadingIv = findViewById(R.id.dialog_loading_iv);
        bmpUtil.displayGif(R.drawable.loading, loadingIv);
    }
}
