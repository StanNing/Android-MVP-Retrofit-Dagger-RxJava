package com.focuschina.ehealth_lib.model;

/**
 * @ClassName: Response
 * @Description: 联网接口回调数据统一格式外层
 * @author Ning Jiajun
 * @date 2014-11-13 下午4:31:16
 *
 * @param <T>
 */
public class Response<T> {
    private int rspCode;
    private String rspMsg;
    private String oprTime;
    private T rspData;
    public Response(){}

    public static final int SUCCESS = 1;
    public static final int REG_FREE = 2;
    public static final int LOGIN_STATUS_DISABLED = 9004; //30天过期
    public static final int LOGIN_STATUS_OFFLINE = 9005; //被挤下线

    public int getRspCode() {
        return rspCode;
    }

    public void setRspCode(int rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public T getRspData() {
        return rspData;
    }

    public void setRspData(T rspData) {
        this.rspData = rspData;
    }

    public String getOprTime() {
        return oprTime;
    }

    public void setOprTime(String oprTime) {
        this.oprTime = oprTime;
    }

    public Response<T> builder (int rspCode, String rspMsg, String oprTime){
        this.setRspCode(rspCode);
        this.setRspMsg(rspMsg);
        this.setOprTime(oprTime);
        return this;
    }

}
