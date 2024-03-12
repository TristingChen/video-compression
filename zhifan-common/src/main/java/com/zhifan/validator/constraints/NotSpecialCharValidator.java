package com.zhifan.validator.constraints;

import com.zhifan.validator.constraints.annotation.NotSpecialChar;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 过滤特殊字符
 *
 * @author Miracle
 * @date 2022/6/6 9:35
 */
public class NotSpecialCharValidator implements ConstraintValidator<NotSpecialChar, String> {

    private final static List<String> SPECIAL_CHARS = Arrays.asList("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+", "_", "=", "{", "}", "[", "]", ":", ";", "'", "<", ">", ",", ".", "?", "/", "~", "`", "\"", "|", " ");

    private List<String> validStrings;

    @Override
    public void initialize(NotSpecialChar constraintAnnotation) {
        List<String> exceptStrings = new ArrayList<>(Arrays.asList(constraintAnnotation.except()));
        if (exceptStrings.size() > 0) {
            validStrings = SPECIAL_CHARS.stream().filter(specialChar -> {
                // 循环排除项
                for (String exceptStr : exceptStrings) {
                    if (exceptStr.equals(specialChar)) {
                        exceptStrings.remove(exceptStr);
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());
        } else {
            validStrings = SPECIAL_CHARS;
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringUtils.isEmpty(value)) {
            return true;
        }

        for (String charStr : validStrings) {
            // 判断是否有排除项
            if (value.contains(charStr)) {
                return false;
            }
        }
        return true;
    }
}
