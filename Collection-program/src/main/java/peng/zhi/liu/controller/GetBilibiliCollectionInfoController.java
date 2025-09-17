package peng.zhi.liu.controller;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import peng.zhi.liu.dto.GetBiliCollectionInfoDTO;
import peng.zhi.liu.entity.VideoInfo;
import peng.zhi.liu.result.Result;
import peng.zhi.liu.vo.GetBiliCollectionInfoVO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class GetBilibiliCollectionInfoController {

    @PostMapping(value = "/getBiliCollectionInfo", produces = "application/json")
    public Result getBiliCollectionInfo(@RequestBody GetBiliCollectionInfoDTO getBiliCollectionInfoDTO) {
        // 创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();


        ArrayList<GetBiliCollectionInfoVO> collectionInfoVOArrayList = new ArrayList<>();
        // 构建请求URL和参数
        List<String> mediaIdList = getBiliCollectionInfoDTO.getCollectionIds();
        for (String media_id : mediaIdList){
            GetBiliCollectionInfoVO getBiliCollectionInfoVO = new GetBiliCollectionInfoVO();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.bilibili.com/x/v3/fav/resource/list").newBuilder();
            urlBuilder.addQueryParameter("media_id", media_id);
            urlBuilder.addQueryParameter("ps", "10");
            String url = urlBuilder.build().toString();
            // 创建请求
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            // 执行请求（同步方式）
            try (Response response = client.newCall(request).execute()) {
                // 检查响应是否成功
                if (!response.isSuccessful()) {
                    return Result.error("请求失败");
                }

                // 获取响应体内容
                String responseData = response.body().string();

                // 解析JSON响应
                JSONObject jsonResponse = new JSONObject(responseData);

                // 检查API返回状态码
                if (jsonResponse.getInt("code") == 0) {
                    JSONObject data = jsonResponse.getJSONObject("data");
                    JSONObject info = data.getJSONObject("info");


                    // 提取info部分的数据
                    String title = info.getString("title");
                    String cover = info.getString("cover");

                    getBiliCollectionInfoVO.setCollectionTitle(title);
                    getBiliCollectionInfoVO.setCollectionCover(cover);

                    // 提取medias数组中的数据
                    JSONArray medias = data.getJSONArray("medias");
                    for (int i = 0; i < medias.length(); i++) {
                        JSONObject media = medias.getJSONObject(i);
                        String mediaTitle = media.getString("title");
                        String mediaCover = media.getString("cover");
                        String mediaLink = "https://www.bilibili.com/video/"+media.getString("bvid");

                        VideoInfo videoInfo = new VideoInfo();
                        videoInfo.setTitle(mediaTitle);
                        videoInfo.setCover(mediaCover);
                        videoInfo.setLink(mediaLink);
                        getBiliCollectionInfoVO.getVideoInfoList().add(videoInfo);
                    }
                    collectionInfoVOArrayList.add(getBiliCollectionInfoVO);
                } else {
                    return Result.error("未知错误");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("请求或者解析过程发生错误");
            }
        }
        return Result.success(collectionInfoVOArrayList);
    }
}