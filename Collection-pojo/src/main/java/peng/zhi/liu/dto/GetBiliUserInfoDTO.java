package peng.zhi.liu.dto;

import lombok.Data;


public class GetBiliUserInfoDTO {
    private String cookie;
    private String upMid;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUpMid() {
        return upMid;
    }

    public void setUpMid(String upMid) {
        this.upMid = upMid;
    }
}
