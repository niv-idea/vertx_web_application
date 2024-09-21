package com.niv.admin.user.request;

import com.niv.models.entity.User;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.Data;

@Data
public class UserLoginRequest {
    private RoutingContext context;
    private User user;
}
