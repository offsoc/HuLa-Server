package com.hula.core.user.controller;

import com.hula.core.user.domain.entity.User;
import com.hula.core.user.domain.vo.req.user.LoginReq;
import com.hula.core.user.domain.vo.req.user.RefreshTokenReq;
import com.hula.core.user.domain.vo.req.user.RegisterReq;
import com.hula.core.user.domain.vo.resp.user.LoginResultVO;
import com.hula.core.user.service.LoginService;
import com.hula.core.user.service.TokenService;
import com.hula.domain.vo.res.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * token
 * @author ZOL
 */
@RestController
@RequestMapping("/token")
@Tag(name = "用户管理")
public class TokenController {

    @Resource
    private LoginService loginService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/pk")
    @Operation(summary ="获取公钥")
    public ApiResult<String> pk() {
        return ApiResult.success("pk");
    }

    @PostMapping("/login")
    @Operation(summary ="用户登录")
    public ApiResult<LoginResultVO> login(@Valid @RequestBody LoginReq loginReq) {
        return ApiResult.success(loginService.login(User.builder()
				.account(loginReq.getAccount()).password(loginReq.getPassword()).build()));
    }

    @PostMapping("/mobileLogin")
    @Operation(summary ="移动端用户登录")
    public ApiResult<LoginResultVO> mobileLogin(@Valid @RequestBody LoginReq loginReq) {
        return ApiResult.success(loginService.mobileLogin(User.builder()
				.account(loginReq.getAccount()).password(loginReq.getPassword()).build()));
    }

    @PostMapping("/logout")
    @Operation(summary ="用户登出")
    public ApiResult<Boolean> logout() {
        loginService.logout();
        return ApiResult.success(Boolean.TRUE);
    }

    @PostMapping("/register")
    @Operation(summary ="用户注册")
    public ApiResult<String> register(@Valid @RequestBody RegisterReq registerReq) {
        loginService.normalRegister(User.builder()
                .avatar(registerReq.getAvatar())
                .account(registerReq.getAccount())
                .password(registerReq.getPassword())
                .name(registerReq.getName()).build());
        return ApiResult.success(registerReq.getAccount());
    }

    @PostMapping("/refreshToken")
    @Operation(summary ="token续签")
    public ApiResult<LoginResultVO> refreshToken(@RequestBody RefreshTokenReq refreshTokenReq) {
        return ApiResult.success(tokenService.refreshToken(refreshTokenReq));
    }

    @PostMapping("/offline")
    @Operation(summary ="下线")
    public ApiResult<Boolean> offline() {
        tokenService.offline(User.builder().build());
        return ApiResult.success(Boolean.TRUE);
    }
}

