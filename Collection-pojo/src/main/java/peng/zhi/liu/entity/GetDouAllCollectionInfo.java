package peng.zhi.liu.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import peng.zhi.liu.vo.GetDouUserInfoVO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDouAllCollectionInfo {
    private String title;
    private String cover;
    private String link;

    public GetDouAllCollectionInfo(String title, String cover, String link) {
        this.title = title;
        this.cover = cover;
        this.link = link;
    }

    public GetDouAllCollectionInfo(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
