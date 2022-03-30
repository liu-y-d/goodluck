package com.luck.vo;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author liuyd
 * @date 2022/3/29 16:59
 */
@Getter
public class LuckUserDetails extends User {
    /**
     * 用户id
     */
    private final Long userId;

    /**
     * 邮箱
     */
    private final String phone;

    /**
     * 真名
     */
    private final String username;



    public LuckUserDetails(
            Long userId,
            String phone,
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.phone = phone;
        this.username = username;
    }

}
