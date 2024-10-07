package com.riwi.industry.configuration.auditory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    private static final Logger logger = Logger.getLogger(AuditorAwareImpl.class.getName());

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            logger.info("Current auditor: " + authentication.getName());
            return Optional.of(authentication.getName());
        }
        logger.info("No authenticated user found, setting auditor to ADMIN");
        return Optional.of("ADMIN");
    }
}
