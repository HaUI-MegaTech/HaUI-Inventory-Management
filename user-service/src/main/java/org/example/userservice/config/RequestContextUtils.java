package org.example.distributedauthentication.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * Phục vụ cho method: public boolean authorize(Authentication authentication, String action, Object callerObj, HttpServletRequest request)
 */
@Component
public class RequestContextUtils {

    public HttpServletRequest getCurrentHttpRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}