package com.niv.admin.user.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysql.cj.log.Log;
import com.niv.admin.authent.TokenService;
import com.niv.exception.RoutingError;
import com.niv.factory.MySqlBeanFactory;
import com.niv.models.dao.UserRepo;
import com.niv.models.dto.Response;
import com.niv.models.dto.SuccessResponse;
import com.niv.models.entity.User;
import com.niv.user.CommonController;
import com.niv.utils.ResponseUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Single;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum RegisterUser implements CommonController {
INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(RegisterUser.class);

    @Override
    public void handle(RoutingContext context) {
        Single.just(context)
                .subscribeOn(RxHelper.blockingScheduler(context.vertx()))
                .map(this::doNext)
                .subscribe(

                        response -> ResponseUtils.writeJsonResponse(context, response),

                        error -> ResponseUtils.handleError(context, error)
                );
    }
    private SuccessResponse doNext(RoutingContext context) {
        try {
            JsonObject bodyAsJson = context.getBodyAsJson();
            UserRequest userRequest = bodyAsJson.mapTo(UserRequest.class);
            validateRequest(userRequest);
            User user = mapRequestToEntity(userRequest);
            user.save();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoutingError(e.getMessage());
        }
        return SuccessResponse.generateSuccessResponse();
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class UserRequest {
        private String firstName;
        private String lastName;
        private String middleName;
        private String email;
        private String mobileNo;
        private String password;
    }
    private void validateRequest(UserRequest request) {
        if (request.getEmail()==null) {
            throw new RoutingError("Please provide email", 400);
        }
        if (request.getFirstName()==null) {
            throw new RoutingError("Please provide First Name 'fName'", 400);
        }
        if (request.getLastName()==null) {
            throw new RoutingError("Please provide Last Name 'lName'", 400);
        }
        if (request.getPassword()==null) {
            throw new RoutingError("Please provide password", 400);
        }
    }
    private User mapRequestToEntity(UserRequest request) {
        log.info("inside mapRequestToEntity");
        User users = new User();
        users.setFirstName(request.getFirstName());
        users.setLastName(request.getLastName());
        users.setPassword(request.getPassword());
        users.setEmail(request.getEmail());
        if (request.getMobileNo()!=null) {
            users.setMobileNo(request.getMobileNo());
        }
        if (request.getMiddleName()!=null) {
            users.setMiddleName(request.getMiddleName());
        }
        return users;
    }

}
