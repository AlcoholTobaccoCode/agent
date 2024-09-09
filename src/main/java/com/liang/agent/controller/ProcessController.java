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
 * @ClassName ProcessController
 * @description:
 * @author: liangliang
 * @create: 2024-08-06 16:02
 * @Version 1.0
 **/

@RestController
@RequestMapping("/process")
@Slf4j
public class ProcessController {
    @Resource
    ProcessService processService;

    @PostMapping(value = "/process")
    public ApiResponse add(@RequestBody EventInput inputMsg) {
        log.info("传入的对象-------{}", JSON.toJSONString(inputMsg));
        return processService.infoProcess(inputMsg) ;

    }

}
