package peng.zhi.liu.dto;

import lombok.Data;

import java.util.List;

public class GetBiliCollectionInfoDTO {
    private List<String> collectionIds;

    public List<String> getCollectionIds() {
        return collectionIds;
    }

    public void setCollectionIds(List<String> collectionId) {
        this.collectionIds = collectionId;
    }
}
