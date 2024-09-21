package com.niv.admin.authent;



import com.niv.admin.user.request.UserLoginRequest;
import com.niv.exception.RoutingError;
import com.niv.models.dao.UserRepo;
import com.niv.models.entity.User;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Single;


public enum AccessMiddleware {
    INSTANCE;

    private static final Logger log= LoggerFactory.getLogger(AccessMiddleware.class);

    public Single<UserLoginRequest> authenticateRequest(RoutingContext context){
        return  Single.just(context).
                subscribeOn(RxHelper.blockingScheduler(context.vertx())).map(this::validateRequest);

    }
    private UserLoginRequest validateRequest(RoutingContext context){
       String token= context.request().getHeader("Authorization");

        System.out.println("Token : "+token);
        if(token==null){
            throw  new RoutingError("Token is not found in the header",401);

        }
        if(token.length()<7){
            throw new RoutingError("Invalid Token",401);
        }
        String tokenType=token.substring(0,6);
        log.info("token Type{} ",tokenType);

        System.out.println("Token type :::: "+tokenType);

        if(!tokenType.equals("Bearer")){
            throw new RoutingError("Invalid Token ",401);
        }
        String accessToken = token.substring(7);
        Integer userId = TokenService.INSTANCE.getIdFromToken(accessToken);
        if (userId==null) {
            throw new RoutingError("Invalid Token", 401);
        }
        User user = UserRepo.INSTANCE.findUserById(userId).
               orElseThrow(()->new RoutingError("User Not found",401));
        UserLoginRequest request=new UserLoginRequest();
        request.setContext(context);
        request.setUser(user);
        return request;
    }
}
