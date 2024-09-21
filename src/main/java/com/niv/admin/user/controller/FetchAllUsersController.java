package com.niv.admin.user.controller;

import com.niv.admin.authent.AccessMiddleware;
import com.niv.admin.user.request.UserLoginRequest;
import com.niv.exception.RoutingError;
import com.niv.models.dao.UserRepo;
import com.niv.models.dto.Response;
import com.niv.models.entity.User;
import com.niv.user.CommonController;
import com.niv.utils.ResponseUtils;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.Data;

import java.util.ArrayList;
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
        try {
            UserRepo.INSTANCE.userFinder.query().where().findList()
                    .forEach(response::setUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoutingError(e.getMessage());
        }
        return response;
    }

    @Data
    private static class Response {
        private List<UserData> datum = new ArrayList<>();
        void setUser(User user) {
            UserData data = new UserData(user);
            datum.add(data);
        }
    }

    @Data
    private static class UserData {
        private Integer id;
        private String firstName;
        private String lastName;
        private String middleName;
        private String email;
        private String mobileNo;
        private String password;
        UserData(User user) {
            this.id = user.getId();
            this.email = user.getEmail();;
            this.firstName = user.getFirstName();
            this.middleName = user.getMiddleName();
            this.lastName = user.getLastName();
            this.mobileNo = user.getMobileNo();;
        }
    }

}
