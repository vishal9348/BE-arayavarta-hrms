package com.aryavarta.identity.mapper;

import com.aryavarta.identity.entity.Session;
import com.aryavarta.identity.record.session.SessionResponse;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {

    public SessionResponse toResponse(Session entity) {
        return new SessionResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getStatus(),
                entity.getIpAddress(),
                entity.getUserAgent(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.getLastAccessedAt()
        );
    }
}