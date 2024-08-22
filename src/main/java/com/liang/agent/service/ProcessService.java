package com.liang.agent.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.agent.annotation.ApiResponse;
import com.liang.agent.dto.*;
import com.liang.agent.entity.CategoryHistory;
import com.liang.agent.entity.Event;
import com.liang.agent.mapper.CategoryHistoryMapper;
import com.liang.agent.repository.EventRep;
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


    public ApiResponse infoProcess(EvnetInput inputMsg) {
        log.info("请求已进入——————————————————————————");
        //逻辑一：直接大模型处理内容
        if ("12345热线数据".equals(inputMsg.getSource())) {
            String res = send(inputMsg.getContent());
            log.info("返回的res对象---------{}", res);
            CompletionRes completion = JSON.parseObject(res, CompletionRes.class);
            log.info("返回的data对象---------{}", completion.getChoices().get(0).getMessage().getContent());
            addContentDTO contentDTO = JSON.parseObject(completion.getChoices().get(0).getMessage().getContent(), addContentDTO.class);
            return ApiResponse.success(contentDTO);
        } else {
            //逻辑二：跟mysql中已有数据比较，比对是否重复
            boolean repeat = judgeRepeat(inputMsg);
            log.info("数据库判断返回结果------—{}", repeat);
            inputMsg.setRepeat(repeat);
            return ApiResponse.success(inputMsg);
        }


    }


    private String send(String s) {

        Completion com = new Completion();
        List<Completion.Message> messages = new ArrayList<>();
        Completion.Message message = new Completion.Message();
        message.setContent(s);
        messages.add(message);
        com.setMessages(messages);

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody bodyOk = RequestBody.create(mediaType, JSON.toJSONString(com));
        Request req = requestBuilder
                .url(BASE_URL + "/v1/chat/completions")
                .post(bodyOk)
                .build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(req);
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                log.info("请求成功");

                String body = response.body().string();
                return body;
            } else {
                log.info("请求失败");

                ResponseBody errorBody = response.body();
                if (errorBody != null) {
                    return errorBody.string();
                }
            }
        } catch (IOException ex) {
            log.error("Error during API call: {}", ex.getMessage(), ex);
        }
        return null;
    }

    private boolean judgeRepeat(EvnetInput inputMsg) {


        log.info("service层传入的参数对象---------{}",JSON.toJSONString(inputMsg));

        List<Event> byProperties = eventRep.findEventsByBidSidAndAddress(inputMsg.getCategory_big_name(), inputMsg.getCategory_small_name(),
                inputMsg.getAddress());

//        List<Event> byProperties = eventRep.findEventsByBidSidAndAddress2(inputMsg.getCategory_big_sym(), inputMsg.getCategory_small_sym(),
//                inputMsg.getAddress());

        log.info("返回的eventList--------{}", JSON.toJSONString(byProperties));
        if (byProperties.isEmpty()) {
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
}
