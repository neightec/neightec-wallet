package com.neightec.neightecavserver.security.oauth2;

import com.neightec.neightecavserver.models.neightec_data.NeightecUser;
import com.neightec.neightecavserver.services.NeightecUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@Log4j2
@RequiredArgsConstructor
public class OAuth2CustomFilter extends GenericFilterBean {

    private final NeightecUserService neightecUserService;

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }

        return null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("no http request or response instance");
        }

        try {
            String jwt = getJwtTokenFromRequest((HttpServletRequest) servletRequest); // is this fine on casting in HttpServletRequest
            NeightecUser currentUser = this.neightecUserService.getCurrentUwbUser();
//            Authentication updatedAuthentication = neightecUserService.writeUwbRolesIntoAuthentication(currentUser);
            Authentication updatedAuthentication = null;
            if (updatedAuthentication == null) {
                log.info("User is not authenticated");
            } else {
                log.info("User is authenticated");
                SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
            }
        } catch (IllegalArgumentException iae) {
            log.debug(iae.getMessage(), iae);
        }

        log.debug("OAuth2CustomFilter is passing request down the filter chain");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
