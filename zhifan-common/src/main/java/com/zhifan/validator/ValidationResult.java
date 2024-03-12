package com.zhifan.validator;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miracle
 * @date 2019/7/10 21:47
 */

@Data
public class ValidationResult {

    /**
     * 校验结果是否有错
     */
    private boolean hasErrors = false;

    /**
     * 存放错误信息的map
     */
    private Map<String, String> errorMsgMap = new HashMap<>();

    public String getErrMsg() {
        return StringUtils.arrayToDelimitedString(errorMsgMap.values().toArray(), ",");
    }

}
