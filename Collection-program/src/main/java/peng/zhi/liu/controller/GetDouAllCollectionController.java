package peng.zhi.liu.controller;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import peng.zhi.liu.dto.GetDouUserInfoDTO;
import peng.zhi.liu.entity.GetDouAllCollectionInfo;
import peng.zhi.liu.result.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class GetDouAllCollectionController {
    @PostMapping(value = "/getdouallcollection", produces = "application/json")
        public Result fetchUserCollectsVideos(@org.springframework.web.bind.annotation.RequestBody GetDouUserInfoDTO getDouUserInfoDTO) throws IOException {

        ArrayList<GetDouAllCollectionInfo> getDouAllCollectionInfoArrayList = new ArrayList<>();

        // 创建日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
            log.debug("OkHttp: {}", message); // 使用log.debug打印详细日志
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /**
         * 提取抖音视频信息的完整方法，使用org.json库解析JSON
         */
            // 创建OkHttp客户端
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor) // 添加日志拦截器
                .build();
        MediaType mediaType = MediaType.parse("application/json");

            // 构建请求体
            RequestBody body = RequestBody.create(mediaType, "{\n    \"cookie\": \""+getDouUserInfoDTO.getCookie()+"\",\n    \"max_cursor\": 0,\n    \"counts\": 20\n}");

            // 构建请求
            Request request = new Request.Builder()
                    .url("https://api.tikhub.io/api/v1/douyin/web/fetch_user_collection_videos")
                    .method("POST", body)
                    .addHeader("Authorization", getDouUserInfoDTO.getAuthorization())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                    .build();

            log.info("请求头：{}",request);
            try {
                // 执行请求
                Response response = client.newCall(request).execute();

                // 处理响应
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();

                    // 解析JSON响应（使用org.json库）
                    JSONObject rootObject = new JSONObject(responseBody);

                    // 提取aweme_list数组
                    JSONObject dataObject = rootObject.optJSONObject("data");
                    JSONArray awemeList = dataObject != null ? dataObject.optJSONArray("aweme_list") : null;

                    // 遍历每个视频项
                    if (awemeList != null) {
                        for (int i = 0; i < awemeList.length(); i++) {
                            GetDouAllCollectionInfo getDouAllCollectionInfo = new GetDouAllCollectionInfo();
                            JSONObject aweme = awemeList.optJSONObject(i);
                            if (aweme == null) continue;
                            // 提取desc
                            getDouAllCollectionInfo.setTitle(aweme.optString("desc", ""));

                            // 提取cover.url_list
                            JSONObject videoObject = aweme.optJSONObject("video");
                            if (videoObject != null) {
                                JSONObject coverNode = videoObject.optJSONObject("cover");
                                if (coverNode != null) {
                                    List<String> coverUrls = new ArrayList<>();
                                    JSONArray urlListArray = coverNode.optJSONArray("url_list");
                                    if (urlListArray != null) {
                                        getDouAllCollectionInfo.setCover(urlListArray.optString(0, ""));
                                    }
                                }

                                // 提取play_addr_h264.url_list
                                JSONObject playAddrNode = videoObject.optJSONObject("play_addr_h264");
                                if (playAddrNode != null) {
                                    JSONArray urlListArray = playAddrNode.optJSONArray("url_list");
                                    if (urlListArray != null) {
                                        getDouAllCollectionInfo.setLink(urlListArray.optString(2, ""));
                                    }
                                }
                            }
                            getDouAllCollectionInfoArrayList.add(getDouAllCollectionInfo);
                        }
                    }
                } else {
                    System.out.println("请求失败，响应码: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Result.success(getDouAllCollectionInfoArrayList);
    }
}
