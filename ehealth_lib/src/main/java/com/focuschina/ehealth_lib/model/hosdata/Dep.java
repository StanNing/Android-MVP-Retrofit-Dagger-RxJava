package com.focuschina.ehealth_lib.model.hosdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: Dep
 * @Description: TODO: (科室基础信息, 重写equals和hashCode，如果父科室id一致则相等，用来去重)
 * @date 2016/12/22 下午5:03
 */
public class Dep implements Parcelable {
    private String departmentId = ""; //科室编码
    private String departmentName = ""; //科室名称
    private String parentDeptId = ""; //父科室id
    private String parentDeptName = ""; //父科室名
    private String schedueFlag = ""; //排班标记
    private String speciality = ""; //科室擅长
    private String orderNo = ""; //排序码

    public Dep() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (obj instanceof Dep) {
            Dep p = (Dep) obj;
            if (p.parentDeptId.equals(this.parentDeptId)) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return parentDeptId.hashCode();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(String parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public String getParentDeptName() {
        return parentDeptName;
    }

    public void setParentDeptName(String parentDeptName) {
        this.parentDeptName = parentDeptName;
    }

    public String getSchedueFlag() {
        return schedueFlag;
    }

    public void setSchedueFlag(String schedueFlag) {
        this.schedueFlag = schedueFlag;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 请求参数类
     */
    public static class QueryParam {
        public String hospitalCode;

        public QueryParam(String hospitalCode) {
            this.hospitalCode = hospitalCode;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.departmentId);
        dest.writeString(this.departmentName);
        dest.writeString(this.parentDeptId);
        dest.writeString(this.parentDeptName);
        dest.writeString(this.schedueFlag);
        dest.writeString(this.speciality);
        dest.writeString(this.orderNo);
    }

    protected Dep(Parcel in) {
        this.departmentId = in.readString();
        this.departmentName = in.readString();
        this.parentDeptId = in.readString();
        this.parentDeptName = in.readString();
        this.schedueFlag = in.readString();
        this.speciality = in.readString();
        this.orderNo = in.readString();
    }

    public static final Parcelable.Creator<Dep> CREATOR = new Parcelable.Creator<Dep>() {
        @Override
        public Dep createFromParcel(Parcel source) {
            return new Dep(source);
        }

        @Override
        public Dep[] newArray(int size) {
            return new Dep[size];
        }
    };
}
