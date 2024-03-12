package com.zhifan.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @author Miracle
 * @date 2022/4/21 15:58
 */
public class ConstantUtils {

    /**
     * RESTFUL请求工具
     */
    public static final RestTemplate REST_TEMPLATE = new RestTemplate();

    /**
     * JSON处理工具
     */
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 随机数生成工具
     */
    public static final Random RANDOM_UTIL = new Random();

}
