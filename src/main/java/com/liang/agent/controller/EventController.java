package com.liang.agent.controller;

import com.alibaba.fastjson.JSON;
import com.liang.agent.annotation.ApiResponse;
import com.liang.agent.dto.EventInput;
import com.liang.agent.service.ProcessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: agent
 * @ClassName EventController
 * @description:
 * @author: liangliang
 * @create: 2024-09-09 13:47
 * @Version 1.0
 **/
@RestController
@RequestMapping("/event")
@Slf4j
public class EventController {

    @Resource
    ProcessService processService;

    @PostMapping(value = "/addevent")
    public ApiResponse add(@RequestBody EventInput inputMsg) {
        log.info("传入的对象-------{}", JSON.toJSONString(inputMsg));
        return processService.addEvent(inputMsg) ;

    }

}
