package peng.zhi.liu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peng.zhi.liu.entity.GetBiliUserInfo;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBiliUserInfoVO {
    private Integer total;
    private List<GetBiliUserInfo> collectionInfo;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<GetBiliUserInfo> getCollectionInfo() {
        return collectionInfo;
    }

    public void setCollectionInfo(List<GetBiliUserInfo> collectionInfo) {
        this.collectionInfo = collectionInfo;
    }
}
