package peng.zhi.liu.controller;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import peng.zhi.liu.entity.GetDouUserInfo;
import peng.zhi.liu.result.Result;
import peng.zhi.liu.vo.GetDouUserInfoVO;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class GetDouUserInfoController {
    // 1. 替换为【最新获取的抖音Cookie】（关键！）
    private static final String COOKIE = "UIFID_TEMP=ad7b1e526ce029fae15c4fe388b5a88a6cfc5b31e687fc2dca11932336017cb54debb6d917109c0e0467fc3fab56b7d7b976ccba1fe6460ca9f5468c0b8fbe5e2ea50add2041a845879029813a696d8c; fpk1=U2FsdGVkX19jf8kZLytHhb74ltd5B+MJUZz0seodguXfGgIZ14T/i2EgfdJXejfjXS/mIbv0s9t/jDtyO7URpw==; fpk2=9fae7894890fe21cd77090af114aa2cd; UIFID=ad7b1e526ce029fae15c4fe388b5a88a6cfc5b31e687fc2dca11932336017cb54debb6d917109c0e0467fc3fab56b7d701018a607a320d7054607a825fad9b7e707007fa2c59a5c85974bc73e172074208b2ecf5b982b3a6a6382e96f4270600159e1058c7f3b505fe9e6bfd0a1796ceea962e20de8a39a27f2c64df63a52eef4a5e62f7a629763b59466632597bc430fbbd94160b779bde8267104a0907f6e5; enter_pc_once=1; s_v_web_id=verify_mem97l9m_0KkMDuH7_5Pk4_4HHJ_844h_7n1XdD8lzJ5F; passport_csrf_token=ac03073905b328f91f334845b316dffc; passport_csrf_token_default=ac03073905b328f91f334845b316dffc; __security_mc_1_s_sdk_crypt_sdk=c5185a1c-4fea-915e; __security_mc_1_s_sdk_cert_key=30d2e708-4005-b96c; bd_ticket_guard_client_web_domain=2; xgplayer_user_id=520506989154; is_staff_user=false; __security_server_data_status=1; SelfTabRedDotControl=%5B%5D; my_rd=2; SEARCH_RESULT_LIST_TYPE=%22single%22; dy_swidth=1506; dy_sheight=848; strategyABtestKey=%221758079446.276%22; volume_info=%7B%22isUserMute%22%3Afalse%2C%22isMute%22%3Atrue%2C%22volume%22%3A0.31%7D; FOLLOW_LIVE_POINT_INFO=%22MS4wLjABAAAAuGhjn-9id_qNHB6E7EaXyfx3hpm6e0WgXPdi6xSIv6IkDl2coOWez5nNEOHtEENy%2F1758124800000%2F0%2F1758079446687%2F0%22; __ac_signature=_02B4Z6wo00f01CAZ6WQAAIDDrbFQ7DhTfqggOe3AAGDSYh2VwAlrh5N6HDH3HlKdqFWB.al-Ic8pgpVnv.3DrthpPeRY-XK0-yHC7pSrYxep1TgH6.-W7VighMnXpOT1N0SReFU5GAJYZ1ofbf; passport_mfa_token=CjhWF1xch0Eo5P1OMLCc%2FyGEZI7rDhxdp8jGLineoI54iYsCkkniugDsYW9RPhOoaLoflN%2BJLJLMOBpKCjwAAAAAAAAAAAAAT3yYu0RwNbX%2FxYvJ0h5xy0yxTvnEerIqU7X2WtJABoQMTuOOzJTIfvOPdUnaL75N%2FKUQgrT8DRj2sdFsIAIiAQPdc8kZ; d_ticket=0f4de4b8250f8c2cfe6cbd4ae2302bd0d8872; passport_assist_user=ClNAk2txDqn7S1c005tF7Be7EsdUdQ9Ulh1XSv0gaBSmbVD6AeInQbbL43komz3vZS5jaTVag6QvsEVZumCvLv2lvINz5BCTfqPcvG7S9ZFfDa3pNBpKCjwAAAAAAAAAAAAAT3wauIRQrGzLJZc4_ED8kNQeXDNbS55OT8XP_aI0KGzMarZOzdsLOdUsQ_EiXIHEeA0QzbT8DRiJr9ZUIAEiAQNDyxK5; n_mh=9-mIeuD4wZnlYrrOvfzG3MuT6aQmCUtmr8FxV8Kl8xY; sid_guard=6c28417d2d92cef92745a219fd0ea461%7C1758079538%7C5183999%7CSun%2C+16-Nov-2025+03%3A25%3A37+GMT; uid_tt=a7f5f2c0fa0f5c12d4d729c927a336e37015d6e3dc8aea574e970e02947a26a4; uid_tt_ss=a7f5f2c0fa0f5c12d4d729c927a336e37015d6e3dc8aea574e970e02947a26a4; sid_tt=6c28417d2d92cef92745a219fd0ea461; sessionid=6c28417d2d92cef92745a219fd0ea461; sessionid_ss=6c28417d2d92cef92745a219fd0ea461; sid_ucp_v1=1.0.0-KDFhZmNhNWM0OWI5OGU3NGZiYTMwMDc4OWZmZjNiOWFlN2JhOGFkZDYKIgi7iKHk2ZKWhmgQstSoxgYY7zEgDDDetrHABjgHQPQHSAQaAmhsIiA2YzI4NDE3ZDJkOTJjZWY5Mjc0NWEyMTlmZDBlYTQ2MQ; ssid_ucp_v1=1.0.0-KDFhZmNhNWM0OWI5OGU3NGZiYTMwMDc4OWZmZjNiOWFlN2JhOGFkZDYKIgi7iKHk2ZKWhmgQstSoxgYY7zEgDDDetrHABjgHQPQHSAQaAmhsIiA2YzI4NDE3ZDJkOTJjZWY5Mjc0NWEyMTlmZDBlYTQ2MQ; login_time=1758079537082; _bd_ticket_crypt_cookie=71fa2ff51ee763b7427f0eb0298c1ffa; __security_mc_1_s_sdk_sign_data_key_web_protect=44c800ba-4a46-a993; publish_badge_show_info=%221%2C0%2C0%2C1758079543521%22; download_guide=%223%2F20250917%2F0%22; douyin.com; device_web_cpu_core=20; device_web_memory_size=8; architecture=amd64; xg_device_score=7.9191272394219645; stream_recommend_feed_params=%22%7B%5C%22cookie_enabled%5C%22%3Atrue%2C%5C%22screen_width%5C%22%3A1506%2C%5C%22screen_height%5C%22%3A848%2C%5C%22browser_online%5C%22%3Atrue%2C%5C%22cpu_core_num%5C%22%3A20%2C%5C%22device_memory%5C%22%3A8%2C%5C%22downlink%5C%22%3A9.35%2C%5C%22effective_type%5C%22%3A%5C%224g%5C%22%2C%5C%22round_trip_time%5C%22%3A50%7D%22; bd_ticket_guard_client_data=eyJiZC10aWNrZXQtZ3VhcmQtdmVyc2lvbiI6MiwiYmQtdGlja2V0LWd1YXJkLWl0ZXJhdGlvbi12ZXJzaW9uIjoxLCJiZC10aWNrZXQtZ3VhcmQtcmVlLXB1YmxpYy1rZXkiOiJCRCtyMlFxZkErb3dURHBYTjlVWVpBVU03V2lXa2pUQTdKMTNQdTlSWGFXTmlyMW5NeG5BQndHQnYySThETXRGbTQ4Z2YyTnBIRXYvWEpJTnkwOUpWZXM9IiwiYmQtdGlja2V0LWd1YXJkLXdlYi12ZXJzaW9uIjoyfQ%3D%3D; home_can_add_dy_2_desktop=%221%22; ttwid=1%7C4ukVNTbOQfB2dU1mrkXIGSxPNhIDt-o4aCVMBvksLWI%7C1758098882%7C17b58fb24c1faff8c4aefccb031a8bca09ac9be617ef0795d676a65102406656; biz_trace_id=fd706b9b; session_tlb_tag=sttt%7C15%7CbChBfS2SzvknRaIZ_Q6kYf_________gDDgYGoS1mutWR1KjCCw6456dpwwD8bbd0F14FEg86WQ%3D; sdk_source_info=7e276470716a68645a606960273f276364697660272927676c715a6d6069756077273f276364697660272927666d776a68605a607d71606b766c6a6b5a7666776c7571273f275e58272927666a6b766a69605a696c6061273f27636469766027292762696a6764695a7364776c6467696076273f275e582729277672715a646971273f2763646976602729277f6b5a666475273f2763646976602729276d6a6e5a6b6a716c273f2763646976602729276c6b6f5a7f6367273f27636469766027292771273f273d3637373d3d3d3c353d303234272927676c715a75776a716a666a69273f2763646976602778; gulu_source_res=eyJwX2luIjoiMzczYjUwZjEwMjE1MTQ5YzM3YTMxYjVjNjA1ZDk0Y2JmYTI2YzkwZWE5MGIxMTNiN2JhMmU1ZTVjNjAyOTJhZiJ9; passport_auth_mix_state=fxtnch6u17svkp0un1m8jkb1odhpgzgghgo8sfhwtw673mr0; odin_tt=09e22e67c8fe382217927ff23e1aba344c7858adb447a4d4b0d3f441d4bd012a776594bf664968fce0e513e281a7660960025c7abc3474f7810aebb4aecc7995; stream_player_status_params=%22%7B%5C%22is_auto_play%5C%22%3A0%2C%5C%22is_full_screen%5C%22%3A0%2C%5C%22is_full_webscreen%5C%22%3A0%2C%5C%22is_mute%5C%22%3A1%2C%5C%22is_speed%5C%22%3A1%2C%5C%22is_visible%5C%22%3A1%7D%22; WallpaperGuide=%7B%22showTime%22%3A1758079627363%2C%22closeTime%22%3A0%2C%22showCount%22%3A1%2C%22cursor1%22%3A23%2C%22cursor2%22%3A6%2C%22hoverTime%22%3A1758099017905%7D; IsDouyinActive=false"; // 你的完整Cookie
    private static final String URL = "https://api.tikhub.dev/api/v1/douyin/web/fetch_user_collects";
    private static final String AUTHORIZATION = "bearer k41nQDpL/HcAnRcR+E6JuyE25F0pt574raxK3hbMkmVZ6HcEULtO4hGeKg==";

    @PostMapping("/getDouUserInfo")
    public Result getDouUserInfoController() {
        GetDouUserInfoVO getDouUserInfoVO = new GetDouUserInfoVO();
        // 创建OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        // 构建JSON请求体（包含max_cursor、counts、cookie）
        JSONObject requestBodyJson = new JSONObject();
        try {
            requestBodyJson.put("max_cursor", 0);    // 改为整数类型（API可能更兼容）
            requestBodyJson.put("counts", 20);       // 改为整数类型
            requestBodyJson.put("cookie", COOKIE);   // 传递最新Cookie
        } catch (JSONException e) {
            e.printStackTrace();
            return Result.error("请求体错误");
        }
        RequestBody requestBody = RequestBody.create(
                requestBodyJson.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        // 构建请求（无Cookie Header，Cookie在请求体中）
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("Authorization", AUTHORIZATION)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .build();

        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            System.out.println("HTTP状态码: " + response.code());
            System.out.println("HTTP响应信息: " + response.message());

            String responseBody = response.body().string();

            if (!response.isSuccessful()) {
                System.err.println("HTTP请求失败，状态码: " + response.code());
                return Result.error("请求错误");
            }

            // 解析API响应（增加空值判断）
            JSONObject jsonResponse = new JSONObject(responseBody);
            if (jsonResponse.getInt("code") == 200) { // API业务码200表示接口调用成功
                JSONObject data = jsonResponse.getJSONObject("data");

                // 关键：判断collects_list是否为null（避免解析异常）
                if (data.isNull("collects_list")) {
                    System.out.println("\n=== 无收藏数据 ===");
                    // 解析抖音业务错误（如未登录）
                    if (data.has("status_code") && data.has("status_msg")) {
                        int dyCode = data.getInt("status_code");
                        String dyMsg = data.getString("status_msg");
                        System.out.println("抖音业务提示：状态码=" + dyCode + "，信息=" + dyMsg);
                        if (dyCode == 8) {
                            System.err.println("解决方案：请重新从抖音网页版获取最新Cookie！");
                        }
                    }
                    return Result.error("cookie错误，请使用最新cookie");
                }

                // 解析收藏列表（正常流程）
                JSONArray collectsList = data.getJSONArray("collects_list");
                System.out.println("\n=== 共获取到 " + collectsList.length() + " 个收藏 ===");
                for (int i = 0; i < collectsList.length(); i++) {
                    GetDouUserInfo getDouUserInfo = new GetDouUserInfo();
                    JSONObject collect = collectsList.getJSONObject(i);
                    String id = collect.optString("collects_id_str", "未知ID"); // 用optString避免字段缺失报错
                    String name = collect.optString("collects_name", "未知名称");
                    JSONObject cover = collect.optJSONObject("collects_cover");
                    getDouUserInfo.setCollectionId(id);
                    getDouUserInfo.setCollectionTitle(name);


                    System.out.println("收藏ID: " + id);
                    System.out.println("收藏名称: " + name);
                    if (cover != null && !cover.isNull("url_list")) {
                        JSONArray urlList = cover.getJSONArray("url_list");
                        System.out.println("封面URL:");
                        Object object = urlList.get(0);
                        getDouUserInfo.setCollectionCoverLink((String) object);
                    } else {
                        System.out.println("封面URL: 无");
                    }
                    getDouUserInfoVO.getGetDouUserInfoList().add(getDouUserInfo);
                    System.out.println("----------------------------");
                }

            } else {
                // API业务错误（如Authorization无效）
                System.err.println("\nAPI业务错误：");
                System.err.println("业务码: " + jsonResponse.getInt("code"));
                System.err.println("错误信息: " + jsonResponse.optString("message", "无"));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return Result.success(getDouUserInfoVO);
    }
}
