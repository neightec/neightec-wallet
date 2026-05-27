package com.neightec.neightecavserver.services;

import com.neightec.neightecavserver.models.neightec_data.NeightecUser;
import io.jsonwebtoken.Jwt;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class NeightecUserService {

    @Transactional
    public NeightecUser login() throws IllegalArgumentException {
        return null;
    }

    @Transactional
    public NeightecUser setAuthentication() {
        return null;
    }

    public NeightecUser getCurrentUwbUser() throws IllegalArgumentException {
        Jwt jwtToken = getIdToken();
        if (jwtToken != null) {
            Object payload = jwtToken.getPayload();
//            UwbUser user = findBySSOId(UUID.fromString(oid));
//            if (user != null) {
//                return user;
//            }
        }
//        throw new IllegalArgumentException("could not find a proper user");
        return null;
    }

    private Jwt getIdToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && (authentication.getPrincipal() instanceof Jwt)) {
            return (Jwt) authentication.getPrincipal();
        }
        return null;
    }
}
