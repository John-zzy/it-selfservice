package com.cnec5.it.selfservice.server.controller;

import com.cnec5.it.selfservice.common.dto.ResponseResult;
import com.cnec5.it.selfservice.common.utils.MapperUtil;
import com.cnec5.it.selfservice.common.utils.OKHttpClientUtils;
import com.cnec5.it.selfservice.feign.ProfileFeign;
import com.cnec5.it.selfservice.feign.UserListsFeign;
import com.cnec5.it.selfservice.provider.api.AdminUserService;
import com.cnec5.it.selfservice.provider.domain.AdminUser;
import com.cnec5.it.selfservice.server.dto.LoginInfo;
import com.cnec5.it.selfservice.server.dto.LoginParam;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.Response;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 用户登录Controller
 */
@Api(tags = "LoginController", description = "用户登录API")
@RestController
@RequestMapping(value = "user")
public class LoginController {

    private static final String URL_OAUTH_TOKEN = "http://localhost:9001/oauth/token";

    @Value("${business.oauth2.grant_type}")
    public String GRANT_TYPE;

    @Value("${business.oauth2.client_id}")
    public String CLIENT_ID;

    @Value("${business.oauth2.client_secret}")
    public String CLIENT_SECRET;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource(name = "userDetailsServiceBean")
    private UserDetailsService userDetailsService;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private ProfileFeign profileFeign;

    @Resource
    private UserListsFeign userListsFeign;

    @Reference(version = "1.0.0")
    private AdminUserService adminUserService;

    /**
     * 用户登录
     *
     * @param loginParam {@link LoginParam} 登录参数
     * @return
     */
    @PostMapping(value = "login")
    @ApiOperation(value = "用户登录")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam) {
        Map<String, Object> resultMap = Maps.newHashMap();
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());

        // 获取用户状态是否可以登录
        AdminUser adminUser = adminUserService.get(loginParam.getUsername());
        if (adminUser == null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户不存在");
        }

        if (0 == adminUser.getStatus()) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户已停用");
        }

        // 用户名密码校验失败
        if (userDetails == null || !passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户名密码错误", null);
        }

        // 用户名密码校验成功
        Map<String, String> params = Maps.newHashMap();
        params.put("username", loginParam.getUsername());
        params.put("password", loginParam.getPassword());
        params.put("grant_type", GRANT_TYPE);
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);

        // 获取access_token
        try {
            Response response = OKHttpClientUtils.getInstance().postData(URL_OAUTH_TOKEN, params);
            String jsonString = Objects.requireNonNull(response.body()).string();
            Map<String, Object> jsonMap = MapperUtil.json2map(jsonString);
            String token = String.valueOf(jsonMap.get("access_token"));

            resultMap.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.SUCCESS, "登录成功", resultMap);
    }


    /**
     * 登录用户信息返回
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "info")
    @ApiOperation(value = "登录用户信息")
    public ResponseResult<LoginInfo> info() throws Exception {
        // 获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 获取个人信息
        String jsonString = profileFeign.info(authentication.getName());
        AdminUser adminUser = MapperUtil.json2pojoByTree(jsonString, "data", AdminUser.class);

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(adminUser.getUsername());
        loginInfo.setAvatar(adminUser.getIcon());
        loginInfo.setNickName(adminUser.getNickName());

        return new ResponseResult<LoginInfo>(ResponseResult.CodeStatus.SUCCESS, "获取登录用户信息", loginInfo);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping(value = "logout")
    @ApiOperation(value = "用户注销")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        String access_token = request.getParameter("access_token");
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "用户注销");
    }

}
