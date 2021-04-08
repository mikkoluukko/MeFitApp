package com.mefitapp.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class SecurityService {
    public boolean isContributor(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Contributor"));
    }

    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Admin"));
    }

    public boolean isOwner(String id, Principal principal) {
        return principal.getName().equals(id);
    }

}
