package peng.zhi.liu.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import peng.zhi.liu.entity.GetDouAllCollectionInfo;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDouCollectionInfoVO {
    private List<GetDouAllCollectionInfo> getDouAllCollectionInfoList = new ArrayList<>();

    public List<GetDouAllCollectionInfo> getGetDouAllCollectionInfoList() {
        return getDouAllCollectionInfoList;
    }

    public void setGetDouAllCollectionInfoList(List<GetDouAllCollectionInfo> getDouAllCollectionInfoList) {
        this.getDouAllCollectionInfoList = getDouAllCollectionInfoList;
    }
}
