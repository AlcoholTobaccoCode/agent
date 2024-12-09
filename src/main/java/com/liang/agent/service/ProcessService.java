package com.liang.agent.service;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.agent.annotation.ApiResponse;
import com.liang.agent.dto.*;
import com.liang.agent.entity.CategoryHistory;
import com.liang.agent.entity.Event;
import com.liang.agent.entity.SmallCategory;
import com.liang.agent.mapper.CategoryHistoryMapper;
import com.liang.agent.repository.EventRep;
import com.liang.agent.repository.SmallCategoryRep;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @program: agent
 * @ClassName ProcessService
 * @description:
 * @author: liangliang
 * @create: 2024-08-06 16:00
 * @Version 1.0
 **/
@Service
@Slf4j
public class ProcessService extends ServiceImpl<CategoryHistoryMapper, CategoryHistory> {
    Request.Builder requestBuilder;

    @PostConstruct
    public void init() {
        requestBuilder = new Request.Builder()
                // .url(COMPLETION_ENDPOINT)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + "fastgpt-xiPhIYfLUitaURnx4o7v4YX10VjwfhckzUaLoKveJEb0aGEssD2gqBWlq6bFYQUw");
    }


    @Value("${BASE_URL}")
    private String BASE_URL;

    @Resource
    private EventRep eventRep;

    @Resource
    SmallCategoryRep smallCategoryRep;


    public ApiResponse infoProcess(EventInput inputMsg) {
        log.info("请求已进入——————————————————————————");
        //逻辑一：直接大模型处理内容
        if ("12345热线数据".equals(inputMsg.getSource())) {
            Input4LLM input4LLM = BeanUtil.copyProperties(inputMsg, Input4LLM.class);
            String res = null;
            try {
                res = send(input4LLM);
            } catch (Exception e) {
                return ApiResponse.fail(500,"LLM大语言模型服务错误！");
            }
            log.info("返回的res对象---------{}", res);
            CompletionRes completion = JSON.parseObject(res, CompletionRes.class);
            String content = completion.getChoices().get(0).getMessage().getContent();

            // 检查并移除前缀
            String[] prefixes = {"```json", "```python", "```markdown"};
            String[] suffixes = {"```"};

            for (String prefix : prefixes) {
                if (content.startsWith(prefix)) {
                    // 修剪开头的前缀
                    content = content.substring(prefix.length());

                    // 检查是否以对应的结尾标记结尾，并修剪它
                    for (String suffix : suffixes) {
                        if (content.endsWith(suffix)) {
                            content = content.substring(0, content.length() - suffix.length());
                            break; // 找到匹配的结尾标记后退出内层循环
                        }
                    }
                    // 既然已经处理了前缀和结尾标记，就可以退出外层循环了
                    break; // 找到匹配的前缀后退出外层循环
                }
            }

            addContentDTO contentDTO = JSON.parseObject(content, addContentDTO.class);
            contentDTO.setReportTime(inputMsg.getReport_time());
            inputMsg.setAddContentDTO(contentDTO);
            return ApiResponse.success(contentDTO);
        } else {
            //逻辑二：跟mysql中已有数据比较，比对是否重复
            boolean repeat = judgeRepeat(inputMsg);
            log.info("数据库判断返回结果------—{}", repeat);
            inputMsg.setRepeat(repeat);
            return ApiResponse.success(inputMsg);
        }

    }


    private String send(Input4LLM input4LLM) {

        log.info("service层传入的参数对象---------{}", JSON.toJSONString(input4LLM));
        Completion com = new Completion();
        List<Completion.Message> messages = new ArrayList<>();
        Completion.Message message = new Completion.Message();
        message.setContent(input4LLM.toString());
        messages.add(message);
        com.setMessages(messages);

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody bodyOk = RequestBody.create(mediaType, JSON.toJSONString(com));
        Request req = requestBuilder
                .url(BASE_URL + "/v1/chat/completions")
                .post(bodyOk)
                .build();


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)  // 启用连接失败自动重试
                .build();
       // OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(req);
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                log.info("请求成功");

                String body = response.body().string();
                return body;
            } else {
                ResponseBody errorBody = response.body();
                if (errorBody != null) {
                    log.info("请求失败---————————{}",errorBody.string());
                    throw new RuntimeException(errorBody.string());
                }
            }
        } catch (IOException ex) {
            log.error("Error during API call: {}", ex.getMessage(), ex);
        }
        return null;
    }

    private boolean judgeRepeat(EventInput inputMsg) {

        log.info("service层传入的参数对象---------{}", JSON.toJSONString(inputMsg));
        List<Event> byProperties = eventRep.findEventsByBidSidAndAddress(inputMsg.getCategory_big_name(), inputMsg.getCategory_small_name(),
                inputMsg.getAddress());
//        List<Event> byProperties = eventRep.findEventsByBidSidAndAddress2(inputMsg.getCategory_big_sym(), inputMsg.getCategory_small_sym(),
//                inputMsg.getAddress());

        log.info("返回的eventList--------{}", JSON.toJSONString(byProperties));
        if (byProperties.isEmpty()) {
            this.addEvent(inputMsg);
            return false;
        } else {
            boolean found = false;
            for (Event e : byProperties) {
                long daysBetween = ChronoUnit.DAYS.between(e.getCreated_at(), inputMsg.getCreated_at());
                if (daysBetween <= 200) {
                    found = true;
                    break;
                }
            }
            return found;
        }
    }


    public void addEvent(EventInput inputMsg){
        log.info("传入service层的EventInput对象------{}",inputMsg);

        Event event = BeanUtil.copyProperties(inputMsg, Event.class);
        Optional<SmallCategory> byId = smallCategoryRep.findByName(inputMsg.getCategory_small_name());
        if (byId.isPresent()){
            SmallCategory smallCategory = byId.get();
            log.info("数据库响应的smallCategory对象------{}",smallCategory);
            event.setCategorySmallSym(smallCategory.getBusinessId());
            event.setSmallCategory(smallCategory);
        }

        log.info("存入数据库的event对象------{}",event);
        Event saved = eventRep.save(event);
        // return ApiResponse.success(saved);
    }
}
