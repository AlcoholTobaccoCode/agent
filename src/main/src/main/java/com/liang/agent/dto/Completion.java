package com.liang.agent.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Completion {
    private String chatId;
    private boolean stream;
    private boolean detail;

    private Map<String, Object> variables; // 使用Map来存储不确定的键值对

    private List<Message> messages;

    @Data
    public static class Message {
        private String content;
        private String role = "user";
    }

}
