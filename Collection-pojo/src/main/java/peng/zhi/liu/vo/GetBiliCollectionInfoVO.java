package peng.zhi.liu.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import peng.zhi.liu.entity.VideoInfo;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetBiliCollectionInfoVO {
    private  String collectionTitle;
    private  String collectionCover;
    private List<VideoInfo> videoInfoList = new ArrayList<>();

    public String getCollectionTitle() {
        return collectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    public String getCollectionCover() {
        return collectionCover;
    }

    public void setCollectionCover(String collectionCover) {
        this.collectionCover = collectionCover;
    }

    public List<VideoInfo> getVideoInfoList() {
        return videoInfoList;
    }

    public void setVideoInfoList(List<VideoInfo> videoInfoList) {
        this.videoInfoList = videoInfoList;
    }
}
