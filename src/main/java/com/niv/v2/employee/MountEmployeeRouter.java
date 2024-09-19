package com.niv.v2.employee;

import com.niv.controller.employeeController.*;
import com.niv.utils.SubRouter;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;

public enum MountEmployeeRouter implements SubRouter {
    INSTANCE;


    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);
        router.post("/add").handler(AddEmployeeController.INSTANCE::handle);
        router.get("/:employeeId").handler(GetEmployeeController.INSTANCE::handle);
        router.get("/").handler(GetAllEmployeeController.INSTANCE::handle);
        router.delete("/:empId").handler(DeleteEmployeeController.INSTANCE::handle);
        router.patch("/update/:empId").handler(UpdateEmployeeController.INSTANCE::handle);
        return router;
    }
}
