package com.liang.agent.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.agent.annotation.ApiResponse;
import com.liang.agent.dto.Completion;
import com.liang.agent.dto.CompletionRes;
import com.liang.agent.dto.InputMsgDTO;
import com.liang.agent.dto.addContentDTO;
import com.liang.agent.entity.CategoryHistory;
import com.liang.agent.mapper.CategoryHistoryMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
        // .addHeader("OpenAI-Beta", "assistants=v2");
    }


    @Value("${BASE_URL}")
    private String BASE_URL;

    public ApiResponse infoProcess(InputMsgDTO inputMsg) {
        log.info("请求已进入——————————————————————————");
        //逻辑一：直接大模型处理内容
        if ("12345热线数据".equals(inputMsg.getSource())) {

            String res = send(inputMsg.getContent());
            log.info("返回的res对象---------{}",res);

            CompletionRes completion = JSON.parseObject(res, CompletionRes.class);

            log.info("返回的data对象---------{}",completion.getChoices().get(0).getMessage().getContent());
            addContentDTO contentDTO =   JSON.parseObject(completion.getChoices().get(0).getMessage().getContent(), addContentDTO.class);
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
                .url(BASE_URL+"/v1/chat/completions")
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

    private boolean judgeRepeat(InputMsgDTO inputMsg) {
        QueryWrapper<CategoryHistory> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(inputMsg.getCategoryBigSym())) {
            queryWrapper.eq("category_big_sym", inputMsg.getCategoryBigSym());
        }
        if (StringUtils.isNotBlank(inputMsg.getCategorySmallSym())) {
            queryWrapper.eq("category_small_sym", inputMsg.getCategorySmallSym());
        }
        if (StringUtils.isNotBlank(inputMsg.getAddress())) {
            queryWrapper.eq("address", inputMsg.getAddress());
        }


        List<CategoryHistory> categoryHistoryList = baseMapper.selectList(queryWrapper);
        log.info("数据库查询结果---------{}", categoryHistoryList);
        if (categoryHistoryList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
