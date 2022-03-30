package com.luck.config;

import com.luck.api.R;
import com.luck.feign.AccountFeignClient;
import com.luck.vo.CustomerVo;
import com.luck.vo.LuckUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author liuyd
 * @date 2022/3/29 16:40
 */
@Service
public class LuckUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountFeignClient accountFeignClient;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        R<CustomerVo> info = accountFeignClient.info(username);
        if (info.isSuccess()) {
            CustomerVo customer = info.getData();
            return new LuckUserDetails(customer.getCId(),customer.getCPhone(),customer.getCName(),passwordEncoder.encode(customer.getCPassword()),true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        }else {
            throw new UsernameNotFoundException(info.getMsg());
        }
    }
}
