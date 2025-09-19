package peng.zhi.liu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.security.KeyStore;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDouUserInfoDTO {
    private Integer max_cursor = 0;
    private Integer counts = 20;
    private String cookie;
    private String authorization;

    public GetDouUserInfoDTO(Integer max_cursor, Integer counts, String cookie, String authorization) {
        this.max_cursor = max_cursor;
        this.counts = counts;
        this.cookie = cookie;
        this.authorization = authorization;
    }

    public GetDouUserInfoDTO(){}

    public Integer getMax_cursor() {
        return max_cursor;
    }

    public void setMax_cursor(Integer max_cursor) {
        this.max_cursor = max_cursor;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
