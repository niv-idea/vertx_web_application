package com.niv.v2.employee;

import com.niv.utils.SubRouter;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;

public enum MountEmployeeRouter implements SubRouter {
    INSTANCE;

    @Override
    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);
       // router.get("/:id/updateGender").handler(UpdateEmployeeGender.INSTANCE::handle);
        router.get("/checkInstance").handler(EmployeeStatisticController.INSTANCE::handle);

        return router;
    }
}
