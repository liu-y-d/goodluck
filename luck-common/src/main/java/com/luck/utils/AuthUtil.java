package com.luck.utils;

import com.luck.vo.LuckUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author liuyd
 * @date 2022/3/30 19:37
 */
public class AuthUtil {
    private static final String LUCK_USER_ATTR = "LUCK_USER_ATTR";

    /**
     * 获取用户信息
     *
     * @return TruthUser
     */
    public static LuckUser getUser() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        if (request == null) {
            return null;
        }
        // 优先从 request 中获取
        Object luckUser = request.getAttribute(LUCK_USER_ATTR);
        if (luckUser == null) {
            luckUser = getUser(request);
            if (luckUser != null) {
                // 设置到 request 中
                request.setAttribute(LUCK_USER_ATTR, luckUser);
            }
        }
        return (LuckUser) luckUser;
    }

    public static LuckUser getUser(HttpServletRequest request) {
        Claims claims = getClaims(request);
        if (claims == null) {
            return null;
        }
        Long userId = Long.parseLong(String.valueOf(claims.get("user_id")));
        String phone = (String) claims.get("phone");
        String username = (String) claims.get("user_name");

        LuckUser luckUser = new LuckUser();
        luckUser.setCId(userId);
        luckUser.setCName(username);
        luckUser.setCPhone(phone);
        return luckUser;
    }
    public static Claims getClaims(HttpServletRequest request) {
        String auth = request.getHeader("Luck-Auth");
        Claims claims = null;
        String token;
        // 获取 Token 参数
        if (StringUtils.isNotBlank(auth)) {
            token = getToken(auth);
        } else {
            String parameter = request.getParameter("Luck-Auth");
            token = getToken(parameter);
        }
        // 获取 Token 值
        if (StringUtils.isNotBlank(token)) {
            claims = AuthUtil.parseJWT(token);
        }
        // 判断 Token 状态
        // if (ObjectUtil.isNotEmpty(claims) && getJwtProperties().getState()) {
        //     String tenantId = Func.toStr(claims.get(AuthUtil.TENANT_ID));
        //     String userId = Func.toStr(claims.get(AuthUtil.USER_ID));
        //     String accessToken = JwtUtil.getAccessToken(tenantId, userId, token);
        //     if (!token.equalsIgnoreCase(accessToken)) {
        //         return null;
        //     }
        // }
        return claims;
    }
    public static Claims parseJWT(String jsonWebToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(Base64.getEncoder().encodeToString("luck".getBytes(StandardCharsets.UTF_8))))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception ex) {
            return null;
        }
    }
    private static String getToken(String auth) {
        if ((auth != null) && (auth.length() > 7)) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo("bearer") == 0) {
                auth = auth.substring(7);
            }
            return auth;
        }
        return null;
    }

}
