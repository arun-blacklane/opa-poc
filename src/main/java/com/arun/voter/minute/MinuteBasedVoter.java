package com.arun.voter.minute;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

public class MinuteBasedVoter implements AccessDecisionVoter {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection collection) {
        boolean isRoleUser = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_USER"::equals);
        if (isRoleUser) {
            if (LocalDateTime.now().getMinute() % 2 != 0) {
                return ACCESS_DENIED;
            } else {
                return ACCESS_GRANTED;
            }
        }
        return ACCESS_GRANTED;
    }
}
