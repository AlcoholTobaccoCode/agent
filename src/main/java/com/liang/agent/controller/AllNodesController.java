package com.liang.agent.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.liang.agent.annotation.ApiResponse;
import com.liang.agent.dto.BigCategoryDTO;
import com.liang.agent.dto.GraphNode;
import com.liang.agent.repository.BigCategoryRep;
import com.liang.agent.repository.SmallCategoryRep;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: agent
 * @ClassName AllNodesController
 * @description:
 * @author: liangliang
 * @create: 2024-08-14 14:33
 * @Version 1.0
 **/

@RestController
@RequestMapping("/allnodes")
@Slf4j
public class AllNodesController {

    @Resource
    BigCategoryRep bigCategoryRep;

    @Resource
    SmallCategoryRep smallCategoryRep;

    @PostMapping(value = "/findall")
    public ApiResponse<Object> findall(@RequestBody BigCategoryDTO bigCategoryDTO) {
        PageRequest pageRequest = PageRequest.of(bigCategoryDTO.getPageNum(), bigCategoryDTO.getPageSize());
        List<GraphNode> allNodes = new ArrayList<>();
        Page<? extends GraphNode> pagedResult;

        if (StringUtils.isEmpty(bigCategoryDTO.getType())) {
            pagedResult = findNodesByName(bigCategoryDTO.getName(), pageRequest, "bigCategory");
            allNodes.addAll(pagedResult.getContent());
            pagedResult = findNodesByName(bigCategoryDTO.getName(), pageRequest, "smallCategory");
            allNodes.addAll(pagedResult.getContent());
        } else {
            pagedResult = findNodesByName(bigCategoryDTO.getName(), pageRequest, bigCategoryDTO.getType());
        }

        if (pagedResult.getContent().isEmpty()) {
            return ApiResponse.success(new PageImpl<>(Collections.emptyList(), pageRequest, 0));
        }

        allNodes.addAll(pagedResult.getContent());
        return ApiResponse.success(new PageImpl<>(allNodes, pageRequest, pagedResult.getTotalElements()));
    }

    private Page<? extends GraphNode> findNodesByName(String name, PageRequest pageRequest, String type) {
        if ("bigCategory".equals(type)) {
            return bigCategoryRep.findByNameContaining(name, pageRequest);
        } else if ("smallCategory".equals(type)) {
            return smallCategoryRep.findByNameContaining(name, pageRequest);
        }
        return new PageImpl<>(Collections.emptyList(), pageRequest, 0);
    }
}
