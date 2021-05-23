package com.ruoyi.process.utils;

import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ParameterUtils {

    @NotNull
    public static Map<String, Object> transToVariables(HttpServletRequest request, Enumeration<String> parameterNames) throws ParseException {
        Map<String, Object> variables = new HashMap<String, Object>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            if (parameterName.startsWith("p_")) {
                // 参数结构：p_B_name，p为参数的前缀，B为类型，name为属性名称
                String[] parameter = parameterName.split("_");
                if (parameter.length == 3) {
                    String paramValue = request.getParameter(parameterName);
                    Object value = paramValue;
                    if (parameter[1].equals("B")) {
                        value = BooleanUtils.toBoolean(paramValue);
                    } else if (parameter[1].equals("DT")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        value = sdf.parse(paramValue);
                    } else {
                        value = String.valueOf(paramValue);
                    }
                    variables.put(parameter[2], value);
                } else {
                    throw new RuntimeException("invalid parameter for activiti variable: " + parameterName);
                }
            }
        }
        return variables;
    }
}
