package com.liang.agent.annotation;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private int code;

    private T data;

    private String msg;

    public ApiResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ApiResponse<T> success (T data) {
        return new ApiResponse<>(200, data, "success");
    }

    public static <T> ApiResponse<T> fail (int code, String msg) {
        return new ApiResponse<>(code, null, msg);
    }
}

