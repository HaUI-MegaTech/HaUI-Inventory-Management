package org.example.userservice.shared.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AppAuthorizer {
    boolean authorize(Authentication authentication, Object callerObj, HttpServletRequest request);
}
