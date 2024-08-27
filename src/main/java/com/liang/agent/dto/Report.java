package com.liang.agent.dto;

/**
 * @program: agent
 * @ClassName Report
 * @description:
 * @author: liangliang
 * @create: 2024-08-27 09:49
 * @Version 1.0
 **/
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Map;

@Data
public class Report {

    private String address;
    private String categoryBigName;
    private String categorySmallName;
    private String responsibleUnit;
    private String responsibleDepartment;
    private boolean isRepeat;
    private String reportTime;

    // 用于存储未知的键值对
    private Map<String, Object> additionalProperties;

    // 使用静态方法来解析JSON字符串
    public static Report parse(String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        Report report = new Report();
        report.setAddress(jsonObject.getString("address"));
        report.setCategoryBigName(jsonObject.getString("categoryBigName"));
        report.setCategorySmallName(jsonObject.getString("categorySmallName"));
        report.setResponsibleUnit(jsonObject.getString("responsibleUnit"));
        report.setReportTime(jsonObject.getString("reportTime"));
        report.setRepeat(jsonObject.getBoolean("isRepeat"));

        // 处理responsibleDepartment字段
        report.setResponsibleDepartment(
                jsonObject.getString("responsibleDepartment") != null ?
                        jsonObject.getString("responsibleDepartment") :
                        jsonObject.getString("road maintenance")
        );

        // 将剩余的字段添加到additionalProperties中
        for (String key : jsonObject.keySet()) {
            if (!"address".equals(key)
                    && !"categoryBigName".equals(key)
                    && !"categorySmallName".equals(key)
                    && !"responsibleUnit".equals(key)
                    && !"responsibleDepartment".equals(key)
                    && !"isRepeat".equals(key)
                    && !"reportTime".equals(key)) {
                report.getAdditionalProperties().put(key, jsonObject.get(key));
            }
        }

        return report;
    }
}
