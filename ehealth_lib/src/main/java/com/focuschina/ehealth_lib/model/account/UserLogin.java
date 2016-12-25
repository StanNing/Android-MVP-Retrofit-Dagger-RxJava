package com.focuschina.ehealth_lib.model.account;

import com.focuschina.ehealth_lib.util.IDCardUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: UserLogin
 * @Description: TODO: (2.4.2 用户登录 返回数据模型 & 对应创建本地数据库存储,表:user)
 * @date 16/6/2 下午7:11
 */
@Entity(nameInDb = "user")
public class UserLogin {
    @Id(autoincrement = true)
    private Long id;
    
    public String sessionId = ""; //登录session

    @Index(unique = true)
    public String accountNo = "";
    public String idNo = "";
    public String phoneNumber = "";
    public boolean isOnline = false;
    public String loginTime = ""; //登录时间

    @Transient //不被写入数据
    public boolean isMale; //是否是男性,根据身份证号判断

    public UserLogin() {
    }

    public UserLogin(String Tag) { //test
        switch (Tag){
            case "test":
                this.sessionId = "";
                this.accountNo = "18795891355";
                this.idNo = "320107198804115016";
                this.phoneNumber = "18795891355";
                break;
        }
    }

    @Generated(hash = 933320008)
    public UserLogin(Long id, String sessionId, String accountNo, String idNo,
            String phoneNumber, boolean isOnline, String loginTime) {
        this.id = id;
        this.sessionId = sessionId;
        this.accountNo = accountNo;
        this.idNo = idNo;
        this.phoneNumber = phoneNumber;
        this.isOnline = isOnline;
        this.loginTime = loginTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean getIsOnline() {
        return this.isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public boolean isMale() {
        return IDCardUtil.checkGender(getIdNo()) == IDCardUtil.MALE;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    /**
     * 如果对象类型是UserLogin 的话 则返回true 去比较hashCode值
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof UserLogin) {
            UserLogin user = (UserLogin) obj;
            if (user.accountNo.equals(this.accountNo)) return true;
        }
        return false;
    }

    /**
     * 重写hashcode 方法，返回的hashCode 不一样才认定为不同的对象
     */
    @Override
    public int hashCode() {
        return accountNo.hashCode();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 请求参数类
     */
    public static class QueryParam {
        public String phoneNumber; //手机号码
        public String userPassword;//用户密码
        public String baiduOpenId; //百度的openid

        public QueryParam(String phoneNumber, String userPassword, String baiduOpenId) {
            this.phoneNumber = phoneNumber;
            this.userPassword = userPassword;
            this.baiduOpenId = baiduOpenId;
        }

//        public QueryParam(String phoneNumber, String userPassword) {
//            this.phoneNumber = phoneNumber;
//            this.userPassword = userPassword;
//            this.baiduOpenId = AppConfig.getBaiDuPushParams()[1];
//        }
    }

}
