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
//    INSTANCE;
//    private static final Logger log= LoggerFactory.getLogger(RegisterUser.class);
//
//    @Override
//    public void handle(RoutingContext context) {
//        Single.just(context)
//                .subscribeOn(RxHelper.blockingScheduler(context.vertx()))
//                .map(this::doNext)
//                .subscribe(
//                        response -> ResponseUtils.INSTANCE.writeJsonResponse(context, response),
//                        error -> ResponseUtils.INSTANCE.handleError(context, error)
//                );
//    }
//
//    private Response doNext(RoutingContext context) {
//        Response response = new Response();
//        try {
//            JsonObject bodyAsJson = context.getBodyAsJson();
//            LoginRequest loginRequest = bodyAsJson.mapTo(LoginRequest.class);
//            User users = UserRepo.INSTANCE.userFinder.getExpressionList()
//                    .eq("email", loginRequest.userName)
//                    .findOneOrEmpty()
//                    .orElseThrow(() -> new RoutingError("User not found with given userName : " + loginRequest.userName));
//            if (!users.getPassword().equals(loginRequest.password)) {
//                throw new RoutingError("Invalid Password");
//            }
//            String token = TokenService.INSTANCE.generateToken(users.getEmail(), users.getId());
//            Map<String, String> data = new HashMap<>();
//            data.put("token", token);
//            response.setMessage("success");
//            response.setData(data);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new RoutingError(e.getMessage());
//        }
//        return response;
//    }
//
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private static class LoginRequest {
//        @JsonProperty(value = "userName")
//        private String userName;
//        @JsonProperty(value = "password")
//        private String password;
//        public LoginRequest(){}
//    }
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
    private Response doNext(RoutingContext context) {
        Response response = new Response();
        try {
            JsonObject bodyAsJson = context.getBodyAsJson();
            log.info("Request body : {}", bodyAsJson.toString());
            UserRequest userRequest = bodyAsJson.mapTo(UserRequest.class);
            log.info("UserRequest : {}", userRequest.toString());
            System.out.println("hare krishna");
            if (userRequest==null) {
                throw new RoutingError("Request Body can not be null", 400);
            }
            validateRequest(userRequest);
            User user = mapRequestToEntity(userRequest);
            MySqlBeanFactory.INSTANCE.save(user);
            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getId());
            response.setData(data);
            response.setMessage("success");
            log.info("saved data : {}", JsonObject.mapFrom(response).toString());
        } catch (Exception e) {
            log.error("Exception : {}", e);
            throw new RoutingError(e.getMessage());
        }
        return response;
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
    private void test(){
        Observable.just("abc").map(s -> s.toUpperCase(Locale.ROOT))
                .subscribe(
//        success -> System.out.println(success),
                        System.out::println,
                        error -> System.out.println(error.getMessage())
                );
    }

}
