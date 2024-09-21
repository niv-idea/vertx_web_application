package com.niv.admin.user.controller;

import com.niv.admin.authent.AccessMiddleware;
import com.niv.admin.user.request.UserLoginRequest;
import com.niv.models.dao.UserRepo;
import com.niv.models.dto.Response;
import com.niv.models.entity.User;
import com.niv.user.CommonController;
import com.niv.utils.ResponseUtils;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.List;

public enum FetchAllUsersController implements CommonController {
    INSTANCE;

    @Override
    public void handle(RoutingContext context) {
        AccessMiddleware.INSTANCE.authenticateRequest(context)
                .map(this::doNext)
                .subscribe(
                        response -> ResponseUtils.INSTANCE.writeJsonResponse(context, response),
                        error -> ResponseUtils.INSTANCE.handleError(context, error)
                );
    }

    Response doNext(UserLoginRequest request) {
        Response response = new Response();
        List<User> users = UserRepo.INSTANCE.userFinder.getExpressionList()
                .findList();
        response.setData(users);
        response.setMessage("success");
        return response;
    }
}
