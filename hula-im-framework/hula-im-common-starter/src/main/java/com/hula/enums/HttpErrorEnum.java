package com.hula.enums;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.hula.domain.vo.res.ApiResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Description: 业务校验异常码6
 * @author nyh
 */
@AllArgsConstructor
@Getter
public enum HttpErrorEnum implements ErrorEnum {
    ACCESS_DENIED(401, "登录失效，请重新登录"),
	BLACK_ERROR(360, "对不起，不在白名单内"),
	JWT_TOKEN_EXCEED(40004, "正在为您的token续签..."),
	JWT_REFRESH_TOKEN_EXCEED(40005, "您的身份已过期, 请重新登录!"),
    ;
    private Integer code;
    private String msg;

    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }

    public void sendHttpError(HttpServletResponse response) throws IOException {
        response.setStatus(this.getErrorCode());
        ApiResult<Object> responseData = ApiResult.fail(this);
        response.setContentType(ContentType.JSON.toString(Charset.forName("UTF-8")));
        response.getWriter().write(JSONUtil.toJsonStr(responseData));
    }
}
