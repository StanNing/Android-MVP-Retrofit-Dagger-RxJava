//package com.focuschina.ehealth_nj.ui.account;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.focuschina.ehealth_lib.base.BaseActivity;
//import com.focuschina.ehealth_nj.R;
//
///**
// * Copyright (C) Focus Technology
// *
// * @author Ning jiajun
// * @ClassName: LoginActivity 登录界面
// * @date 16/5/12 上午10:08
// */
//public class LoginActivity extends BaseActivity implements AccountContract.AccountView {
//
//    private AccountContract.Presenter mPresenter;
//
//    private EditText phone, code;
//    private TextView codeTv, sure;
//    private ImageView zhifubao, myNj;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        super.setContentView(R.layout.activity_login);
//        initView();
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return 0;
//    }
//
//    @Override
//    protected void initTitle() { //初始化标题
//        super.initTitle();
//        title.setBackgroundResource(R.color.transparent);
//        reg_title_left.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    protected void initView() {
//        phone = (EditText) findViewById(R.id.phone_et);
//        code = (EditText) findViewById(R.id.code_et);
//        codeTv = (TextView) findViewById(R.id.getcode_tv);
//        sure = (TextView) findViewById(R.id.sure_tv);
//        zhifubao = (ImageView) findViewById(R.id.zhifubao_iv);
//        myNj = (ImageView) findViewById(R.id.my_nj_iv);
//
//        bindClickEvent(sure);
//        bindClickEvent(codeTv);
//        bindClickEvent(zhifubao);
//    }
//
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        switch (v.getId()) {
//            case R.id.sure_tv:
//                turnToNext();
////                verifyAccountIsExist(false, "18795891355"); //test
//                break;
//            case R.id.getcode_tv: //获取验证码
//                doGetCheckCode();
//                break;
//            case R.id.zhifubao_iv: //支付宝第三方登录
//                mPresenter.doZhiFuBaoLogin();
//                break;
//        }
//    }
//
//    /**
//     * 点击获取验证码按钮
//     */
//    private void doGetCheckCode() {
//        String phoneInput = phone.getText().toString();
//        if (!mPresenter.validateInput(new String[]{phoneInput}, new String[]{"请填写正确的手机号"})) return;
//        //先发送请求
//        mPresenter.getCheckCode(new CheckCode.QueryParam(BaseApi.NULL_PARAM, phoneInput, CheckCode.REG));
//        codeTv.setEnabled(false);
//        TimerTaskUtil.countdown(60).subscribe(integer -> codeTv.setText("获取验证码" + integer + "秒"),
//                throwable -> {
//                },
//                () -> { //on complete 计时完成
//                    codeTv.setText("获取验证码");
//                    codeTv.setEnabled(true);
//                });
//    }
//
//    //确定点击,下一步判断
//    private void turnToNext() {
//        String phoneInput = phone.getText().toString();
//        String codeInput = codeTv.getText().toString();
//        if (mPresenter.validateInput( //效验输入内容
//                new String[]{phoneInput, codeInput},
//                new String[]{"请填写正确的手机号", "请填写正确的验证码"})) {
//            mPresenter.canUserRegister(new CanUserRegister.QueryParam(phoneInput, codeInput, BaseApi.NULL_PARAM));
//        }
//    }
//
//    //验证通过
//    @Override
//    public void verifyAccountIsExist(boolean isExist, String phone) {
//        if (isExist) { //已经存在,直接进入手势密码输入界面
//            LoginGLockVerifyActivity.startForResult(this, phone, ComConstant.ActReqCode.login);
//        } else { //不存在,则进入用户信息完善界面
//            LoginIdVerifyActivity.startForResult(this, phone, ComConstant.ActReqCode.login);
//        }
//    }
//
//    @Override
//    public void setPresenter(AccountContract.Presenter presenter) {
//        mPresenter = presenter;
//    }
//
//    @Override
//    protected void clearTask() {
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case ComConstant.ActReqCode.login:
//                if (resultCode == ComConstant.ActResultCode.login_suc) { //登录成功，结束界面
//                    setResult(ComConstant.ActResultCode.login_suc);
//                    finish();
//                }
//                break;
//            default:
//                break;
//        }
//    }
//}
