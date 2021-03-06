package com.focuschina.ehealth_lib.model;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * @author Ning Jiajun
 * @ClassName: HSPSService
 * @Description: 服务地址 解析模型
 * @date 2015年6月23日 上午10:58:10
 */
public class HSPSService implements Serializable {
    private static final long serialVersionUID = -9081279842194327927L;

    public String serverurl = "";
    public String timestamp = ""; //最后更新时间戳
    public JsonObject coreservice = new JsonObject(); //返回JSON对象
    public LatestVersion latestversion = new LatestVersion();

    public class LatestVersion implements Serializable {
        private static final long serialVersionUID = 3707706645775982141L;
        private String android = "";
        private String ios = "";

        public LatestVersion() {
        }

        public LatestVersion(String android, String ios) {
            this.android = android;
            this.ios = ios;
        }

        public String getAndroid() {
            return android;
        }

        public void setAndroid(String android) {
            this.android = android;
        }

        public String getIos() {
            return ios;
        }

        public void setIos(String ios) {
            this.ios = ios;
        }
    }

    public HSPSService() {
    }

    public String getServerurl() {
        return serverurl;
    }

    public void setServerurl(String serverurl) {
        this.serverurl = serverurl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public JsonObject getCoreservice() {
        return coreservice;
    }

    public void setCoreservice(JsonObject coreservice) {
        this.coreservice = coreservice;
    }

    public LatestVersion getLatestversion() {
        return latestversion;
    }

    public void setLatestversion(LatestVersion latestversion) {
        this.latestversion = latestversion;
    }
}
