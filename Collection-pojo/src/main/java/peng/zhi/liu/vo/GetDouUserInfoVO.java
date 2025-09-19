package peng.zhi.liu.vo;

import peng.zhi.liu.entity.GetDouUserInfo;

import java.util.List;

public class GetDouUserInfoVO {
    private List<GetDouUserInfo> getDouUserInfoList;

    public List<GetDouUserInfo> getGetDouUserInfoList() {
        return getDouUserInfoList;
    }

    public void setGetDouUserInfoList(List<GetDouUserInfo> getDouUserInfoList) {
        this.getDouUserInfoList = getDouUserInfoList;
    }

    public GetDouUserInfoVO(List<GetDouUserInfo> getDouUserInfoList) {
        this.getDouUserInfoList = getDouUserInfoList;
    }
    public GetDouUserInfoVO(){}
}
