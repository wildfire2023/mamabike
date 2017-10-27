package cn.powerr.mamabike.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class RestAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            PreAuthenticatedAuthenticationToken preAuth = (PreAuthenticatedAuthenticationToken) authentication;
            RestAuthenticationToken sysAuth = (RestAuthenticationToken) preAuth.getPrincipal();
            if (sysAuth.getAuthorities() != null && sysAuth.getAuthorities().size() > 0) {
                GrantedAuthority gauth = sysAuth.getAuthorities().iterator().next();
                if ("BIKE_CLIENT".equals(gauth.getAuthority())) {
                    return sysAuth;
                } else if ("ROLE_SOME".equals(gauth.getAuthority())) {
                    return sysAuth;
                }
            }
        } else if (authentication instanceof RestAuthenticationToken) {
            RestAuthenticationToken sysAuth = (RestAuthenticationToken) authentication;
            if (sysAuth.getAuthorities() != null && sysAuth.getAuthorities().size() > 0) {
                GrantedAuthority gauth = sysAuth.getAuthorities().iterator().next();
                if ("BIKE_CLIENT".equals(gauth.getAuthority())) {
                    return sysAuth;
                } else if ("ROLE_SOME".equals(gauth.getAuthority())) {
                    return sysAuth;
                }
            }
        }
        throw new BadCredentialException("unknown.error");
    }

    //如果拿到的Object是正确的值,返回true执行authenticate授权方法
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication) || RestAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
