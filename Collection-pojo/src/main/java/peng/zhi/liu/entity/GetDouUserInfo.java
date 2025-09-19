package peng.zhi.liu.entity;

public class GetDouUserInfo {
    private String collectionCoverLink;
    private String collectionTitle;
    private String collectionId;

    public GetDouUserInfo(String collectionCoverLink, String collectionTitle, String collectionId) {
        this.collectionCoverLink = collectionCoverLink;
        this.collectionTitle = collectionTitle;
        this.collectionId = collectionId;
    }

    public GetDouUserInfo(){}

    public String getCollectionCoverLink() {
        return collectionCoverLink;
    }

    public void setCollectionCoverLink(String collectionCoverLink) {
        this.collectionCoverLink = collectionCoverLink;
    }

    public String getCollectionTitle() {
        return collectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
