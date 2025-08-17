package com.fullcycle.subscription.infrastructure.gateway.client;

import com.fullcycle.subscription.domain.account.idp.GroupId;
import com.fullcycle.subscription.domain.account.idp.IdentityProviderGateway;
import com.fullcycle.subscription.domain.account.idp.User;
import com.fullcycle.subscription.domain.account.idp.UserId;
import com.fullcycle.subscription.domain.utils.IdUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.util.*;

@Component
public class KeycloakIdentityProviderClient implements IdentityProviderGateway {

    @Override
    public UserId create(final User anUser) {
        return new UserId(IdUtils.uniqueId());
    }

    @Override
    public void addUserToGroup(final UserId userId, final GroupId aGroupId) {

    }

    @Override
    public void removeUserFromGroup(final UserId userId, final GroupId aGroupId) {

    }

    private String getUserId(final HttpHeaders headers) {
        return Optional.ofNullable(headers)
                .map(HttpHeaders::getLocation)
                .map(URI::getPath)
                .map(it -> {
                    String[] parts = it.split("/");
                    return parts[parts.length - 1];
                })
                .orElse(null);
    }
}