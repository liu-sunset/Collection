package peng.zhi.liu.controller;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import peng.zhi.liu.dto.GetBiliUserInfoDTO;
import peng.zhi.liu.entity.GetBiliUserInfo;
import peng.zhi.liu.result.Result;
import peng.zhi.liu.vo.GetBiliUserInfoVO;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@RestController
public class GetBilibiliUserInfoController {

    private static final OkHttpClient client = new OkHttpClient();

    @PostMapping(value = "/getBiliUserInfo",produces = "application/json")
    @ResponseBody
    public Result getBiliUserInfo(@RequestBody GetBiliUserInfoDTO getBiliUserInfoDTO) {
        if (getBiliUserInfoDTO.getCookie() == null || getBiliUserInfoDTO.getCookie().isEmpty()) {
            return Result.error("Cookie不能为空");
        }

        if (getBiliUserInfoDTO.getUpMid() == null || getBiliUserInfoDTO.getUpMid().isEmpty()) {
            return Result.error("upMid不能为空");
        }

        String url = "https://api.bilibili.com/x/v3/fav/folder/created/list-all";
        String upMid = getBiliUserInfoDTO.getUpMid();

        GetBiliUserInfoVO getBiliUserInfoVO = new GetBiliUserInfoVO();
        // 构建带参数的URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("up_mid", upMid);
        String finalUrl = urlBuilder.build().toString();

        // 创建请求对象
        Request request = new Request.Builder()
                .url(finalUrl)
                .get()
                .addHeader("Cookie", getBiliUserInfoDTO.getCookie())
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .build();

        // 发送同步请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return Result.error("请求失败");
            }

            // 获取响应体
            String responseBody = response.body().string();

            // 解析JSON响应
            JSONObject jsonResponse = new JSONObject(responseBody);

            // 检查API响应是否成功
            if (jsonResponse.getInt("code") != 0) {
                return Result.error("未知错误");
            }

            // 提取data部分
            JSONObject data = jsonResponse.getJSONObject("data");

            // 提取count
            int count = data.getInt("count");

            getBiliUserInfoVO.setTotal(count);
            // 提取list数组
            JSONArray list = data.getJSONArray("list");

            ArrayList<GetBiliUserInfo> getBiliUserInfoArrayList = new ArrayList<>();
            // 遍历每个收藏夹
            for (int i = 0; i < list.length(); i++) {
                JSONObject folder = list.getJSONObject(i);
                long id = folder.getLong("id");
                String title = folder.getString("title");
                GetBiliUserInfo getBiliUserInfo = new GetBiliUserInfo();
                getBiliUserInfo.setCollectionId(String.valueOf(id));
                getBiliUserInfo.setCollectionTitle(title);
                getBiliUserInfoArrayList.add(getBiliUserInfo);
            }
            getBiliUserInfoVO.setCollectionInfo(getBiliUserInfoArrayList);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("请求失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("json解析错误");
        }
        return Result.success(getBiliUserInfoVO);
    }
}